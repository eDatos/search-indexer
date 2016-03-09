package es.gobcan.istac.search.core.idxmanager.service.web;

import org.apache.droids.exception.InvalidTaskException;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface WebIndexerService {

    void reindexWeb() throws ServiceExcepcion;

    void indexWeb() throws MetamacException, ServiceExcepcion, InvalidTaskException, InterruptedException;

}
