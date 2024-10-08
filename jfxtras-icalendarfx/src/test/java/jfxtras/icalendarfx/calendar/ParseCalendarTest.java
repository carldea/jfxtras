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
package jfxtras.icalendarfx.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import jfxtras.icalendarfx.ICalendarTestAbstract;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.calendar.Version;

public class ParseCalendarTest extends ICalendarTestAbstract
{
    @Test
    public void canParseSimpleVCalendar()
    {
        String content = 
       "BEGIN:VCALENDAR" + System.lineSeparator() +
       "VERSION:2.0" + System.lineSeparator() +
       "PRODID:-//hacksw/handcal//NONSGML v1.0//EN" + System.lineSeparator() +
       "BEGIN:VEVENT" + System.lineSeparator() +
       "UID:19970610T172345Z-AF23B2@example.com" + System.lineSeparator() +
       "DTSTAMP:19970610T172345Z" + System.lineSeparator() +
       "DTSTART:19970714T170000Z" + System.lineSeparator() +
       "DTEND:19970715T040000Z" + System.lineSeparator() +
       "SUMMARY:Bastille Day Party" + System.lineSeparator() +
       "END:VEVENT" + System.lineSeparator() +
       "END:VCALENDAR";
        
        VCalendar vCalendar = VCalendar.parse(content);
        assertEquals(content, vCalendar.toString());
        
        VCalendar vCalendar2 = new VCalendar()
                .withVersion(new Version())
                .withProductIdentifier("-//hacksw/handcal//NONSGML v1.0//EN")
                .withVEvents(new VEvent()
                        .withUniqueIdentifier("UID:19970610T172345Z-AF23B2@example.com")
                        .withDateTimeStamp("DTSTAMP:19970610T172345Z")
                        .withDateTimeStart("19970714T170000Z")
                        .withDateTimeEnd(ZonedDateTime.of(LocalDateTime.of(1997, 7, 15, 4, 0), ZoneId.of("Z")))
                        .withSummary("Bastille Day Party")
                        );
        assertEquals(vCalendar2, vCalendar);
    }
    
    @Test
    public void canParseVCalendarWithNonStandard()
    {
        String content = 
       "BEGIN:VCALENDAR" + System.lineSeparator() +
       "VERSION:2.0" + System.lineSeparator() +
       "PRODID:-//hacksw/handcal//NONSGML v1.0//EN" + System.lineSeparator() +
       "BEGIN:VEVENT" + System.lineSeparator() +
       "UID:19970610T172345Z-AF23B2@example.com" + System.lineSeparator() +
       "DTSTAMP:19970610T172345Z" + System.lineSeparator() +
       "DTSTART:19970714T170000Z" + System.lineSeparator() +
       "DTEND:19970715T040000Z" + System.lineSeparator() +
       "SUMMARY:Bastille Day Party" + System.lineSeparator() +
       "END:VEVENT" + System.lineSeparator() +
       "BEGIN:VEVENT" + System.lineSeparator() +
       "SUMMARY:New Event" + System.lineSeparator() +
       "CLASS:PUBLIC" + System.lineSeparator() +
       "DTSTART;TZID=Etc/GMT:20150902T133000Z" + System.lineSeparator() +
       "DTEND;TZID=Etc/GMT:20150902T180000Z" + System.lineSeparator() +
       "PRIORITY:0" + System.lineSeparator() +
       "SEQUENCE:1" + System.lineSeparator() +
       "STATUS:CANCELLED" + System.lineSeparator() +
       "UID:dc654e79-cc85-449c-a1b2-71b2d20b80df" + System.lineSeparator() +
       "RECURRENCE-ID;TZID=Etc/GMT:20150902T133000Z" + System.lineSeparator() +
       "DTSTAMP:20150831T053218Z" + System.lineSeparator() +
       "ORGANIZER;CN=David Bal;SENT-BY=\"mailto:ddbal1@yahoo.com\":mailto:ddbal1@yaho" + System.lineSeparator() +
       " o.com" + System.lineSeparator() +
       "X-YAHOO-YID:testuser" + System.lineSeparator() +
       "TRANSP:OPAQUE" + System.lineSeparator() +
       "X-YAHOO-USER-STATUS:BUSY" + System.lineSeparator() +
       "X-YAHOO-EVENT-STATUS:BUSY" + System.lineSeparator() +
       "BEGIN:VALARM" + System.lineSeparator() +
       "ACTION:DISPLAY" + System.lineSeparator() +
       "DESCRIPTION:" + System.lineSeparator() +
       "TRIGGER;RELATED=START:-PT30M" + System.lineSeparator() +
       "END:VALARM" + System.lineSeparator() +
       "END:VEVENT" + System.lineSeparator() +
       "END:VCALENDAR";
        
        VCalendar vCalendar = VCalendar.parse(content);
        assertEquals(content, vCalendar.toString());
    }
    
    
    @Test
    public void canParseGoogleRepeatable()
    {
        String vEventString1 = "BEGIN:VEVENT" + System.lineSeparator()
                + "DTSTART;TZID=America/Los_Angeles:20160214T110000" + System.lineSeparator()
                + "DTEND;TZID=America/Los_Angeles:20160214T130000" + System.lineSeparator()
                + "RRULE:FREQ=DAILY;UNTIL=20160218T185959Z" + System.lineSeparator()
                + "DTSTAMP:20160214T213637Z" + System.lineSeparator()
                + "UID:mrrfvnj5acdcvn13273on9nrhs@google.com" + System.lineSeparator()
                + "CREATED:20160214T193703Z" + System.lineSeparator()
                + "DESCRIPTION:" + System.lineSeparator()
                + "LAST-MODIFIED:20160214T193717Z" + System.lineSeparator()
                + "LOCATION:" + System.lineSeparator()
                + "SEQUENCE:0" + System.lineSeparator()
                + "STATUS:CONFIRMED" + System.lineSeparator() // currently not supported
                + "SUMMARY:test4" + System.lineSeparator()
                + "TRANSP:OPAQUE" + System.lineSeparator() // currently not supported
                + "END:VEVENT";
    
        String vEventString2 = "BEGIN:VEVENT" + System.lineSeparator()
                + "DTSTART;TZID=America/Los_Angeles:20160218T110000" + System.lineSeparator()
                + "DTEND;TZID=America/Los_Angeles:20160218T140000" + System.lineSeparator()
                + "RRULE:FREQ=DAILY;COUNT=6" + System.lineSeparator()
                + "DTSTAMP:20160214T213637Z" + System.lineSeparator()
                + "UID:mrrfvnj5acdcvn13273on9nrhs_R20160218T190000@google.com" + System.lineSeparator()
                + "CREATED:20160214T193703Z" + System.lineSeparator()
                + "DESCRIPTION:" + System.lineSeparator()
                + "LAST-MODIFIED:20160214T193717Z" + System.lineSeparator()
                + "LOCATION:" + System.lineSeparator()
                + "SEQUENCE:0" + System.lineSeparator()
                + "STATUS:CONFIRMED" + System.lineSeparator() // currently not supported
                + "SUMMARY:test5" + System.lineSeparator()
                + "TRANSP:OPAQUE" + System.lineSeparator() // currently not supported
                + "END:VEVENT";
    
        String vEventString3 = "BEGIN:VEVENT" + System.lineSeparator()
                + "DTSTART;TZID=America/Los_Angeles:20160216T070000" + System.lineSeparator()
                + "DTEND;TZID=America/Los_Angeles:20160216T090000" + System.lineSeparator()
                + "DTSTAMP:20160214T213637Z" + System.lineSeparator()
                + "UID:mrrfvnj5acdcvn13273on9nrhs@google.com" + System.lineSeparator()
                + "CREATED:20160214T193703Z" + System.lineSeparator()
                + "DESCRIPTION:" + System.lineSeparator()
                + "LAST-MODIFIED:20160214T213226Z" + System.lineSeparator()
                + "LOCATION:" + System.lineSeparator()
                + "RECURRENCE-ID;TZID=America/Los_Angeles:20160216T110000" + System.lineSeparator()
                + "SEQUENCE:1" + System.lineSeparator()
                + "STATUS:CONFIRMED" + System.lineSeparator() // currently not supported
                + "SUMMARY:test6" + System.lineSeparator()
                + "TRANSP:OPAQUE" + System.lineSeparator() // currently not supported
                + "END:VEVENT";
        
        VCalendar c = new VCalendar()
                .withProductIdentifier("My ID")
                .withVersion(new Version());
        c.addChild(vEventString1);
        c.addChild(vEventString2);
        c.addChild(vEventString3);
        assertEquals(3, c.getVEvents().size());
        assertEquals(1, c.getVEvents().get(0).recurrenceChildren().size());
        assertEquals(c.getVEvents().get(0), c.getVEvents().get(2).recurrenceParent());
        assertTrue(c.isValid());
    }
    
    @Test (expected = NullPointerException.class)
    public void canParseNullCalendar() throws IOException
    {
        VCalendar.parse((Reader) null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void canParseInvalidCalendar1()
    {
    	VCalendar.parse("invalid calendar");
    }
    
    @Test
    public void canIgnoreUnrecognizedLine()
    {
        String content = 
       "BEGIN:VCALENDAR" + System.lineSeparator() +
       "Ignore this line" + System.lineSeparator() +       
       "END:VCALENDAR";
        VCalendar v = VCalendar.parse(content);
        VCalendar expected = new VCalendar();
        assertEquals(expected, v);
        assertEquals("BEGIN:VCALENDAR" + System.lineSeparator() +   
        	       "END:VCALENDAR", v.toString());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canParseBadVCalendar1()
    {
        String content = 
       "BEGIN:VCALENDAR" + System.lineSeparator() +
       "VERSION:2.0" + System.lineSeparator() +
       "PRODID:-//hacksw/handcal//NONSGML v1.0//EN" + System.lineSeparator() +
       "BEGIN:VEVENT" + System.lineSeparator() +
       "UID:19970610T172345Z-AF23B2@example.com" + System.lineSeparator() +
       "DTSTAMP:19970610T172345Z" + System.lineSeparator() +
       "DTSTART:19970714T170000Z" + System.lineSeparator() +
//       "DTSTART:19970714T170000Z" + System.lineSeparator() +
       "DTEND:19970715T040000Z" + System.lineSeparator() +
       "SUMMARY:Bastille Day Party" + System.lineSeparator() +
       "END:VEVENT" + System.lineSeparator() +
       "BEGIN:VEVENT" + System.lineSeparator() +
       "SUMMARY:New Event" + System.lineSeparator() +
       "CLASS:PUBLIC" + System.lineSeparator() +
       "DTSTART;TZID=Etc/GMT:20150902T133000Z" + System.lineSeparator() +
       "DTEND;TZID=Etc/GMT:20150902T180000Z" + System.lineSeparator() +
       "PRIORITY:0" + System.lineSeparator() +
       "SEQUENCE:1" + System.lineSeparator() +
       "STATUS:CANCELLED" + System.lineSeparator() +
       "UID:dc654e79-cc85-449c-a1b2-71b2d20b80df" + System.lineSeparator() +
       "RECURRENCE-ID;TZID=Etc/GMT:20150902T133000Z" + System.lineSeparator() +
       "STATUS:CANCELLED" + System.lineSeparator() + // extra property to be ignored
       "DTSTAMP:20150831T053218Z" + System.lineSeparator() +
       "ORGANIZER;CN=David Bal;SENT-BY=\"mailto:ddbal1@yahoo.com\":mailto:ddbal1@yaho" + System.lineSeparator() +
       " o.com" + System.lineSeparator() +
       "X-YAHOO-YID:testuser" + System.lineSeparator() +
       "TRANSP:OPAQUE" + System.lineSeparator() +
       "X-YAHOO-USER-STATUS:BUSY" + System.lineSeparator() +
       "X-YAHOO-EVENT-STATUS:BUSY" + System.lineSeparator() +
       "BEGIN:VALARM" + System.lineSeparator() +
       "ACTION:DISPLAY" + System.lineSeparator() +
       "DESCRIPTION:" + System.lineSeparator() +
       "TRIGGER;RELATED=START:-PT30M" + System.lineSeparator() +
       "END:VALARM" + System.lineSeparator() +
       "END:VEVENT" + System.lineSeparator() +
       "END:VCALENDAR";
        
        VCalendar.parse(content);
    }
    
    @Test
    public void canIgnoreBadLine()
    {
            String content = "BEGIN:VCALENDAR" + System.lineSeparator() +
            "X-CUSTOM-PROP:THE DATA" + System.lineSeparator() +
            "IGNORE THIS LINE" + System.lineSeparator() +
            "END:VCALENDAR";
            VCalendar v = VCalendar.parse(content);            
            VCalendar expected = new VCalendar()
                    .withNonStandard("X-CUSTOM-PROP:THE DATA")
                    ;
            assertEquals(expected, v);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canHandleDuplicate()
    {
            String content = "BEGIN:VCALENDAR" + System.lineSeparator() +
            "X-CUSTOM-PROP:THE DATA" + System.lineSeparator() +
            "X-CUSTOM-PROP:THE DATA" + System.lineSeparator() +
            "END:VCALENDAR";
            VCalendar v = VCalendar.parse(content);
            assertEquals(content, v.toString());
    }
    
    @Test
    @Ignore // TODO - Consider ways to collect messages when parsing
    public void canGetErrorMessageFromBadLine()
    {
            String content = "BEGIN:VCALENDAR" + System.lineSeparator() +
            "X-CUSTOM-PROP:THE DATA" + System.lineSeparator() +
            "IGNORE THIS LINE" + System.lineSeparator() +
            "UNKNOWN-PROP:SOMETHING" + System.lineSeparator() +
            "END:VCALENDAR";
            VCalendar v = new VCalendar();
            List<String> m = v.processITIPMessage(content);
            v.addChild(content);
            List<String> messages = null; // v.parseContent(content);
            List<String> expectedMessages = Arrays.asList("VCALENDAR:Unknown line is ignored:IGNORE THIS LINE", "VCALENDAR:Unknown property is ignored:UNKNOWN-PROP:SOMETHING");
            assertEquals(expectedMessages, messages);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void canHandleInvalidCalendarProperty()
    {
        String content = 
       "BEGIN:VCALENDAR" + System.lineSeparator() +
       "CALSCALE:INVALID" + System.lineSeparator() +
       "END:VCALENDAR";

        VCalendar v = VCalendar.parse(content);
    }
}
