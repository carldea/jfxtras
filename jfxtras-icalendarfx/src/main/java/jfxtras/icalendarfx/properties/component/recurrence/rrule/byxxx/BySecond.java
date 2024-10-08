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

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx.ByMinute;
import jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx.ByRuleIntegerAbstract;
import jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx.BySecond;

public class BySecond extends ByRuleIntegerAbstract<BySecond>
{
    public BySecond()
    {
        super();
    }
    
    public BySecond(Integer... minutes)
    {
        super(minutes);
    }
    
    public BySecond(BySecond source)
    {
        super(source);
    }
    
    @Override
    Predicate<Integer> isValidValue()
    {
        return (value) -> (value >= 0) && (value <= 59);
    }
    
    @Override
    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal dateTimeStart)
    {
        if (dateTimeStart.isSupported(ChronoField.SECOND_OF_MINUTE))
        {
            switch (chronoUnit)
            {
            case SECONDS:
                return inStream.filter(d ->
                        { // filter out all but qualifying hours
                            int mySecondOfMinute = d.get(ChronoField.SECOND_OF_MINUTE);
                            for (int secondOfMinute : getValue())
                            {
                                if (secondOfMinute > 0)
                                {
                                    if (secondOfMinute == mySecondOfMinute) return true;
                                }
                            }
                            return false;
                        });
            case HOURS:
            case MINUTES:
            case DAYS:
            case WEEKS:
            case MONTHS:
            case YEARS:
                return inStream.flatMap(d -> 
                { // Expand to be include all hours of day
                    List<Temporal> dates = new ArrayList<>();
                    for (int minuteOfHour : getValue())
                    {
                        Temporal newTemporal = d.with(ChronoField.SECOND_OF_MINUTE, minuteOfHour);
//                        if (! DateTimeUtilities.isBefore(newTemporal, dateTimeStart))
//                        {
                            dates.add(newTemporal);
//                        }
                    }
                    return dates.stream();
                });
            default:
                throw new IllegalArgumentException("Not implemented: " + chronoUnit);
            }
        } else
        {
            return inStream; // ignore rule when not supported (RFC 5545 requirement)
        }
    }

    public static ByMinute parse(String content)
    {
    	return ByMinute.parse(new ByMinute(), content);
    }
}
