/**
 * Copyright (c) 2011-2024, JFXtras
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL JFXTRAS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx.ByRuleIntegerAbstract;
import jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx.ByYearDay;

/**
 * By Year Day
 * BYYEARDAY
 * RFC 5545, iCalendar 3.3.10, page 42
 * 
 * The BYYEARDAY rule part specifies a COMMA-separated list of days
      of the year.  Valid values are 1 to 366 or -366 to -1.  For
      example, -1 represents the last day of the year (December 31st)
      and -306 represents the 306th to the last day of the year (March
      1st).  The BYYEARDAY rule part MUST NOT be specified when the FREQ
      rule part is set to DAILY, WEEKLY, or MONTHLY.
  *
  * @author David Bal
  * 
  */
public class ByYearDay extends ByRuleIntegerAbstract<ByYearDay>
{       
    public ByYearDay()
    {
        super();
    }
    
    public ByYearDay(Integer... yearDays)
    {
        super(yearDays);
    }
    
    public ByYearDay(ByYearDay source)
    {
        super(source);
    }
    
    @Override
    Predicate<Integer> isValidValue()
    {
        return (value) -> (value >= -366) && (value <= 366) && (value != 0);
    }

    @Override
    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal dateTimeStart)
    {
        switch (chronoUnit)
        {
        case HOURS:
        case MINUTES:
        case SECONDS:
            return inStream.filter(d ->
                    { // filter out all but qualifying days
                        int myDayOfYear = d.get(ChronoField.DAY_OF_YEAR);
                        for (int dayOfYear : getValue())
                        {
                            if (dayOfYear > 0)
                            {
                                if (dayOfYear == myDayOfYear) return true;
                            } else
                            { // handle negative days of year
                                Temporal firstDayOfNextYear = d.with(TemporalAdjusters.firstDayOfNextYear());
                                Period myNegativeDayOfYear = Period.between(LocalDate.from(firstDayOfNextYear), LocalDate.from(d));
                                if (Period.ofDays(dayOfYear).equals(myNegativeDayOfYear)) return true;
                            }
                        }
                        return false;
                    });
        case YEARS:
            return inStream.flatMap(d -> 
            { // Expand to be include all days of year
                List<Temporal> dates = new ArrayList<>();
                for (int dayOfYear : getValue())
                {
                    final Temporal correctYearTemporal = (dayOfYear > 0) ? d : d.minus(1, ChronoUnit.YEARS);
                    int daysInYear = (int) ChronoUnit.DAYS.between(correctYearTemporal.with(TemporalAdjusters.firstDayOfYear()),
                                                                   correctYearTemporal.with(TemporalAdjusters.firstDayOfNextYear()));
                    int finalDayOfYear = 0;
                    if (dayOfYear > 0)
                    {
                        if (dayOfYear <= daysInYear)
                        {
                            finalDayOfYear = dayOfYear;
                        }
                    } else if (dayOfYear < 0)
                    {
                        int newDayOfYear = daysInYear + dayOfYear + 1;
                        if (newDayOfYear > 0)
                        {
                            finalDayOfYear = newDayOfYear;
                        }
                    } else
                    {
                        throw new IllegalArgumentException(name().toString() + " can't have a value of zero");
                    }
                    Temporal newTemporal = (finalDayOfYear != 0) ? correctYearTemporal.with(ChronoField.DAY_OF_YEAR, finalDayOfYear) : null;

                    if (newTemporal != null)
                    {
                        dates.add(newTemporal);
                    }
                }
                return dates.stream();
            });       
        case DAYS:
        case WEEKS:
        case MONTHS:
            throw new IllegalArgumentException(name().toString() + " is not available for " + chronoUnit + " frequency."); // Not available
        default:
            throw new IllegalArgumentException("Not implemented: " + chronoUnit);
        }
    }
    
    public static ByYearDay parse(String content)
    {
    	return ByYearDay.parse(new ByYearDay(), content);
    }
}
