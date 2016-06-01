package es.gobcan.istac.search.web.server.handlers.recommendedlink;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedKeywordListResult;

@Component
public class DeleteRecommendedKeywordListActionHandler extends SecurityActionHandler<DeleteRecommendedKeywordListAction, DeleteRecommendedKeywordListResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public DeleteRecommendedKeywordListActionHandler() {
        super(DeleteRecommendedKeywordListAction.class);
    }

    @Override
    public DeleteRecommendedKeywordListResult executeSecurityAction(DeleteRecommendedKeywordListAction action) throws ActionException {

        try {
            searchServiceFacade.deleteRecommendedKeyword(ServiceContextHolder.getCurrentServiceContext(), action.getRecommendedKeywordIds());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        return new DeleteRecommendedKeywordListResult();
    }
}