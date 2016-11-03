package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.solr.common.SolrInputDocument;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface ResourceIndexerService {

    void indexaRecurso(SolrInputDocument solrInputDocument) throws ServiceExcepcion;

}
