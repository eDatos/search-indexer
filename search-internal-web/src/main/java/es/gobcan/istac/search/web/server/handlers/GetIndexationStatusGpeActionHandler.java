package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.IndexationStatus;
import es.gobcan.istac.search.web.shared.GetIndexationStatusGpeAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusGpeResult;

@Component
public class GetIndexationStatusGpeActionHandler extends SecurityActionHandler<GetIndexationStatusGpeAction, GetIndexationStatusGpeResult> {

    @Autowired
    private IndexationStatus indexationStatusGpe;

    public GetIndexationStatusGpeActionHandler() {
        super(GetIndexationStatusGpeAction.class);
    }

    @Override
    public GetIndexationStatusGpeResult executeSecurityAction(GetIndexationStatusGpeAction action) throws ActionException {
        IndexationStatusDto indexationStatus = new IndexationStatusDto(indexationStatusGpe.getIdxGPEStatusSiglas(), indexationStatusGpe.getIdxGPELastDate());
        return new GetIndexationStatusGpeResult(indexationStatus);
    }
}
