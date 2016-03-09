package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

public class RecomendadosReIndexerThread implements Runnable {

    protected static Log log = LogFactory.getLog(RecomendadosReIndexerThread.class);

    public RecomendadosReIndexerThread() {

    }

    @Override
    public void run() {

        try {
            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.INDEXANDO);
            log.info("Reindexación Recomendados iniciada...");

            // 0. Se eliminan todos los recursos GPE publicados
            _getRecomendadosIndexerService().borrar(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_PATROCINADO.getSiglas());

            // 1. Obtener el fichero xml con patrocinados
            String recomendadosFile = getConfigurationService().retrieveIndexationRecommendedLinks();

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

    private SearchConfigurationService getConfigurationService() {
        return (SearchConfigurationService) ApplicationContextProvider.getApplicationContext().getBean("configurationService");
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
