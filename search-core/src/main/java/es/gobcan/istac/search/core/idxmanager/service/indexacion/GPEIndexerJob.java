package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerServiceImpl;

public class GPEIndexerJob implements Job {

    private static Log LOG = LogFactory.getLog(GPEIndexerJob.class);

    /**
     * Quartz requiere un constructor publico vacio para que el scheduler pueda
     * instanciar la clase cuando la necesite.
     */
    public GPEIndexerJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // Reindexación GPE
            LOG.info("Comenzando JOB reindexación del GPE...");
            NucleoIstacIndexerService nucleoIstacIndexerService = (NucleoIstacIndexerServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("nucleoIstacIndexerServiceImpl");

            // Al finalizar realiza un COMMIT y OPTIMIZE
            ServiceContext ctx = new ServiceContext("GPEIndexerJob", context.getFireInstanceId(), "search-core");
            nucleoIstacIndexerService.reindexarGPEelementos(ctx);

            // Reindexación Recomendados
            LOG.info("JOB reindexación GPE finalizada.");
        } catch (Exception e) {
            LOG.error("GPEIndexerJob::execute: ", e);
            throw new JobExecutionException(e);
        }

    }
}
