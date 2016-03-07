package pruebas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import es.gobcan.istac.idxmanager.service.indexacion.CustomMtdParseImpl;

public class TestTikaExtract {

    public static void main(String[] args) throws IOException, SAXException, TikaException {

        InputStream instream = new FileInputStream(new File("K:/0c7cfa0a-38df-40a1-963f-a9f29b0b553f.pdf"));
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        ContentHandler handler = new BodyContentHandler(1200000);
        try {
            parser.parse(instream, handler, metadata);
            CustomMtdParseImpl parse = new CustomMtdParseImpl(handler.toString(), null, metadata);
            System.out.println("TERMINADO");
            System.out.println(handler.toString());
        } finally {
            instream.close();
        }
    }
}
