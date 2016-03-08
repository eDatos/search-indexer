package es.gobcan.istac.idxmanager.service.test.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

public class SolrServer extends EmbeddedSolrServerTestCaseBase implements InitializingBean, DisposableBean {

    @Autowired
    SolrService solrService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Inicializando servidor embebido de Solr");
        super.setUp();

        solrService.setSolrClient(getIstacCore());
    }

    @Override
    public void destroy() throws Exception {
        log.info("Destruyendo servidor embebido de Solr");
        super.tearDown();
    }

}