package es.gobcan.istac.search.core.idxmanager.service.busqueda;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface BusquedaService {

    QueryResponse ejecutarQuery(Busqueda busqueda, int resultByPage) throws ServiceExcepcion;

    SolrDocumentList ejecutarQueryForSuggested(Busqueda busqueda) throws ServiceExcepcion;

    QueryResponse ejecutarInitialFacetQuery() throws ServiceExcepcion;
}
