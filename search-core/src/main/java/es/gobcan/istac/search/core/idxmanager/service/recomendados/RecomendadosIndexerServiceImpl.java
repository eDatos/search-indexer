package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

@Service
public class RecomendadosIndexerServiceImpl implements RecomendadosIndexerService {

    protected static Log              log                       = LogFactory.getLog(RecomendadosIndexerService.class);

    @Autowired
    private SolrService               solr                      = null;

    @Autowired
    private IndexationSuggestedStatus indexationSponsoredStatus = null;

    @Override
    public void indexRecommendedKeyword(RecommendedKeyword recommendedKeyword) throws MetamacException {

        // Campos el indice
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField(IndexacionEnumDomain.ID.getCampo(), recommendedKeyword.getId());
        solrInputDocument.addField(IndexacionEnumDomain.SPONSORED.getCampo(), recommendedKeyword.getKeyword());
        solrInputDocument.addField(IndexacionEnumDomain.ORIGENRECURSO.getCampo(), OrigenRecursoDomain.RECURSO_PATROCINADO.getSiglas());

        // Seteamos los campos dinamicos
        int index = 0;
        for (RecommendedLink enlace : recommendedKeyword.getRecommendedLinks()) {
            solrInputDocument.addField(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo() + index + "_s", enlace.getTitle());
            solrInputDocument.addField(IndexacionEnumDomain.CUS_DESC_PREFIX.getCampo() + index + "_s", enlace.getDescription());
            solrInputDocument.addField(IndexacionEnumDomain.CUS_URL_PREFIX.getCampo() + index + "_s", enlace.getUrl());
            index++;
        }

        try {
            solr.insertarDoc(solrInputDocument);
        } catch (ServiceExcepcion e) {
            throw new MetamacException(ServiceExceptionType.INDEXATION_SOLR_ERROR);
        }
    }

    @Override
    public void deleteById(String id) throws ServiceExcepcion {
        solr.eliminarDoc(id);
        solr.commitANDoptimize();
    }

    @Override
    public void deleteAll() throws ServiceExcepcion {
        solr.borrarTodoAndCommit();
    }

    @Override
    public void deleteByQuery(String query) throws ServiceExcepcion {
        solr.borrarWithoutCommit(query);
    }

    @Override
    public void commitANDoptimize() throws ServiceExcepcion {
        solr.commitANDoptimize();
    }

    @Override
    public void reindexRecommendedKeywords(ServiceContext ctx) throws MetamacException {

        try {
            if (!indexationSponsoredStatus.getIdxSuggestedStatus().equals(IndexacionStatusDomain.INDEXANDO)) {
                RecomendadosReIndexerThread recomendadosReIndexerThread = new RecomendadosReIndexerThread(ctx);
                new Thread(recomendadosReIndexerThread).start();
            } else {
                throw new MetamacException(ServiceExceptionType.INDEXATION_INPROGRESS);
            }
        } catch (Exception e) {
            log.error("Error: RecomendadosIndexerService:reindexarElementosRecomendados" + e.getMessage());
            throw new MetamacException(ServiceExceptionType.INDEXATION_RECOMMENDED_KEYWORDS);
        }

    }

    /***************************************************************************
     * GETTERS/SETTERS
     ***************************************************************************/

    public void setSolr(SolrService solr) {
        this.solr = solr;
    }

    public SolrService getSolr() {
        return solr;
    }

}
