package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.facade.serviceapi.RecommendedKeywordsServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListResult;

@Component
public class GetRecommendedKeywordListActionHandler extends SecurityActionHandler<GetRecommendedKeywordListAction, GetRecommendedKeywordListResult> {

    @Autowired
    private RecommendedKeywordsServiceFacade recommendedKeywordsServiceFacade;

    public GetRecommendedKeywordListActionHandler() {
        super(GetRecommendedKeywordListAction.class);
    }

    @Override
    public GetRecommendedKeywordListResult executeSecurityAction(GetRecommendedKeywordListAction action) throws ActionException {
        try {
            List<RecommendedKeywordDto> recommendedKeywordList = recommendedKeywordsServiceFacade.findAllRecommendedKeywords(ServiceContextHolder.getCurrentServiceContext());
            return new GetRecommendedKeywordListResult(recommendedKeywordList);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
