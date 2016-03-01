package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.arte.acom.configuration.ConfigurationServiceImpl;
import com.thoughtworks.xstream.XStream;

import es.gobcan.istac.idxmanager.domain.dom.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;
import es.gobcan.istac.search.core.idxmanager.service.util.ApplicationContextProvider;

public class RecomendadosReIndexerThread implements Runnable {

    protected static Log log = LogFactory.getLog(RecomendadosReIndexerThread.class);

    public RecomendadosReIndexerThread() {

    }

    @Override
    public void run() {

        try {
            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.INDEXANDO);

            Properties properties = ((ConfigurationServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("configurationService")).getProperties();

            // 0. Se eliminan todos los recursos GPE publicados
            _getRecomendadosIndexerService().borrar(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_PATROCINADO.getSiglas());

            // 1. Obtener el fichero xml con patrocinados
            String recomendadosFile = properties.getProperty("istac.idxmanager.indexacion.recomendados");

            // 2. Parsear el xml
            Recomendados recomendados = new Recomendados();
            XStream xstream = new XStream();
            xstream.processAnnotations(Recomendados.class);
            Resource resource = ApplicationContextProvider.getApplicationContext().getResource(recomendadosFile);
            xstream.fromXML(resource.getInputStream(), recomendados);

            // 3. Tratamos los enlaces patrocinados
            int index = 0;
            for (Recomendacion recom : recomendados.getRecomendaciones()) {
                recom.setId("SUGGESTED" + index++);
                _getRecomendadosIndexerService().indexarElementoRecomendado(recom);
            }

            _getSolrService().commitANDoptimize();

            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.PARADO);
            log.info("Reindexación Recomendados finalizada.");
        } catch (Exception e) {
            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.FALLO);

            log.error("Error: PatrocinadosIndexerServiceImpl:reindexarElementosPatrocinados " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_RECOMENDADOS);
        }
    }

    /**************************************************************************
     * METODOS PRIVADOS
     *************************************************************************/

    private SolrService _getSolrService() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (SolrService) ctx.getBean("solr");
    }

    private RecomendadosIndexerService _getRecomendadosIndexerService() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (RecomendadosIndexerService) ctx.getBean("recomendadosIndexerServiceImpl");
    }

    private IndexationSuggestedStatus _getIndexacionStatus() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (IndexationSuggestedStatus) ctx.getBean("indexationSuggestedStatus");
    }

}
