package es.gobcan.istac.search.web.server.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.facade.serviceapi.SearchServiceFacade;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.web.shared.ReindexGpeAction;
import es.gobcan.istac.search.web.shared.ReindexGpeResult;

@Component
public class ReindexGpeActionHandler extends SecurityActionHandler<ReindexGpeAction, ReindexGpeResult> {

    private static Logger       logger = Logger.getLogger(ReindexWebActionHandler.class.getName());

    @Autowired
    private SearchServiceFacade searchServiceFacade;

    public ReindexGpeActionHandler() {
        super(ReindexGpeAction.class);
    }

    @Override
    public ReindexGpeResult executeSecurityAction(ReindexGpeAction action) throws ActionException {
        try {
            searchServiceFacade.reindexGpe(ServiceContextHolder.getCurrentServiceContext());
        } catch (ServiceExcepcion e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        return new ReindexGpeResult();
    }
}
