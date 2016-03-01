/*
 * ROBOT DE INDEXACION EN SOLR
 */

package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import org.apache.droids.api.Link;
import org.apache.droids.api.TaskMaster;
import org.apache.droids.api.TaskQueue;
import org.apache.droids.api.Worker;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.robot.crawler.CrawlingDroid;
import org.apache.droids.robot.crawler.CrawlingWorker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author arte
 */
public class SolrCrawlingDroid extends CrawlingDroid {

    @Autowired
    private HandlerFactory handlerFactory = null;

    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public SolrCrawlingDroid(TaskQueue<Link> queue, TaskMaster<Link> taskMaster) {
        super(queue, taskMaster);
    }

    @Override
    public Worker<Link> getNewWorker() {
        final CrawlingWorker worker = new IstacCrawlingWorker(this);
        worker.setHandlerFactory(handlerFactory);
        return worker;
    }

}
