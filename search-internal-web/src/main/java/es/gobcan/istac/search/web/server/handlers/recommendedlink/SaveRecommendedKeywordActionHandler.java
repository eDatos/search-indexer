package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.RecommendedKeywordsServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordResult;

@Component
public class SaveRecommendedKeywordActionHandler extends SecurityActionHandler<SaveRecommendedKeywordAction, SaveRecommendedKeywordResult> {

    @Autowired
    private RecommendedKeywordsServiceFacade recommendedKeywordsServiceFacade;

    public SaveRecommendedKeywordActionHandler() {
        super(SaveRecommendedKeywordAction.class);
    }

    @Override
    public SaveRecommendedKeywordResult executeSecurityAction(SaveRecommendedKeywordAction action) throws ActionException {
        try {
            // There is no update recommendedKeyword for now
            recommendedKeywordsServiceFacade.createRecommendedKeyword(ServiceContextHolder.getCurrentServiceContext(), action.getRecommendedKeyword());
            return new SaveRecommendedKeywordResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
