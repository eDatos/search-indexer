package es.gobcan.istac.idxmanager.service.test.nucleoistac;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import es.gobcan.istac.idxmanager.domain.alfresco.ContenidoOperacion;
import es.gobcan.istac.idxmanager.domain.alfresco.NucleoMetadatosPublicado;

public class JacksonServiceTest {

    @Test
    public void testJacksonContenidoOperacion() throws Exception {

        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        InputStream inputStream = JacksonServiceTest.class.getResourceAsStream("/json/listaPublicados.json");

        List<ContenidoOperacion> userData = mapper.readValue(inputStream, new TypeReference<List<ContenidoOperacion>>() {});
        assertTrue(!userData.isEmpty());

        // ContenidoOperacion pojo = new ContenidoOperacion();
        // pojo.setId("sssdsds");
        // pojo.setOperacion("op");
        //
        // PxInfo px = new PxInfo();
        // px.setId("aa");
        // px.setPx("px");
        //
        // PublicacionInfo pub = new PublicacionInfo();
        // pub.setId("bb");
        // pub.setPublicacion("pub");
        //
        // List listPX = new ArrayList<PxInfo>();
        // listPX.add(px);
        //
        // List listPUB = new ArrayList<PublicacionInfo>();
        // listPUB.add(pub);
        //
        // pojo.setPublicaciones(listPUB);
        // pojo.setPxs(listPX);
        //
        // List<ContenidoOperacion> listaMadre = new ArrayList<ContenidoOperacion>();
        // listaMadre.add(pojo);
        // listaMadre.add(pojo);
        //
        // mapper.writeValue(new File("salida.json"), listaMadre);

    }

    @Test
    public void testJacksonNucleoPublicado() throws Exception {

        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        InputStream inputStream = JacksonServiceTest.class.getResourceAsStream("/json/nucleoPublicado.json");

        NucleoMetadatosPublicado userData = mapper.readValue(inputStream, NucleoMetadatosPublicado.class);

        assertNotNull(userData);
    }

}
