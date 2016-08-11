package es.gobcan.istac.search.core.idxmanager.service.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.droids.api.Link;
import org.apache.droids.delay.SimpleDelayTimer;
import org.apache.droids.exception.InvalidTaskException;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.helper.factories.ParserFactory;
import org.apache.droids.helper.factories.ProtocolFactory;
import org.apache.droids.helper.factories.URLFiltersFactory;
import org.apache.droids.impl.DefaultTaskExceptionHandler;
import org.apache.droids.impl.SequentialTaskMaster;
import org.apache.droids.impl.SimpleTaskQueueWithHistory;
import org.apache.droids.protocol.http.DroidsHttpClient;
import org.apache.droids.protocol.http.HttpProtocol;
import org.apache.http.params.BasicHttpParams;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.indexacion.SolrCrawlingDroid;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

@Service
public class WebIndexerServiceImpl implements WebIndexerService {

    public static final String  BEAN_NAME           = "webIndexerServiceImpl";
    protected static Log        log                 = LogFactory.getLog(WebIndexerService.class);

    @Autowired
    private IndexationWebStatus indexationWebStatus = null;

    @Override
    public void reindexWeb(ServiceContext ctx) throws ServiceExcepcion {
        try {
            if (!indexationWebStatus.getIndexationWebStatus().equals(IndexacionStatusDomain.INDEXANDO)) {
                WebIndexerThread webIndexerThread = new WebIndexerThread(ctx);
                new Thread(webIndexerThread).start();
            } else {
                throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_INPROGRESS);
            }
        } catch (Exception e) {
            log.error("Error: WebIndexerService:reindexWeb" + e.getMessage());
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_WEB);
        }

    }

    @Override
    public void indexWeb() throws MetamacException, ServiceExcepcion, InvalidTaskException, InterruptedException {
        // BEANS SPRING
        URLFiltersFactory filtersFactory = (URLFiltersFactory) ApplicationContextProvider.getApplicationContext().getBean("uRLFiltersFactory");
        ParserFactory parserFactory = (ParserFactory) ApplicationContextProvider.getApplicationContext().getBean("parserFactory");
        HandlerFactory handlerFactory = (HandlerFactory) ApplicationContextProvider.getApplicationContext().getBean("handlerFactory");
        BasicHttpParams basicHttpParams = (BasicHttpParams) ApplicationContextProvider.getApplicationContext().getBean("httpParams");
        SolrService solr = (SolrService) ApplicationContextProvider.getApplicationContext().getBean("solr");

        // CREACION DEL DROID

        // Protocol factory. Aceptaremos HTTP y HTTPS
        ProtocolFactory protocolFactory = new ProtocolFactory();
        DroidsHttpClient httpclient = new DroidsHttpClient(basicHttpParams);
        HttpProtocol httpProtocol = new HttpProtocol(httpclient);
        protocolFactory.setMap(new HashMap<String, Object>());
        protocolFactory.getMap().put("http", httpProtocol);
        protocolFactory.getMap().put("https", httpProtocol);

        SimpleDelayTimer simpleDelayTimer = new SimpleDelayTimer();
        simpleDelayTimer.setDelayMillis(100);

        SimpleTaskQueueWithHistory<Link> simpleQueueWhitHistory = new SimpleTaskQueueWithHistory<Link>();

        SequentialTaskMaster<Link> taskMaster = new SequentialTaskMaster<Link>();
        taskMaster.setDelayTimer(simpleDelayTimer);
        taskMaster.setExceptionHandler(new DefaultTaskExceptionHandler());

        SolrCrawlingDroid solrCrawler = new SolrCrawlingDroid(simpleQueueWhitHistory, taskMaster);
        solrCrawler.setFiltersFactory(filtersFactory);
        solrCrawler.setParserFactory(parserFactory);
        solrCrawler.setProtocolFactory(protocolFactory);
        solrCrawler.setHandlerFactory(handlerFactory);

        Collection<String> initialLocations = new ArrayList<String>();
        initialLocations.add(getConfigurationService().retrieveIndexationWebUrl());
        solrCrawler.setInitialLocations(initialLocations);

        // Borramos del indice, lo elementos WEB
        solr.borrarWithoutCommit(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_WEB.getSiglas());

        // Inicializamos y arrancamos el crawler
        solrCrawler.init();
        solrCrawler.start();

        // Esperamos a que termine
        solrCrawler.getTaskMaster().awaitTermination(0, TimeUnit.MILLISECONDS);

        // Shut down the HTTP connection manager
        httpclient.getConnectionManager().shutdown();

        // Commit pero no optimize
        solr.commit();
    }

    private SearchConfigurationService getConfigurationService() {
        return (SearchConfigurationService) ApplicationContextProvider.getApplicationContext().getBean("configurationService");
    }
}
