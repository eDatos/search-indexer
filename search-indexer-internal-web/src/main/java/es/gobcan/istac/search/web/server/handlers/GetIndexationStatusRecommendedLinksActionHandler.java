package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.IndexationSuggestedStatus;
import es.gobcan.istac.search.web.shared.GetIndexationStatusRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusRecommendedLinksResult;

@Component
public class GetIndexationStatusRecommendedLinksActionHandler extends SecurityActionHandler<GetIndexationStatusRecommendedLinksAction, GetIndexationStatusRecommendedLinksResult> {

    @Autowired
    private IndexationSuggestedStatus indexationStatusRecommendedLinks;

    public GetIndexationStatusRecommendedLinksActionHandler() {
        super(GetIndexationStatusRecommendedLinksAction.class);
    }

    @Override
    public GetIndexationStatusRecommendedLinksResult executeSecurityAction(GetIndexationStatusRecommendedLinksAction action) throws ActionException {
        IndexationStatusDto indexationStatus = new IndexationStatusDto(indexationStatusRecommendedLinks.getIdxSuggestedStatusSiglas(), indexationStatusRecommendedLinks.getIdxSuggestedLastDate());
        return new GetIndexationStatusRecommendedLinksResult(indexationStatus);
    }
}
