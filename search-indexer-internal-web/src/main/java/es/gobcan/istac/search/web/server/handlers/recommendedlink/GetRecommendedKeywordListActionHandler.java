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

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.server.utils.MetamacWebCriteriaUtils;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListResult;

@Component
public class GetRecommendedKeywordListActionHandler extends SecurityActionHandler<GetRecommendedKeywordListAction, GetRecommendedKeywordListResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public GetRecommendedKeywordListActionHandler() {
        super(GetRecommendedKeywordListAction.class);
    }

    @Override
    public GetRecommendedKeywordListResult executeSecurityAction(GetRecommendedKeywordListAction action) throws ActionException {
        try {
            MetamacCriteria criteria = MetamacWebCriteriaUtils.build(action.getCriteria());
            MetamacCriteriaResult<RecommendedKeywordDto> result = searchServiceFacade.findRecommendedKeywords(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetRecommendedKeywordListResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
