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
package jfxtras.icalendarfx.parameters;

import java.net.URI;

import jfxtras.icalendarfx.parameters.SentBy;
import jfxtras.icalendarfx.parameters.VParameterBase;
import jfxtras.icalendarfx.utilities.StringConverter;
import jfxtras.icalendarfx.utilities.StringConverters;

/**
 * SENT-BY
 * Sent By
 * RFC 5545, 3.2.18, page 27
 * 
 * To specify the calendar user that is acting on behalf of
 *  the calendar user specified by the property.
 * 
 * Example:
 * ORGANIZER;SENT-BY="mailto:sray@example.com":mailto:
 *  jsmith@example.com
 * 
 * @author David Bal
 *
 */
public class SentBy extends VParameterBase<SentBy, URI>
{
	private static final StringConverter<URI> CONVERTER = StringConverters.uriConverterWithQuotes();

    public SentBy(URI uri)
    {
        super(uri, CONVERTER);
    }

    public SentBy()
    {
        super(CONVERTER);
    }
    
    public SentBy(SentBy source)
    {
        super(source, CONVERTER);
    }
    
    public static SentBy parse(String content)
    {
    	return SentBy.parse(new SentBy(), content);
    }
}
