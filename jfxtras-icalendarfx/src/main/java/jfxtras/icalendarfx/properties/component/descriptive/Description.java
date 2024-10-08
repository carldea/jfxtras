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
package jfxtras.icalendarfx.properties.component.descriptive;

import jfxtras.icalendarfx.components.VAlarm;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.components.VJournal;
import jfxtras.icalendarfx.components.VTodo;
import jfxtras.icalendarfx.parameters.AlternateText;
import jfxtras.icalendarfx.parameters.Language;
import jfxtras.icalendarfx.parameters.NonStandardParameter;
import jfxtras.icalendarfx.properties.PropBaseAltText;
import jfxtras.icalendarfx.properties.ValueType;
import jfxtras.icalendarfx.properties.component.descriptive.Description;
import jfxtras.icalendarfx.properties.component.descriptive.Summary;

/**
 * <h2>3.8.1.5.  Description</h2>

   <p>Property Name:  DESCRIPTION</p>

   <p>Purpose:  This property provides a more complete description of the
      calendar component than that provided by the {@link Summary SUMMARY} property.</p>

   <p>Value Type:  {@link ValueType.TEXT TEXT}</p>

   <p>Property Parameters:  IANA, {@link NonStandardParameter non-standard},
      {@link AlternateText alternate text representation}, and {@link Language language property} parameters can be specified
      on this property.</p>

   <p>Conformance:  The property can be specified in the {@link VEvent VEVENT}, {@link VTodo VTODO},
      {@link VJournal VJOURNAL}, or {@link VAlarm VALARM} calendar components.  The property can be
      specified multiple times only within a {@link VJournal VJOURNAL} calendar
      component.</p>

   <p>Description:  This property is used in the {@link VEvent VEVENT} and {@link VTodo VTODO} to
      capture lengthy textual descriptions associated with the activity.</p>

      <p>This property is used in the {@link VJournal VJOURNAL} calendar component to
      capture one or more textual journal entries.</p>

      <p>This property is used in the {@link VAlarm VALARM} calendar component to
      capture the display text for a DISPLAY category of alarm, and to
      capture the body text for an EMAIL category of alarm.</p>

  <p>Format Definition:  This property is defined by the following notation:
  <ul>
  <li>description
    <ul>
    <li>"DESCRIPTION" descparam ":" text CRLF
    </ul>
  <li>descparam
    <ul>
    <li>The following are OPTIONAL, but MUST NOT occur more than once.
      <ul>
      <li>";" {@link AlternateText Alternate text representation}
      <li>";" {@link Language Language for text}
      </ul>
    <li>The following are OPTIONAL, and MAY occur more than once.
    <ul>
      <li>other-param
        <ul>
        <li>";" {@link NonStandardParameter}
        <li>";" {@link IANAParameter}
        </ul>
    </ul>
  </ul>
  <p>Example:  The following is an example of this property with formatted
      line breaks in the property value:
  <ul>
  <li>DESCRIPTION:Meeting to provide technical review for "Phoenix"<br>
         design.\nHappy Face Conference Room. Phoenix design team<br>
         MUST attend this meeting.\nRSVP to team leader.
  </ul>
  </p>
  RFC 5545                       iCalendar                  September 2009
 */
public class Description extends PropBaseAltText<String,Description>
{
    /** Create deep copy of source Description */
    public Description(Description source)
    {
        super(source);
    }
    
    /** Create Description with property value set to input parameter */
    public Description(String value)
    {
        super();
        setValue(value);
    }

    /** Create default Description with no value set */
    public Description()
    {
        super();
    }
    
    /** Create new Description by parsing unfolded calendar content */
    public static Description parse(String unfoldedContent)
    {
    	return Description.parse(new Description(), unfoldedContent);
    }
}
