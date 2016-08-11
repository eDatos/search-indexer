package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.core.idxmanager.service.web.IndexationWebStatus;
import es.gobcan.istac.search.web.shared.GetIndexationStatusWebAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusWebResult;

@Component
public class GetIndexationStatusWebActionHandler extends SecurityActionHandler<GetIndexationStatusWebAction, GetIndexationStatusWebResult> {

    @Autowired
    private IndexationWebStatus indexationStatusWeb;

    public GetIndexationStatusWebActionHandler() {
        super(GetIndexationStatusWebAction.class);
    }

    @Override
    public GetIndexationStatusWebResult executeSecurityAction(GetIndexationStatusWebAction action) throws ActionException {
        IndexationStatusDto indexationStatus = new IndexationStatusDto(indexationStatusWeb.getIndexationWebStatusKey(), indexationStatusWeb.getIndexationWebLastDate());
        return new GetIndexationStatusWebResult(indexationStatus);
    }
}
