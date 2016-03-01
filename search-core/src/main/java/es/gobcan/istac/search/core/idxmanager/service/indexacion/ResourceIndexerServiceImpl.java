package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

@Service
public class ResourceIndexerServiceImpl implements ResourceIndexerService {

    protected static Log log  = LogFactory.getLog(ResourceIndexerService.class);

    @Autowired
    private SolrService  solr = null;

    /**************************************************************************
     * PUBLICOS
     *************************************************************************/

    @Override
    public void indexaRecurso(SolrInputDocument solrInputDocument) throws ServiceExcepcion {
        solr.insertarDoc(solrInputDocument);
    }

    /**************************************************************************
     * PRIVADOS
     *************************************************************************/

}
