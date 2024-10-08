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
package jfxtras.icalendarfx.properties.component.change;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.components.VJournal;
import jfxtras.icalendarfx.components.VTodo;
import jfxtras.icalendarfx.properties.VPropertyBase;
import jfxtras.icalendarfx.properties.component.change.Sequence;

/**
 * SEQUENCE
 * Sequence Number
 * RFC 5545, 3.8.7.4, page 138
 * 
 * This property defines the revision sequence number of the
 * calendar component within a sequence of revisions.
 * 
 * Example:  The following is an example of this property for a calendar
 * component that was just created by the "Organizer":
 * SEQUENCE:0
 * 
 * The following is an example of this property for a calendar
 * component that has been revised two different times by the "Organizer":
 * SEQUENCE:2
 * 
  <pre>
  From RFC 5546
  
  The "SEQUENCE" property is used by the "Organizer" to indicate
   revisions to the calendar component.  When the "Organizer" makes
   changes to one of the following properties, the sequence number MUST
   be incremented:

   o  "DTSTART"

   o  "DTEND"

   o  "DURATION"

   o  "DUE"

   o  "RRULE"

   o  "RDATE"

   o  "EXDATE"

   o  "STATUS"

   In addition, changes made by the "Organizer" to other properties MAY
   also require the sequence number to be incremented.  The "Organizer"
   CUA MUST increment the sequence number whenever it makes changes to
   properties in the calendar component that the "Organizer" deems will

   jeopardize the validity of the participation status of the
   "Attendees".  For example, changing the location of a meeting from
   one location to another distant location could effectively impact the
   participation status of the "Attendees".

   Depending on the "METHOD", the "SEQUENCE" property MUST follow these
   rules in the context of iTIP:

   o  For the "PUBLISH" and "REQUEST" methods, the "SEQUENCE" property
      value is incremented according to the rules stated above.

   o  The "SEQUENCE" property value MUST be incremented each time the
      "Organizer" uses the "ADD" or "CANCEL" methods.

   o  The "SEQUENCE" property value MUST NOT be incremented when using
      "REPLY", "REFRESH", "COUNTER", "DECLINECOUNTER", or when sending a
      delegation "REQUEST".

   In some circumstances, the "Organizer" may not have received
   responses to the final revision sent out.  In this situation, the
   "Organizer" may wish to send an update "REQUEST" and set "RSVP=TRUE"
   for all "Attendees" so that current responses can be collected.

   The value of the "SEQUENCE" property contained in a response from an
   "Attendee" may not always match the "Organizer's" revision.
   Implementations may choose to have the CUA indicate to the CU that
   the response is to an iCalendar object that has been revised, and
   allow the CU to decide whether or not to accept the response.

   Whilst a change in sequence number is indicative of a significant
   change to a previously scheduled item, "Attendee" CUAs SHOULD NOT
   rely solely on a change in sequence number as a means of detecting a
   significant change.  Instead, CUAs SHOULD compare the old and new
   versions of the calendar components, determine the exact nature of
   the changes, and make decisions -- possibly based on "Calendar User"
   preferences -- as to whether the user needs to be explicitly informed
   of the change.
   </pre>
 * 
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 */
public class Sequence extends VPropertyBase<Integer, Sequence>
{
    public Sequence(Integer value)
    {
        super(value);
    }
    
    public Sequence(Sequence source)
    {
        super(source);
    }
    
    public Sequence()
    {
        super(0); // default is 0
    }
    
    @Override
    public void setValue(Integer value)
    {
        if (value >= 0)
        {
            super.setValue(value);
        } else
        {
            throw new IllegalArgumentException(name() + " must be greater than or equal to zero");
        }
    }

    public static Sequence parse(String content)
    {
    	return Sequence.parse(new Sequence(), content);
    }
}
