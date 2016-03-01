/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.gobcan.istac.idxmanager.service.indexacion;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler that write the events in a StringBuffer to save a XML
 * representation.
 */
public class EchoHandler extends DefaultHandler {

    protected String encoding = null;

    private StringBuffer xmlBuffer = null;

    private ByteArrayInputStream inputStream = null;

    private byte[] bytes;

    /**
     * Set the encoding for the XML output.
     *
     * @param encoding
     */
    public EchoHandler(String encoding) {
        if (null != encoding && !" ".equals(encoding)) {
            this.encoding = encoding;
        } else {
            this.encoding = "UTF-8";
        }
    }

    /*
     * Receive notification of the beginning of a document. (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        this.xmlBuffer = new StringBuffer();
        this.xmlBuffer.append("<?xml version=\"1.0\" encoding=\"" + this.encoding + "\"?>\r\n");
    }

    /*
     * Receive notification of the end of a document. (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        try {
            this.setResult();
        } catch (UnsupportedEncodingException e) {
            throw new SAXException(e);
        }
    }

    /*
     * Receive notification of the beginning of an element.
     * @param uri The Namespace URI, or the empty string if the element has no
     * Namespace URI or if Namespace processing is not being performed. @param loc
     * The local name (without prefix), or the empty string if Namespace
     * processing is not being performed. @param raw The raw XML 1.0 name (with
     * prefix), or the empty string if raw names are not available. @param atts
     * The attributes attached to the element. If there are no attributes, it
     * shall be an empty Attributes object.
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String loc, String raw, Attributes atts) throws SAXException {
        this.xmlBuffer.append("\r\n<" + raw);
        for (int i = 0; i < atts.getLength(); i++) {
            this.xmlBuffer.append(" ");
            this.xmlBuffer.append(atts.getQName(i));
            this.xmlBuffer.append("=\"");
            String value = atts.getValue(i);

            this.xmlBuffer.append(value);
            this.xmlBuffer.append("\"");
        }
        this.xmlBuffer.append(">\r\n");
    }

    /*
     * Receive notification of the end of an element.
     * @param uri The Namespace URI, or the empty string if the element has no
     * Namespace URI or if Namespace processing is not being performed. @param loc
     * The local name (without prefix), or the empty string if Namespace
     * processing is not being performed. @param raw The raw XML 1.0 name (with
     * prefix), or the empty string if raw names are not available.
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String loc, String raw) throws SAXException {
        this.xmlBuffer.append("\r\n</" + raw + ">\r\n");
    }

    /*
     * Receive notification of character data.
     * @param ch The characters from the XML document. @param start The start
     * position in the array. @param length The number of characters to read from
     * the array.
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        for (int i = 0; i < length; i++) {
            char c = ch[start + i];
            if (c == '&') {
                this.xmlBuffer.append("&amp;");
            } else if (c == '<') {
                this.xmlBuffer.append("&lt;");
            } else if (c == '>') {
                this.xmlBuffer.append("&gt;");
            } else {
                this.xmlBuffer.append(c);
            }
        }
    }

    /*
     * Receive notification of ignorable whitespace in element content.
     * @param ch The characters from the XML document. @param start The start
     * position in the array. @param length The number of characters to read from
     * the array.
     * @see org.xml.sax.helpers.DefaultHandler#ignorableWhitespace(char[], int,
     * int)
     */
    @Override
    public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {
        this.characters(ch, start, length);
    }

    /*
     * Receive notification of a processing instruction.
     * @param target The processing instruction target. @param data The processing
     * instruction data, or null if none was supplied.
     * @see org.xml.sax.helpers.DefaultHandler#processingInstruction(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        this.xmlBuffer.append("<?" + target + " " + data + "?>");
    }

    /*
     * Receive notification of a skipped entity.
     * @param name The name of the skipped entity. If it is a parameter entity,
     * the name will begin with '%'.
     * @see org.xml.sax.helpers.DefaultHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String name) throws SAXException {
        this.xmlBuffer.append("&" + name + ";");
    }

    private void setResult() throws UnsupportedEncodingException {
        try {
            this.bytes = this.xmlBuffer.toString().getBytes(this.encoding);
            this.inputStream = new ByteArrayInputStream(this.bytes);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException();
        }
    }

    /**
     * Return the underlying input stream
     *
     * @return the input stream
     */
    public ByteArrayInputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * Return the underlying input stream
     *
     * @return the input stream
     */
    public byte[] getBytes() {
        return this.bytes;
    }

    /**
     * String representation of the data.
     */
    @Override
    public String toString() {
        return this.xmlBuffer.toString();
    }
}