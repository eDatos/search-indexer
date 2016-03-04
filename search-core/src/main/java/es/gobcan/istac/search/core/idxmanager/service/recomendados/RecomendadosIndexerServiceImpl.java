package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

@Service
public class RecomendadosIndexerServiceImpl implements RecomendadosIndexerService {

    protected static Log              log                       = LogFactory.getLog(RecomendadosIndexerService.class);

    @Autowired
    private SolrService               solr                      = null;

    @Autowired
    private IndexationSuggestedStatus indexationSponsoredStatus = null;

    @Override
    public void indexarElementoRecomendado(Recomendacion recomendacion) throws ServiceExcepcion {

        // Campos el indice
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField(IndexacionEnumDomain.ID.getCampo(), recomendacion.getId());
        String[] suggestedWords = new String[recomendacion.getListaPalabras().size()];
        int index = 0;
        for (String entry : recomendacion.getListaPalabras()) {
            suggestedWords[index++] = entry;
        }

        solrInputDocument.addField(IndexacionEnumDomain.SPONSORED.getCampo(), suggestedWords);
        solrInputDocument.addField(IndexacionEnumDomain.ORIGENRECURSO.getCampo(), OrigenRecursoDomain.RECURSO_PATROCINADO.getSiglas());

        // Seteamos los campos dinamicos
        index = 0;
        for (Enlace enlace : recomendacion.getEnlaces()) {
            solrInputDocument.addField(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo() + index + "_s", enlace.getTitulo());
            solrInputDocument.addField(IndexacionEnumDomain.CUS_DESC_PREFIX.getCampo() + index + "_s", enlace.getDescripcion());
            solrInputDocument.addField(IndexacionEnumDomain.CUS_URL_PREFIX.getCampo() + index + "_s", enlace.getUrl());
            index++;
        }

        solr.insertarDoc(solrInputDocument);
    }

    @Override
    public void eliminarElemento(String id) throws ServiceExcepcion {
        solr.eliminarDoc(id);
        solr.commitANDoptimize();
    }

    @Override
    public void borrarTodo() throws ServiceExcepcion {
        solr.borrarTodoAndCommit();
    }

    @Override
    public void borrar(String query) throws ServiceExcepcion {
        solr.borrarWithoutCommit(query);
    }

    @Override
    public void commitANDoptimize() throws ServiceExcepcion {
        solr.commitANDoptimize();
    }

    @Override
    public void reindexarElementosRecomendados() throws ServiceExcepcion {

        try {
            if (!indexationSponsoredStatus.getIdxSuggestedStatus().equals(IndexacionStatusDomain.INDEXANDO)) {
                RecomendadosReIndexerThread recomendadosReIndexerThread = new RecomendadosReIndexerThread();
                new Thread(recomendadosReIndexerThread).start();
            } else {
                new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_INPROGRESS);
            }
        } catch (Exception e) {
            log.error("Error: RecomendadosIndexerService:reindexarElementosRecomendados" + e.getMessage());
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_RECOMENDADOS);
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
