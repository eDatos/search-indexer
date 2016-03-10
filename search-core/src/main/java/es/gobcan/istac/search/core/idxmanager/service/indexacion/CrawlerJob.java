package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerServiceImpl;
import es.gobcan.istac.search.core.idxmanager.service.web.WebIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.web.WebIndexerServiceImpl;

public class CrawlerJob implements Job {

    private static Log LOG = LogFactory.getLog(CrawlerJob.class);

    /**
     * Quartz requiere un constructor publico vacio para que el scheduler pueda
     * instanciar la clase cuando la necesite.
     */
    public CrawlerJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            LOG.info("Comenzando JOB reindexación de la WEB...");
            
            // Al finalizar realiza un COMMIT y OPTIMIZE
            getWebIndexerService().reindexWeb();

            // Reindexación Recomendados
            LOG.info("JOB Reindexación WEB finalizada");
        } catch (Exception e) {
            LOG.error("CrawlerJob::execute: ", e);
            throw new JobExecutionException(e);
        }

    }

    private WebIndexerService getWebIndexerService() {
        return (WebIndexerService) ApplicationContextProvider.getApplicationContext().getBean(WebIndexerServiceImpl.BEAN_NAME);
    }

}
