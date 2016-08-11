package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.CreateRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.CreateRecommendedLinksResult;

@Component
public class CreateRecommendedLinksActionHandler extends SecurityActionHandler<CreateRecommendedLinksAction, CreateRecommendedLinksResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public CreateRecommendedLinksActionHandler() {
        super(CreateRecommendedLinksAction.class);
    }

    @Override
    public CreateRecommendedLinksResult executeSecurityAction(CreateRecommendedLinksAction action) throws ActionException {
        try {
            RecommendedLinkGroupedKeywordsDto recommendedLink = action.getRecommendedLinkGroupedKeywordsDto();
            searchServiceFacade.createRecommendedLinks(ServiceContextHolder.getCurrentServiceContext(), recommendedLink);
            return new CreateRecommendedLinksResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
