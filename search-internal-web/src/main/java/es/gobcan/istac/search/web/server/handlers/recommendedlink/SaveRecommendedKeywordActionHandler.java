package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordResult;

@Component
public class SaveRecommendedKeywordActionHandler extends SecurityActionHandler<SaveRecommendedKeywordAction, SaveRecommendedKeywordResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public SaveRecommendedKeywordActionHandler() {
        super(SaveRecommendedKeywordAction.class);
    }

    @Override
    public SaveRecommendedKeywordResult executeSecurityAction(SaveRecommendedKeywordAction action) throws ActionException {
        try {
            RecommendedKeywordDto recommendedKeywordDto = action.getRecommendedKeyword();
            if (recommendedKeywordDto.getId() == null) {
                searchServiceFacade.createRecommendedKeyword(ServiceContextHolder.getCurrentServiceContext(), recommendedKeywordDto);
            } else {
                searchServiceFacade.updateRecommendedKeyword(ServiceContextHolder.getCurrentServiceContext(), recommendedKeywordDto);
            }
            return new SaveRecommendedKeywordResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
