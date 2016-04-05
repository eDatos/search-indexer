package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.facade.serviceapi.RecommendedLinksServiceFacade;
import es.gobcan.istac.search.web.server.utils.MetamacWebCriteriaUtils;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedLinkListResult;

@Component
public class GetRecommendedLinkListActionHandler extends SecurityActionHandler<GetRecommendedLinkListAction, GetRecommendedLinkListResult> {

    @Autowired
    private RecommendedLinksServiceFacade recommendededLinksServiceFacade;

    public GetRecommendedLinkListActionHandler() {
        super(GetRecommendedLinkListAction.class);
    }

    @Override
    public GetRecommendedLinkListResult executeSecurityAction(GetRecommendedLinkListAction action) throws ActionException {
        try {
            MetamacCriteria criteria = MetamacWebCriteriaUtils.build(action.getCriteria());
            MetamacCriteriaResult<RecommendedLinkDto> result = recommendededLinksServiceFacade.findRecommendedLinks(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetRecommendedLinkListResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
