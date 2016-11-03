package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

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
            ServiceContext ctx = new ServiceContext("crawlerJob", context.getFireInstanceId(), "search-core");
            getWebIndexerService().reindexWeb(ctx);

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
