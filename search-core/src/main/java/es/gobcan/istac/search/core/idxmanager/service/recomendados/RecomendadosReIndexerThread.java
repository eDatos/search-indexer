package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;

public class RecomendadosReIndexerThread implements Runnable {

    protected static Log   log = LogFactory.getLog(RecomendadosReIndexerThread.class);
    private ServiceContext ctx;

    public RecomendadosReIndexerThread(ServiceContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {

        try {
            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.INDEXANDO);
            log.info("Reindexación Recomendados iniciada...");

            // 0. Se eliminan todos los recursos GPE publicados
            _getRecomendadosIndexerService().deleteByQuery(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_PATROCINADO.getSiglas());

            indexRecommendedKeywords();

            _getSolrService().commitANDoptimize();

            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.PARADO);
            log.info("Reindexación Recomendados finalizada.");
        } catch (Exception e) {
            _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.FALLO);

            log.error("Error: PatrocinadosIndexerServiceImpl:reindexarElementosPatrocinados " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_RECOMENDADOS);
        }
    }

    private void indexRecommendedKeywords() throws MetamacException {
        TransactionTemplate template = new TransactionTemplate(getPlatformTransactionManager());
        template.execute(new TransactionCallback<Void>() {

            @Override
            public Void doInTransaction(TransactionStatus status) {
                List<RecommendedKeyword> recommendedKeywords;
                try {
                    recommendedKeywords = getRecommendedKeywordsService().findAllRecommendedKeywords(ctx);
                    for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
                        _getRecomendadosIndexerService().indexRecommendedKeyword(recommendedKeyword);
                    }
                } catch (MetamacException e) {
                    _getIndexacionStatus().setIdxSuggestedStatus(IndexacionStatusDomain.FALLO);
                    log.error("Error: PatrocinadosIndexerServiceImpl:reindexarElementosPatrocinados " + e);
                    new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_RECOMENDADOS);
                }
                return null;
            }
        });

    }

    private RecommendedKeywordsService getRecommendedKeywordsService() {
        return (RecommendedKeywordsService) ApplicationContextProvider.getApplicationContext().getBean(RecommendedKeywordsService.BEAN_ID);
    }

    private SolrService _getSolrService() {
        return (SolrService) ApplicationContextProvider.getApplicationContext().getBean("solr");
    }

    private RecomendadosIndexerService _getRecomendadosIndexerService() {
        return (RecomendadosIndexerService) ApplicationContextProvider.getApplicationContext().getBean("recomendadosIndexerServiceImpl");
    }

    private IndexationSuggestedStatus _getIndexacionStatus() {
        return (IndexationSuggestedStatus) ApplicationContextProvider.getApplicationContext().getBean("indexationSuggestedStatus");
    }

    protected PlatformTransactionManager getPlatformTransactionManager() {
        return ApplicationContextProvider.getApplicationContext().getBean(PlatformTransactionManager.class);
    }

}
