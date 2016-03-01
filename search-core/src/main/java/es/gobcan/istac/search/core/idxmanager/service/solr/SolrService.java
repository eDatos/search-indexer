package es.gobcan.istac.idxmanager.service.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import es.gobcan.istac.idxmanager.service.excepcion.ServiceExcepcion;

public interface SolrService {

    void borrarTodoAndCommit() throws ServiceExcepcion;

    void borrarWithoutCommit(String query) throws ServiceExcepcion;

    void insertarDoc(SolrInputDocument solrInputDocument) throws ServiceExcepcion;

    void commitANDoptimize() throws ServiceExcepcion;

    void commit() throws ServiceExcepcion;

    void eliminarDoc(String id) throws ServiceExcepcion;

    QueryResponse ejecutarQuery(SolrQuery solrQuery) throws ServiceExcepcion;

    void setSolrClient(SolrClient solrClient);

}
