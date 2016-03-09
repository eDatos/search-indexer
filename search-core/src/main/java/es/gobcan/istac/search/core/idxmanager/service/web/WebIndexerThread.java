package es.gobcan.istac.search.core.idxmanager.service.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;

public class WebIndexerThread implements Runnable {

    protected static Log log = LogFactory.getLog(WebIndexerThread.class);

    public WebIndexerThread() {

    }

    @Override
    public void run() {

        try {
            getIndexationStatus().setIndexationWebStatus(IndexacionStatusDomain.INDEXANDO);
            log.info("Reindexación Web iniciada...");

            getWebIndexerService().indexWeb();

            getIndexationStatus().setIndexationWebStatus(IndexacionStatusDomain.PARADO);
            log.info("Reindexación Web finalizada.");
        } catch (Exception e) {
            getIndexationStatus().setIndexationWebStatus(IndexacionStatusDomain.FALLO);

            log.error("Error: WebIndexerServiceImpl:reindexWeb " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_WEB);
        }
    }

    private WebIndexerService getWebIndexerService() {
        return (WebIndexerService) ApplicationContextProvider.getApplicationContext().getBean(WebIndexerServiceImpl.BEAN_NAME);
    }

    private IndexationWebStatus getIndexationStatus() {
        return (IndexationWebStatus) ApplicationContextProvider.getApplicationContext().getBean(IndexationWebStatus.BEAN_NAME);
    }

}
