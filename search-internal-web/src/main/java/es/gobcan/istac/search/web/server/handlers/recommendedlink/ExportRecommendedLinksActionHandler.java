package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksResult;

@Component
public class ExportRecommendedLinksActionHandler extends SecurityActionHandler<ExportRecommendedLinksAction, ExportRecommendedLinksResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public ExportRecommendedLinksActionHandler() {
        super(ExportRecommendedLinksAction.class);
    }

    @Override
    public ExportRecommendedLinksResult executeSecurityAction(ExportRecommendedLinksAction action) throws ActionException {
        try {
            String fileName = searchServiceFacade.exportRecommendedLinks(ServiceContextHolder.getCurrentServiceContext());
            return new ExportRecommendedLinksResult(fileName);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}