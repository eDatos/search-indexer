package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.droids.LinkTask;
import org.apache.droids.api.Link;
import org.apache.droids.api.Parse;
import org.apache.droids.robot.crawler.CrawlingDroid;
import org.apache.droids.robot.crawler.CrawlingWorker;
import org.apache.http.client.utils.URIBuilder;

public class IstacCrawlingWorker extends CrawlingWorker {

    private static final Pattern URL_PATTERN     = Pattern.compile("^(?:https?:\\/\\/)?((?:[\\da-z\\.-]+)\\.(?:[a-z\\.]{2,6}))/istac/.*$");
    private static final String  GOBCAN_URL_HOST = "www.gobiernodecanarias.org";

    public IstacCrawlingWorker(CrawlingDroid droid) {
        super(droid);
    }

    @Override
    protected Collection<Link> getFilteredOutlinks(Parse parse) {
        Collection<Link> filteredOutlinksList = super.getFilteredOutlinks(parse);

        // Se filtran los host del ISTAC para evitar que www.gobiernodecanarias.org, www2.gobiernodecanarias.org o www.gobcan.es sean tratados como host distintos.
        List<Link> resultsList = new LinkedList<Link>();
        for (Link link : filteredOutlinksList) {
            String beforeUri = link.getURI().toString();
            String afterUri = beforeUri;
            afterUri = normalizeIstacUrl(afterUri);
            if (beforeUri != afterUri) {
                try {
                    LinkTask linkTask = new LinkTask(link.getFrom(), new URI(afterUri), link.getDepth());
                    resultsList.add(linkTask);
                } catch (URISyntaxException e) {
                    resultsList.add(link);
                }
            } else {
                resultsList.add(link);
            }
        }

        return resultsList;
    }

    private String normalizeIstacUrl(String urlString) {
        String prev = urlString;
        Matcher matcher = URL_PATTERN.matcher(urlString);
        if (matcher.find() && matcher.groupCount() >= 1) {
            try {
                URIBuilder uriBuilder = new URIBuilder(urlString);
                uriBuilder.setHost(GOBCAN_URL_HOST);
                uriBuilder.getHost();
                urlString = uriBuilder.toString();
            } catch (URISyntaxException e1) {
                return urlString;
            }
        }

        return urlString;
    }
}
