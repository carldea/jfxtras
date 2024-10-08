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

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import jfxtras.icalendarfx.parameters.AlarmTriggerRelationship;
import jfxtras.icalendarfx.parameters.AlarmTriggerRelationship.AlarmTriggerRelationshipType;
import jfxtras.icalendarfx.properties.ValueType;
import jfxtras.icalendarfx.properties.component.alarm.Trigger;

public class TriggerTest
{
    @Test
    public void canParseTrigger1()
    {
        String expectedContent = "TRIGGER:-PT15M";
        Trigger<Duration> madeProperty = Trigger.parse(expectedContent);
        assertEquals(expectedContent, madeProperty.toString());
        assertEquals(Duration.ofMinutes(-15), madeProperty.getValue());
    }
    
    @Test
    public void canBuildTrigger1()
    {
        Trigger<Duration> madeProperty = new Trigger<Duration>(Duration.ofMinutes(5))
                .withAlarmTrigger(new AlarmTriggerRelationship(AlarmTriggerRelationshipType.END));
        String expectedContent = "TRIGGER;RELATED=END:PT5M";
        assertEquals(expectedContent, madeProperty.toString());
        assertEquals(Duration.ofMinutes(5), madeProperty.getValue());
        assertEquals(AlarmTriggerRelationshipType.END, madeProperty.getAlarmTrigger().getValue());
    }

    @Test
    public void canBuildTrigger2()
    {
        ZonedDateTime d = ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("Z"));
        Trigger<ZonedDateTime> madeProperty = new Trigger<ZonedDateTime>(d);
        String expectedContent = "TRIGGER;VALUE=DATE-TIME:20160306T043000Z";
        assertEquals(expectedContent, madeProperty.toString());
    }
    
    @Test
    public void canParseTrigger2()
    {
        String expectedContent = "TRIGGER;VALUE=DATE-TIME:20160306T043000Z";
        Trigger<ZonedDateTime> madeProperty = Trigger.parse(ZonedDateTime.class, expectedContent);
        assertEquals(expectedContent, madeProperty.toString());
        ZonedDateTime d = ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("Z"));
        assertEquals(d, madeProperty.getValue());
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void canCatchInvalidTypeChange()
    {
        new Trigger<Duration>(Duration.ofMinutes(5))
                .withAlarmTrigger(new AlarmTriggerRelationship(AlarmTriggerRelationshipType.END))
                .withValueType(ValueType.DATE_TIME); // invalid type
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void canCatchInvalidRelationship()
    {
        ZonedDateTime d = ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("Z"));
        new Trigger<ZonedDateTime>(d)
                .withAlarmTrigger(new AlarmTriggerRelationship(AlarmTriggerRelationshipType.END)); // not allowed for DATE-TIME value type
    }    
    
    @Test (expected=DateTimeException.class)
    public void canCatchNonUTCZone()
    {
        ZonedDateTime d = ZonedDateTime.of(LocalDateTime.of(2016, 3, 6, 4, 30), ZoneId.of("America/Los_Angeles"));
        new Trigger<ZonedDateTime>(d);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void canCatchInvalidParameterizedType()
    {
        Trigger.parse(Integer.class, "123"); // only TemporalAmount and ZonedDateTime accepted
    }
    
    @Test (expected=DateTimeException.class)
    public void canCatchTypeContentMismatch()
    {
        Trigger.parse(ZonedDateTime.class, "TRIGGER;RELATED=END:PT5M");
    }
    
    @Test (expected=DateTimeException.class)
    public void canCatchTypeContentMismatch2()
    {
        Trigger.parse(Duration.class, "TRIGGER;VALUE=DATE-TIME:20160306T043000Z");
    }
}
