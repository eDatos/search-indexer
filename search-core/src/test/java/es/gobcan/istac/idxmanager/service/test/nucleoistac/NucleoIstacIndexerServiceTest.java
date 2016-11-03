package es.gobcan.istac.idxmanager.service.test.nucleoistac;

import java.util.Arrays;

import org.apache.log4j.helpers.ISO8601DateFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain;
import es.gobcan.istac.idxmanager.service.test.util.TestBase;
import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;

public class NucleoIstacIndexerServiceTest extends TestBase {

    @Autowired
    private NucleoIstacIndexerService nucleoIstacIndexerService = null;

    @Test
    public void testInicializarDatos() throws Exception {
        nucleoIstacIndexerService.borrarTodo();
        for (int i = 0; i < 100; i++) {
            NucleoMetadatos nucleoIstacDomain = new NucleoMetadatos();

            nucleoIstacDomain.setIdentifierUniversal("C00027A_000" + i);

            nucleoIstacDomain.setTitle("Este es mi titulito de prueba. " + i);
            nucleoIstacDomain.setSubtitle(Arrays.asList("subtitulo primero 1", "subtitulo segundo 2", "subtitulo tercero 3"));
            nucleoIstacDomain.setTitleAlternative(Arrays.asList("titulo alternativo primero 1", "titulo alternativo segundo 2", "titulo alternativo tercero 3"));

            if (i < 20) {
                nucleoIstacDomain.setSubjectAreas(Arrays.asList("Encuesta de poblacion activa (EPA)", "Demanda turística: Turistas y pasajeros", "Población"));
                nucleoIstacDomain.setSubjectCodes(Arrays.asList("1", "2", "3"));
                nucleoIstacDomain.setSurveyCode("123456");
                nucleoIstacDomain.setSurveyTitle("Titulo de survey");
                nucleoIstacDomain.setSurveyAlternative("Otra cosita para el survey");
                nucleoIstacDomain.setCoverageSpatial(Arrays.asList("Nuts", "Provincias"));
                nucleoIstacDomain.setCoverageSpatialCodes(Arrays.asList("A123", "B456"));
                nucleoIstacDomain.setCoverageTemporal(Arrays.asList("días", "segundos"));
                nucleoIstacDomain.setCoverageTemporalCodes(Arrays.asList("d30", "s12"));
            } else if (i >= 20 && i < 60) {
                nucleoIstacDomain.setSubjectAreas(Arrays.asList("Demanda turística: Turistas y pasajeros", "Población"));
                nucleoIstacDomain.setSubjectCodes(Arrays.asList("1", "2", "3"));
                nucleoIstacDomain.setSurveyCode("123456");
                nucleoIstacDomain.setSurveyTitle("Este es otro titulo de survey");
                nucleoIstacDomain.setSurveyAlternative("Otra cosita para el survey");
                nucleoIstacDomain.setCoverageSpatial(Arrays.asList("Municipios"));
                nucleoIstacDomain.setCoverageSpatialCodes(Arrays.asList("A123"));
                nucleoIstacDomain.setCoverageTemporal(Arrays.asList("horas", "minutos"));
                nucleoIstacDomain.setCoverageTemporalCodes(Arrays.asList("h30", "m12"));
            } else {
                nucleoIstacDomain.setSubjectAreas(Arrays.asList("Población"));
                nucleoIstacDomain.setSubjectCodes(Arrays.asList("1", "2", "3"));
                nucleoIstacDomain.setSurveyCode("123456");
                nucleoIstacDomain.setSurveyTitle("Mi otro tiulo de survey");
                nucleoIstacDomain.setSurveyAlternative("Otra cosita para el survey");
                nucleoIstacDomain.setCoverageSpatial(Arrays.asList("Municipios", "Distritos", "Secciones"));
                nucleoIstacDomain.setCoverageSpatialCodes(Arrays.asList("A123", "B456", "C456"));
                nucleoIstacDomain.setCoverageTemporal(Arrays.asList("días", "meses"));
                nucleoIstacDomain.setCoverageTemporalCodes(Arrays.asList("d30", "m12"));
            }

            nucleoIstacDomain.setDescription(Arrays.asList(
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.",
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim."));
            nucleoIstacDomain.setAbstract(Arrays.asList("Esto es un texto abstracto de complemento"));
            nucleoIstacDomain.setKeywords(Arrays.asList("tenerife", "gran canaria", "la gomera", "el hierro", "la palma", "fuerteventura", "lanzarote"));

            if (i < 56) {
                nucleoIstacDomain.setType(Arrays.asList(TypeNMDomain.DATASET_DSC.getSiglas()));
            } else {
                nucleoIstacDomain.setType(Arrays.asList(TypeNMDomain.COLLECTION_DSP.getSiglas()));
            }

            nucleoIstacDomain.setLastUpdate(ISO8601DateFormat.getInstance().getCalendar().getTime());

            nucleoIstacIndexerService.indexarElementoGPE(nucleoIstacDomain, null);
        }
        nucleoIstacIndexerService.commitANDoptimize();
    }

    @Test
    @Ignore
    public void testReindexarFromGPE() throws Exception {
        nucleoIstacIndexerService.reindexarGPEelementos(mockServiceContextWithoutPrincipal());
    }

}
