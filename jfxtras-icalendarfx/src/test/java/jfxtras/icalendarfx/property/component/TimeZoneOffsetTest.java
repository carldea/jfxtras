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

import java.time.ZoneOffset;

import org.junit.Test;

import jfxtras.icalendarfx.properties.component.timezone.TimeZoneOffsetFrom;
import jfxtras.icalendarfx.properties.component.timezone.TimeZoneOffsetTo;

public class TimeZoneOffsetTest
{
    @Test
    public void canParseTimeZoneOffsetFrom()
    {
        String content = "TZOFFSETFROM:-0500";
        TimeZoneOffsetFrom madeProperty = TimeZoneOffsetFrom.parse(content);
        assertEquals(content, madeProperty.toString());
        TimeZoneOffsetFrom expectedProperty = new TimeZoneOffsetFrom(ZoneOffset.of("-05:00"));
        assertEquals(expectedProperty, madeProperty);
    }
    
    @Test
    public void canParseTimeZoneOffsetTo()
    {
        String content = "TZOFFSETTO:+0000";
        TimeZoneOffsetTo madeProperty = TimeZoneOffsetTo.parse(content);
        assertEquals(content, madeProperty.toString());
        TimeZoneOffsetTo expectedProperty = new TimeZoneOffsetTo(ZoneOffset.of("+00:00"));
        assertEquals(expectedProperty, madeProperty);
    }

}
