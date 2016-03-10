package es.gobcan.istac.search.core.facade.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.web.WebIndexerService;
import es.gobcan.istac.search.security.SearchSecurityUtils;

// Similar in behaviour to IdxServiceAP
@Service("searchServiceFacade")
public class SearchServiceFacadeImpl implements SearchServiceFacade {

    @Autowired
    private RecomendadosIndexerService recomendadosIndexerService;

    @Autowired
    private NucleoIstacIndexerService  nucleoIstacIndexerService;

    @Autowired
    private WebIndexerService          webIndexerService;

    public SearchServiceFacadeImpl() {
    }

    @Override
    public void reindexWeb(ServiceContext ctx) throws ServiceExcepcion, MetamacException {

        // Security
        SearchSecurityUtils.canReindexWeb(ctx);

        // Create
        webIndexerService.reindexWeb();
    }

    @Override
    public void reindexGpe(ServiceContext ctx) throws ServiceExcepcion, MetamacException {

        // Security
        SearchSecurityUtils.canReindexGpe(ctx);

        // Create
        nucleoIstacIndexerService.reindexarGPEelementos();
    }

    @Override
    public void reindexRecommendedLinks(ServiceContext ctx) throws ServiceExcepcion, MetamacException {
        // Security
        SearchSecurityUtils.canReindexRecommendedLinks(ctx);

        // Create
        recomendadosIndexerService.reindexarElementosRecomendados();
    }
}
