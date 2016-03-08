package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerServiceImpl;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerServiceImpl;

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
            LOG.info("Comenzando reindexación del GPE...");
            NucleoIstacIndexerService nucleoIstacIndexerService = (NucleoIstacIndexerServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("nucleoIstacIndexerServiceImpl");

            // Al finalizar realiza un COMMIT y OPTIMIZE
            nucleoIstacIndexerService.reindexarGPEelementos();

            // Reindexación Recomendados
            LOG.info("Reindexación GPE finalizada. Comenzando reindexación de los enlaces recomendados ...");
            RecomendadosIndexerService recomendadosIndexerService = (RecomendadosIndexerServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("recomendadosIndexerServiceImpl");

            // Al finalizar realiza un COMMIT y OPTIMIZE
            recomendadosIndexerService.reindexarElementosRecomendados();

        } catch (Exception e) {
            LOG.error("GPEIndexerJob::execute: ", e);
            throw new JobExecutionException(e);
        }

    }
}
