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

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.components.VTodo;
import jfxtras.icalendarfx.properties.VPropertyBase;
import jfxtras.icalendarfx.properties.component.descriptive.Priority;

/**
 * PRIORITY
 * RFC 5545 iCalendar 3.8.1.9. page 89
 * 
 * This property defines the relative priority for a calendar component.
 * 
 * A value of 0 specifies an undefined priority.  A value of 1
 * is the highest priority.  A value of 2 is the second highest
 * priority.  Subsequent numbers specify a decreasing ordinal
 * priority.  A value of 9 is the lowest priority.
 *     
 * Example:
 * The following is an example of a property with the highest priority:
 * PRIORITY:1
 *  
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 */
public class Priority extends VPropertyBase<Integer, Priority>
{
    public Priority(Priority source)
    {
        super(source);
    }
    
    public Priority(Integer value)
    {
        super(value);
    }
    
    public Priority()
    {
        super();
    }

    @Override
    public void setValue(Integer value)
    {
        if ((value < 0) || (value > 9))
        {
            throw new IllegalArgumentException("Priority must between 0 and 9");
        }
        super.setValue(value);
    }
    
    @Override
    public boolean isValid()
    {
        return ((getValue() < 0) || (getValue() > 9)) ? false : super.isValid();
    }

    public static Priority parse(String content)
    {
    	return Priority.parse(new Priority(), content);
    }
}
