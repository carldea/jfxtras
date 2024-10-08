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
package jfxtras.icalendarfx.property.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import jfxtras.icalendarfx.properties.ValueType;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;

public class DateTimeStartTest
{
    @Test
    public void canParseDateTimeStart1()
    {
        DateTimeStart dateTimeStart = DateTimeStart.parse(LocalDateTime.class, "20160322T174422");
        String expectedContentLine = "DTSTART:20160322T174422";
        String madeContentLine = dateTimeStart.toString();
        assertEquals(expectedContentLine, madeContentLine);
        assertEquals(LocalDateTime.of(2016, 3, 22, 17, 44, 22), dateTimeStart.getValue());
    }
    
    @Test
    public void canParseDateTimeStart2()
    {
        DateTimeStart dateTimeStart = DateTimeStart.parse(LocalDate.class, "DTSTART;VALUE=DATE:20160322");
        String expectedContentLine = "DTSTART;VALUE=DATE:20160322";
        String madeContentLine = dateTimeStart.toString();
        assertEquals(expectedContentLine, madeContentLine);
        assertEquals(LocalDate.of(2016, 3, 22), dateTimeStart.getValue());
        DateTimeStart dateTimeStart2 = new DateTimeStart(LocalDate.of(2016, 3, 22));
        assertEquals(dateTimeStart, dateTimeStart2);
        assertEquals(dateTimeStart.toString(), dateTimeStart2.toString());
    }
    
    @Test
    public void canParseDateTimeStart3()
    {
        DateTimeStart dateTimeStart = DateTimeStart.parse(ZonedDateTime.class, "DTSTART;TZID=America/Los_Angeles:20160306T043000");
        String expectedContentLine = "DTSTART;TZID=America/Los_Angeles:20160306T043000";
        String madeContentLine = dateTimeStart.toString();
        assertEquals(expectedContentLine, madeContentLine);
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("America/Los_Angeles")), dateTimeStart.getValue());
    }
    
    @Test
    public void canParseDateTimeStart4()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("America/Los_Angeles")))
                .withTimeZoneIdentifier("America/Los_Angeles")
                .withValueType(ValueType.DATE_TIME);;
        String expectedContentLine = "DTSTART;TZID=America/Los_Angeles;VALUE=DATE-TIME:20160306T043000";
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(ZonedDateTime.class, expectedContentLine);
        assertEquals(expectedDateTimeStart, dateTimeStart);
        String madeContentLine = dateTimeStart.toString();
        assertEquals(expectedContentLine, madeContentLine);
    }

    @Test
    public void canBuildDateTimeStartZoned()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("America/Los_Angeles")))
                .withTimeZoneIdentifier("America/Los_Angeles")
                .withValueType(ValueType.DATE_TIME);
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(ZonedDateTime.class, "DTSTART;TZID=America/Los_Angeles;VALUE=DATE-TIME:20160306T043000");
        assertEquals(expectedDateTimeStart, dateTimeStart);
    }
    
    @Test
    public void canBuildDateTimeStartZoned2()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("America/Los_Angeles")));
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(ZonedDateTime.class, "DTSTART;TZID=America/Los_Angeles:20160306T043000");
        assertEquals(expectedDateTimeStart, dateTimeStart);
    }

    @Test
    public void canBuildDateTimeStartZonedUTC()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("Z")));
        String expectedContentLine = "DTSTART:20160306T043000Z";
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(ZonedDateTime.class, expectedContentLine);
        assertEquals(expectedContentLine, dateTimeStart.toString());
        assertEquals(expectedDateTimeStart, dateTimeStart);
    }
    
    @Test
    public void canBuildDateTimeStartZonedUTC2()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 6, 0), ZoneId.of("Etc/GMT")));
        String expectedContentLine = "DTSTART;TZID=Etc/GMT:20160306T060000Z";
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(ZonedDateTime.class, expectedContentLine);
        assertEquals(expectedContentLine, dateTimeStart.toString());
        assertEquals(expectedDateTimeStart, dateTimeStart);
    }

    @Test
    public void canBuildDateTimeStartLocal()
    {
        DateTimeStart dateTimeStart = new DateTimeStart(LocalDateTime.of(2016, 3, 6, 4, 30));
        String expectedContentLine = "DTSTART:20160306T043000";
        DateTimeStart expectedDateTimeStart = DateTimeStart.parse(expectedContentLine);
        assertEquals(expectedContentLine, dateTimeStart.toString());
        assertEquals(expectedDateTimeStart, dateTimeStart);
    }
    
    @Test
    public void canCopyDateTimeStart()
    {
        DateTimeStart dateTimeStart1 = new DateTimeStart(LocalDateTime.of(2016, 3, 6, 4, 30));
        DateTimeStart dateTimeStart2 = new DateTimeStart(dateTimeStart1);
        assertEquals(dateTimeStart1, dateTimeStart2);
        assertTrue(dateTimeStart1 != dateTimeStart2);
    }
    
    @Test (expected=ClassCastException.class)
    public void canCatchWrongTemporalType()
    {
        DateTimeStart.parse(LocalDate.class, "DTSTART;TZID=America/Los_Angeles:20160306T043000");
    }
    
    @Test (expected=DateTimeException.class)
    public void canCatchWrongType()
    {
        new DateTimeStart(LocalTime.of(5, 10));        
    }
}
