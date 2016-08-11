package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedLinkAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedLinkResult;

@Component
public class SaveRecommendedLinkActionHandler extends SecurityActionHandler<SaveRecommendedLinkAction, SaveRecommendedLinkResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public SaveRecommendedLinkActionHandler() {
        super(SaveRecommendedLinkAction.class);
    }

    @Override
    public SaveRecommendedLinkResult executeSecurityAction(SaveRecommendedLinkAction action) throws ActionException {
        try {
            RecommendedLinkDto recommendedLink = action.getRecommendedLink();
            if (recommendedLink.getId() == null) {
                searchServiceFacade.createRecommendedLink(ServiceContextHolder.getCurrentServiceContext(), recommendedLink);
            } else {
                searchServiceFacade.updateRecommendedLink(ServiceContextHolder.getCurrentServiceContext(), recommendedLink);
            }
            return new SaveRecommendedLinkResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
