package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.RecommendedLinksServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedLinkListResult;

@Component
public class DeleteRecommendedLinkListActionHandler extends SecurityActionHandler<DeleteRecommendedLinkListAction, DeleteRecommendedLinkListResult> {

    @Autowired
    private RecommendedLinksServiceFacade recommendedLinksServiceFacade;

    public DeleteRecommendedLinkListActionHandler() {
        super(DeleteRecommendedLinkListAction.class);
    }

    @Override
    public DeleteRecommendedLinkListResult executeSecurityAction(DeleteRecommendedLinkListAction action) throws ActionException {

        try {
            recommendedLinksServiceFacade.deleteRecommendedLink(ServiceContextHolder.getCurrentServiceContext(), action.getRecommendedLinkIds());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        return new DeleteRecommendedLinkListResult();
    }
}