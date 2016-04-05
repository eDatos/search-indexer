package es.gobcan.istac.search.core.idxmanager.service.nucleoistac;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

import es.gobcan.istac.idxmanager.domain.alfresco.ContenidoOperacion;
import es.gobcan.istac.idxmanager.domain.alfresco.ContenidoOperacion.RecursoInfo;
import es.gobcan.istac.idxmanager.domain.alfresco.NucleoMetadatosPublicado;
import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.alfresco.ConexionAlfrescoService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerServiceImpl;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

public class GpeReIndexerThread implements Runnable {

    protected static Log            log = LogFactory.getLog(GpeReIndexerThread.class);

    private ConexionAlfrescoService conexionAlfrescoService;

    private ServiceContext          ctx;

    public GpeReIndexerThread(ConexionAlfrescoService conexion, ServiceContext ctx) {
        conexionAlfrescoService = conexion;
        this.ctx = ctx;
    }

    @Override
    public void run() {

        try {
            _getIndexacionStatus().setIdxGPEStatus(IndexacionStatusDomain.INDEXANDO);
            log.info("Reindexación GPE iniciada...");

            // 0. Se eliminan todos los recursos GPE publicados
            _getNucleoIstacIndexerService().borrar(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_GPE.getSiglas());

            // 3. Obtencion del rercurso
            List<ContenidoOperacion> recursosList = conexionAlfrescoService.obtenerRecursosPublicados();

            // 5. Comenzamos a indexar todos los px y publicaciones estadisticas recibidas
            // Por cada Operacion
            for (ContenidoOperacion contenidoOperacion : recursosList) {
                // Indexado de PXs
                for (RecursoInfo recInfo : contenidoOperacion.getRecursos()) {
                    // Recibir nodo de alfresco
                    NucleoMetadatosPublicado nucleoMetadatosPublicado = conexionAlfrescoService.obtenerNodoNucleoMetadatosUltimaVersionVisible(recInfo.getId());
                    if (nucleoMetadatosPublicado != null) { // Puede ser null si esta publicado pero no visible
                        _getNucleoIstacIndexerService().indexarElementoGPE(nucleoMetadatosPublicado, recInfo.getTipoRecurso());
                    }
                }
            }

            _getSolrService().commitANDoptimize();

            _getIndexacionStatus().setIdxGPEStatus(IndexacionStatusDomain.PARADO);
            log.info("Reindexación GPE finalizada.");

            // Al finalizar realiza un COMMIT y OPTIMIZE
            getRecomendadosIndexerService().reindexRecommendedKeywords(ctx);
        } catch (Exception e) {
            _getIndexacionStatus().setIdxGPEStatus(IndexacionStatusDomain.FALLO);
            log.error("Error: NucleoIstacIndexerServiceImpl:reindexarGPEelementos " + e);
        }
    }
    /**************************************************************************
     * METODOS PRIVADOS
     *************************************************************************/

    private SolrService _getSolrService() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (SolrService) ctx.getBean("solr");
    }

    private NucleoIstacIndexerService _getNucleoIstacIndexerService() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (NucleoIstacIndexerService) ctx.getBean("nucleoIstacIndexerServiceImpl");
    }

    private IndexationStatus _getIndexacionStatus() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return (IndexationStatus) ctx.getBean("indexationStatus");
    }

    private RecomendadosIndexerServiceImpl getRecomendadosIndexerService() {
        return (RecomendadosIndexerServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("recomendadosIndexerServiceImpl");
    }

}
