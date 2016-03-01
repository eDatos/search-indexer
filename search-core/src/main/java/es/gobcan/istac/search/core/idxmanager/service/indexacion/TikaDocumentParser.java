package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import java.io.IOException;
import java.io.InputStream;

import org.apache.droids.api.ContentEntity;
import org.apache.droids.api.Link;
import org.apache.droids.api.Parse;
import org.apache.droids.api.Parser;
import org.apache.droids.exception.DroidsException;
import org.apache.droids.helper.Loggable;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.InitializingBean;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import es.gobcan.istac.search.core.idxmanager.service.util.ServiceUtils;

public class TikaDocumentParser extends Loggable implements Parser, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public Parse parse(ContentEntity entity, Link link) throws DroidsException, IOException {
        try {
            // PARSE: AUTODETECTION
            return parseAutodetection(entity, link);
        } catch (SAXException ex) {
            throw new DroidsException("Failure parsing document " + link.getId(), ex);
        } catch (TikaException ex) {
            throw new DroidsException("Failure parsing document " + link.getId(), ex);
        }
    }

    /**************************************************************************
     * PRIVADOS
     ***************************************************************************/

    private Parse parseAutodetection(ContentEntity entity, Link link) throws SAXException, TikaException, IOException {

        InputStream instream = entity.obtainContent();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        ContentHandler handler = new BodyContentHandler(ServiceUtils.MAX_TIKA_LIMIT);
        try {
            parser.parse(instream, handler, metadata);
            CustomMtdParseImpl parse = new CustomMtdParseImpl(handler.toString(), null, metadata);
            return parse;
        } finally {
            instream.close();
        }
    }

}
