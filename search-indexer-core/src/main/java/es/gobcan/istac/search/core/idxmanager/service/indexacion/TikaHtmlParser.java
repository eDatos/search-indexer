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
package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.droids.api.ContentEntity;
import org.apache.droids.api.Link;
import org.apache.droids.api.Parse;
import org.apache.droids.api.Parser;
import org.apache.droids.exception.DroidsException;
import org.apache.droids.helper.Loggable;
import org.apache.droids.parse.html.LinkExtractor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaHtmlParser extends Loggable implements Parser {

    private Map<String, String> elements = null;

    public Map<String, String> getElements() {
        if (elements == null) {
            elements = new HashMap<String, String>();
        }
        return elements;
    }

    public void setElements(Map<String, String> elements) {
        this.elements = elements;
    }

    @Override
    public Parse parse(ContentEntity entity, Link link) throws IOException, DroidsException {
        Metadata metadata = new Metadata();
        LinkExtractor extractor = new LinkExtractor(link, elements);
        InputStream instream = entity.obtainContent();
        ContentHandler body = new BodyContentHandler();
        try {
            new HtmlParser().parse(instream, new TeeContentHandler(body, extractor), metadata, new ParseContext());
            return new CustomMtdParseImpl(body.toString(), extractor.getLinks(), metadata);

        } catch (SAXException ex) {
            throw new DroidsException("Failure parsing document " + link.getId(), ex);
        } catch (TikaException ex) {
            throw new DroidsException("Failure parsing document " + link.getId(), ex);
        } finally {
            instream.close();
        }
    }

}
