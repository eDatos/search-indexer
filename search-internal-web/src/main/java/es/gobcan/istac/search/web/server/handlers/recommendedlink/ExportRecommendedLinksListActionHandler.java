package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksListResult;

@Component
public class ExportRecommendedLinksListActionHandler extends SecurityActionHandler<ExportRecommendedLinksListAction, ExportRecommendedLinksListResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public ExportRecommendedLinksListActionHandler() {
        super(ExportRecommendedLinksListAction.class);
    }

    @Override
    public ExportRecommendedLinksListResult executeSecurityAction(ExportRecommendedLinksListAction action) throws ActionException {
        try {
            String fileName = searchServiceFacade.exportRecommendedLinks(ServiceContextHolder.getCurrentServiceContext(), action.getRecommendedLinkIds());
            return new ExportRecommendedLinksListResult(fileName);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}