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
package jfxtras.icalendarfx.itip;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VComponent;
import jfxtras.icalendarfx.components.VDisplayable;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.components.VTimeZone;
import jfxtras.icalendarfx.properties.calendar.Method.MethodType;
import jfxtras.icalendarfx.properties.component.relationship.Attendee;
import jfxtras.icalendarfx.properties.component.relationship.Organizer;
import jfxtras.icalendarfx.properties.component.relationship.UniqueIdentifier;

/** 
 * 
 * <h2>3.2.1.  PUBLISH</h2>

   <p>The {@link MethodType#Publish PUBLISH} method in a {@link VEvent VEVENT} calendar component is an
   unsolicited posting of an iCalendar object.  Any CU may add published
   components to their calendar.  The {@link Organizer} MUST be present in a
   published iCalendar component.  {@link Attendee Attendees} MUST NOT be present.  Its
   expected usage is for encapsulating an arbitrary event as an
   iCalendar object.  The {@link Organizer} may subsequently update (with
   another {@link MethodType#Publish PUBLISH} method), add instances to (with an {@link MethodType#Add ADD} method),
   or cancel (with a {@link MethodType#Cancel CANCEL} method) a previously published {@link VEvent VEVENT}
   calendar component.</p>

   <p>This method type is an iCalendar object that conforms to the
   following property constraints:</p>
<pre>
             +----------------------------------------------+
             | Constraints for a METHOD:PUBLISH of a VEVENT |
             +----------------------------------------------+

   +--------------------+----------+-----------------------------------+
   | Component/Property | Presence | Comment                           |
   +--------------------+----------+-----------------------------------+
   | METHOD             | 1        | MUST equal PUBLISH.               |
   |                    |          |                                   |
   | VEVENT             | 1+       |                                   |
   |   DTSTAMP          | 1        |                                   |
   |   DTSTART          | 1        |                                   |
   |   ORGANIZER        | 1        |                                   |
   |   SUMMARY          | 1        | Can be null.                      |
   |   UID              | 1        |                                   |
   |   RECURRENCE-ID    | 0 or 1   | Only if referring to an instance  |
   |                    |          | of a recurring calendar           |
   |                    |          | component.  Otherwise, it MUST    |
   |                    |          | NOT be present.                   |
   |   SEQUENCE         | 0 or 1   | MUST be present if value is       |
   |                    |          | greater than 0; MAY be present if |
   |                    |          | 0.                                |
   |   ATTACH           | 0+       |                                   |
   |   CATEGORIES       | 0+       |                                   |
   |   CLASS            | 0 or 1   |                                   |
   |   COMMENT          | 0+       |                                   |
   |   CONTACT          | 0 or 1   |                                   |
   |   CREATED          | 0 or 1   |                                   |
   |   DESCRIPTION      | 0 or 1   | Can be null.                      |
   |   DTEND            | 0 or 1   | If present, DURATION MUST NOT be  |
   |                    |          | present.                          |
   |   DURATION         | 0 or 1   | If present, DTEND MUST NOT be     |
   |                    |          | present.                          |
   |   EXDATE           | 0+       |                                   |
   |   GEO              | 0 or 1   |                                   |
   |   LAST-MODIFIED    | 0 or 1   |                                   |
   |   LOCATION         | 0 or 1   |                                   |
   |   PRIORITY         | 0 or 1   |                                   |
   |   RDATE            | 0+       |                                   |
   |   RELATED-TO       | 0+       |                                   |
   |   RESOURCES        | 0+       |                                   |
   |   RRULE            | 0 or 1   |                                   |
   |   STATUS           | 0 or 1   | MAY be one of                     |
   |                    |          | TENTATIVE/CONFIRMED/CANCELLED.    |
   |   TRANSP           | 0 or 1   |                                   |
   |   URL              | 0 or 1   |                                   |
   |   IANA-PROPERTY    | 0+       |                                   |
   |   X-PROPERTY       | 0+       |                                   |
   |   ATTENDEE         | 0        |                                   |
   |   REQUEST-STATUS   | 0        |                                   |
   |                    |          |                                   |
   |   VALARM           | 0+       |                                   |
   |                    |          |                                   |
   | VFREEBUSY          | 0        |                                   |
   |                    |          |                                   |
   | VJOURNAL           | 0        |                                   |
   |                    |          |                                   |
   | VTODO              | 0        |                                   |
   |                    |          |                                   |
   | VTIMEZONE          | 0+       | MUST be present if any date/time  |
   |                    |          | refers to a timezone.             |
   |                    |          |                                   |
   | IANA-COMPONENT     | 0+       |                                   |
   | X-COMPONENT        | 0+       |                                   |
   +--------------------+----------+-----------------------------------+
   </pre>
 * @author David Bal   
 */
public class ProcessPublish implements Processable
{
    @Override
    public List<String> process(VCalendar mainVCalendar, VCalendar iTIPMessage)
    {
        List<String> log = new ArrayList<>();
        iTIPMessage.childrenUnmodifiable()
        	.stream()
        	.filter(c -> c instanceof VComponent)
        	.map(c -> (VComponent) c)
        	.forEach(c ->
        {
            if (c instanceof VDisplayable)
            {
                VDisplayable<?> vDisplayable = ((VDisplayable<?>) c);
                final boolean hasOrganizer = vDisplayable.getOrganizer() != null;
                // TODO - LOG DEFECTS - DON'T THROW EXCEPTIONS
                if (! hasOrganizer) log.add("WARNING: According to RFC 5546, a PUBLISH method MUST contain the ORGANIZER property and it's absent. " + c.getClass().getSimpleName() + " with UID:" + vDisplayable.getUniqueIdentifier().getValue() + " is being processed anyway.");
                final boolean hasNoAttendees = vDisplayable.getAttendees() == null;
                if (! hasNoAttendees) log.add("WARNING: According to RFC 5546, a PUBLISH MUST NOT contain the ATTENDEE property yet it's exists. " + c.getClass().getSimpleName() + " with UID:" + vDisplayable.getUniqueIdentifier().getValue() + " is being processed anyway.");
                final int newSequence = (vDisplayable.getSequence() == null) ? 0 : vDisplayable.getSequence().getValue();
                boolean isNewSequenceHigher = true;
                UniqueIdentifier uid = vDisplayable.getUniqueIdentifier();
                
                final List<VDisplayable<?>> relatedVComponents;
                List<? extends VComponent> list = mainVCalendar.getVComponents(vDisplayable);
                if (list == null)
                {
                	relatedVComponents = null;
                } else
                {
	                relatedVComponents = mainVCalendar.getVComponents(vDisplayable)
	                	.stream()
	                	.filter(v -> v instanceof VDisplayable)
	            		.map(v -> (VDisplayable<?>) v)
	            		.filter(v -> v.getUniqueIdentifier().equals(uid))
	            		.collect(Collectors.toList());
                }
                final Temporal recurrenceID = (vDisplayable.getRecurrenceId() != null) ? vDisplayable.getRecurrenceId().getValue() : null;

                // check for previous match to remove it
                if (relatedVComponents != null)
                {
                    /* if new has recurrence id:
                     *      if old has matching recurrence-id then replace it
                     *      if old doesn't have recurrence-id, add component as new recurrence
                     * if new doesn't have recurrence id, but match exists, replace match
                     * If no match then just add - can't have recurrence id 
                     */
                    VDisplayable<?> oldMatchingVComponent = relatedVComponents
                            .stream()
                            .filter(v -> {
                                Temporal mRecurrenceID = (v.getRecurrenceId() != null) ? v.getRecurrenceId().getValue() : null;
                                return Objects.equals(recurrenceID, mRecurrenceID);
                            })
                            .findAny()
                            .orElseGet(() -> null);
                    if (oldMatchingVComponent != null)
                    {
                        int oldSequence = (oldMatchingVComponent.getSequence() == null) ? 0 : oldMatchingVComponent.getSequence().getValue();
                        isNewSequenceHigher = newSequence > oldSequence || (newSequence == 0 && oldSequence == 0);
                        // SEQUENCE INCREASE REQUIREMENT ONLY APPLIES FOR PARENTS - NOT RECURRENCES THAT ONLY HAVE RECURRENCE-ID CHANGED
                        if (! isNewSequenceHigher) throw new IllegalArgumentException("Can't process PUBLISH method: SEQUENCY property MUST be higher than previously published component (new=" + newSequence + " old=" + oldSequence + ")");
                        if (isNewSequenceHigher)
                        {
                        	mainVCalendar.removeChild(oldMatchingVComponent);
                        }
                    }
                }
            	mainVCalendar.addChild(c);

                log.add("SUCCESS: added " + c.getClass().getSimpleName() + " with UID:" + vDisplayable.getUniqueIdentifier().getValue());
                
                // Remove orphaned recurrence children
                // 	TODO  - IS THIS NECESSARY???
                List<VDisplayable<?>> orphanedChildren = vDisplayable.orphanedRecurrenceChildren();
                if (! orphanedChildren.isEmpty())
                {
                    vDisplayable.calendarList().removeAll(orphanedChildren);
                }
            } else if (c instanceof VTimeZone)
            {
                mainVCalendar.getVTimeZones().add((VTimeZone) c);
            } else
            { // non-displayable VComponents (only VFREEBUSY has UID)
                log.add("Can't process non-displayble component method (not implemented):" + System.lineSeparator() + c.toString());
            }
        });
//        System.out.println("log:" + log);
        return log;
    }

}
