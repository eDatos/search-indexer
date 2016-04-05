package es.gobcan.istac.search.core.idxmanager.service.web;

import org.apache.droids.exception.InvalidTaskException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface WebIndexerService {

    void indexWeb() throws MetamacException, ServiceExcepcion, InvalidTaskException, InterruptedException;

    void reindexWeb(ServiceContext ctx) throws ServiceExcepcion;

}
