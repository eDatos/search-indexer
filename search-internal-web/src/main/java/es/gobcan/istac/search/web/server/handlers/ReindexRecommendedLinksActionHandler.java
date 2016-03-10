package es.gobcan.istac.search.web.server.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.web.shared.ReindexRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.ReindexRecommendedLinksResult;

@Component
public class ReindexRecommendedLinksActionHandler extends SecurityActionHandler<ReindexRecommendedLinksAction, ReindexRecommendedLinksResult> {

    private static Logger       logger = Logger.getLogger(ReindexRecommendedLinksActionHandler.class.getName());

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public ReindexRecommendedLinksActionHandler() {
        super(ReindexRecommendedLinksAction.class);
    }

    @Override
    public ReindexRecommendedLinksResult executeSecurityAction(ReindexRecommendedLinksAction action) throws ActionException {
        try {
            searchServiceFacade.reindexRecommendedLinks(ServiceContextHolder.getCurrentServiceContext());
        } catch (ServiceExcepcion e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        return new ReindexRecommendedLinksResult();
    }
}
