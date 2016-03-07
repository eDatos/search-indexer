package es.gobcan.istac.idxmanager.service.test.droid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.droids.api.Link;
import org.apache.droids.delay.SimpleDelayTimer;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.helper.factories.ParserFactory;
import org.apache.droids.helper.factories.ProtocolFactory;
import org.apache.droids.helper.factories.URLFiltersFactory;
import org.apache.droids.impl.DefaultTaskExceptionHandler;
import org.apache.droids.impl.SequentialTaskMaster;
import org.apache.droids.impl.SimpleTaskQueue;
import org.apache.droids.protocol.http.DroidsHttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.idxmanager.service.indexacion.SolrCrawlingDroid;
import es.gobcan.istac.idxmanager.service.test.util.WithoutSolrTestBase;

public class DroidTest extends WithoutSolrTestBase {

    @Autowired
    private URLFiltersFactory filtersFactory = null;

    @Autowired
    private ParserFactory parserFactory = null;

    @Autowired
    private ProtocolFactory protocolFactory = null;

    @Autowired
    private DroidsHttpClient httpclient = null;

    @Autowired
    private HandlerFactory handlerFactory = null;

    @Test
    @Ignore
    public void testCrawlerHello() throws Exception {

        try {
            // Create default droid
            SimpleDelayTimer simpleDelayTimer = new SimpleDelayTimer();
            simpleDelayTimer.setDelayMillis(100);

            SimpleTaskQueue<Link> simpleQueue = new SimpleTaskQueue<Link>();
            // simpleQueue.setMaxDepth(3);
            // simpleQueue.setMaxSize(-1);

            SequentialTaskMaster<Link> taskMaster = new SequentialTaskMaster<Link>();
            taskMaster.setDelayTimer(simpleDelayTimer);
            taskMaster.setExceptionHandler(new DefaultTaskExceptionHandler());

            // CrawlingDroid helloCrawler = new SysoutCrawlingDroid(simpleQueue, taskMaster);
            SolrCrawlingDroid solrCrawler = new SolrCrawlingDroid(simpleQueue, taskMaster);
            // new SysoutCrawlingDroid(simpleQueue, taskMaster);
            solrCrawler.setFiltersFactory(filtersFactory);
            solrCrawler.setParserFactory(parserFactory);
            solrCrawler.setProtocolFactory(protocolFactory);
            solrCrawler.setHandlerFactory(handlerFactory);

            Collection<String> initialLocations = new ArrayList<String>();
            initialLocations.add("http://localhost/crawler/");
            solrCrawler.setInitialLocations(initialLocations);

            // Initialize and start the crawler
            solrCrawler.init();
            solrCrawler.start();

            // Await termination
            solrCrawler.getTaskMaster().awaitTermination(0, TimeUnit.MILLISECONDS);
            // Shut down the HTTP connection manager
            httpclient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println("EXCP: " + e);
        }
    }

}
