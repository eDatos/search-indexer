package es.gobcan.istac.idxmanager.service.test.recomendados;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import es.gobcan.istac.search.core.idxmanager.service.recomendados.Enlace;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.Recomendacion;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.Recomendados;

public class EnlacesRecomendadosParserTest {

    @Test
    public void createXml() {
        Recomendados recomendados = new Recomendados();

        Recomendacion recomendacion = new Recomendacion();

        Enlace enlace = new Enlace();
        enlace.setUrl("http://localhost:8080/pepito");
        enlace.setTitulo("Mi titulo");
        enlace.setDescripcion("descripcion del enlace");

        recomendacion.setPalabras("paro,    Espa�a");

        recomendacion.getEnlaces().add(enlace);

        recomendados.getRecomendaciones().add(recomendacion);

        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        String myXml = xstream.toXML(recomendados);
        String expected = "<recomendados>\n" + "  <recomendacion palabras=\"paro,Espa�a\">\n" + "    <enlace url=\"http://localhost:8080/pepito\" titulo=\"Mi titulo\">\n"
                + "      <descripcion>descripcion del enlace</descripcion>\n" + "    </enlace>\n" + "  </recomendacion>\n" + "</recomendados>";
        assertEquals("No ha generado el xml que deberia", expected, myXml);
    }

    @Test
    public void procesaEnlaces() {
        String myXml = "<recomendados>\n" + "  <recomendacion palabras=\"paro,Espa�a\">\n" + "    <enlace url=\"http://localhost:8080/enlace1\" titulo=\"Mi titulo\">\n"
                + "      <descripcion>descripcion del enlace</descripcion>\n" + "    </enlace>\n" + "    <enlace url=\"http://localhost:8080/enlace2\" titulo=\"Mi titulo 2\">\n"
                + "      <descripcion>descripcion del enlace 2</descripcion>\n" + "    </enlace>\n" + "  </recomendacion>\n" + "</recomendados>";
        XStream xstream = new XStream();
        xstream.processAnnotations(Recomendados.class);
        Recomendados recomendados = new Recomendados();

        xstream.fromXML(myXml, recomendados);

        assertEquals(1, recomendados.getRecomendaciones().size()); // 2 enlaces
    }

    @Test
    public void readEncoding() throws UnsupportedEncodingException, FileNotFoundException {
        String recomendadosFile = "../search-core/src/main/resources/search/recomendados.xml";
        XStream xstream = new XStream();
        xstream.processAnnotations(Recomendados.class);
        Recomendados recomendados = new Recomendados();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(recomendadosFile));
        xstream.fromXML(reader, recomendados);
    }

    @Test
    public void parseXml() {
        String myXml = "<recomendados>\n" + "  <recomendacion palabras=\"paro, Espa�a\">\n" + "    <enlace url=\"http://localhost:8080/pepito\" titulo=\"Mi titulo\">\n"
                + "      <descripcion>descripcion del enlace</descripcion>\n" + "    </enlace>\n" + "  </recomendacion>\n" + "</recomendados>";
        XStream xstream = new XStream();
        xstream.processAnnotations(Recomendados.class);
        Recomendados recomendados = new Recomendados();
        xstream.fromXML(myXml, recomendados);
        // una unica recomendacion
        assertEquals(1, recomendados.getRecomendaciones().size());

        // 2 palabras clave
        assertEquals(2, recomendados.getRecomendaciones().get(0).getListaPalabras().size());
        assertEquals(1, recomendados.getRecomendaciones().get(0).getEnlaces().size());
    }
}
