package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.RecommendedKeywordsServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.UpdateRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.UpdateRecommendedKeywordListResult;

@Component
public class UpdateRecommendedKeywordListActionHandler extends SecurityActionHandler<UpdateRecommendedKeywordListAction, UpdateRecommendedKeywordListResult> {

    @Autowired
    private RecommendedKeywordsServiceFacade recommendedKeywordsServiceFacade;

    public UpdateRecommendedKeywordListActionHandler() {
        super(UpdateRecommendedKeywordListAction.class);
    }

    @Override
    public UpdateRecommendedKeywordListResult executeSecurityAction(UpdateRecommendedKeywordListAction action) throws ActionException {
        try {

            recommendedKeywordsServiceFacade.updateRecommendedKeyword(ServiceContextHolder.getCurrentServiceContext(), action.getRecommendedKeywordList());

            return new UpdateRecommendedKeywordListResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
