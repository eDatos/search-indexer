package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.web.shared.ReindexWebAction;
import es.gobcan.istac.search.web.shared.ReindexWebResult;

@Component
public class ReindexWebActionHandler extends SecurityActionHandler<ReindexWebAction, ReindexWebResult> {

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public ReindexWebActionHandler() {
        super(ReindexWebAction.class);
    }

    @Override
    public ReindexWebResult executeSecurityAction(ReindexWebAction action) throws ActionException {
        try {
            searchServiceFacade.reindexWeb(ServiceContextHolder.getCurrentServiceContext());
        } catch (ServiceExcepcion e) {
            // throw WebExceptionUtils.createMetamacWebException(e.getReasonType());
        }
        return new ReindexWebResult();
    }
}
