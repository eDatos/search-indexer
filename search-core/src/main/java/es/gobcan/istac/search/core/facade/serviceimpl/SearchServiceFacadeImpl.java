package es.gobcan.istac.search.core.facade.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;

// Similar in behaviour to IdxServiceAP
@Service("searchServiceFacade")
public class SearchServiceFacadeImpl implements SearchServiceFacade {

    @Autowired
    private RecomendadosIndexerService recomendadosIndexerService;

    @Autowired
    private NucleoIstacIndexerService  nucleoIstacIndexerService;

    public SearchServiceFacadeImpl() {
    }

    @Override
    public void reindexWeb(ServiceContext ctx) {

        // Security
        // SearchSecurityUtils.canReindexWeb(ctx);

        // Create
        // TODO
    }

    @Override
    public void reindexGpe(ServiceContext ctx) throws ServiceExcepcion {

        // Security
        // SearchSecurityUtils.canReindexWeb(ctx);

        // Create
        nucleoIstacIndexerService.reindexarGPEelementos();
    }

    @Override
    public void reindexRecommendedLinks(ServiceContext ctx) throws ServiceExcepcion {
        // Security
        // SearchSecurityUtils.canReindexRecommendedLinks(ctx);

        // Create
        recomendadosIndexerService.reindexarElementosRecomendados();
    }
}
