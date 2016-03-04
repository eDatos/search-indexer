package es.gobcan.istac.search.web.client.reindex.widgets;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.web.client.SearchWeb;

public class ReindexLayout extends VLayout {

    private ReindexFormLayout reindexWeb;
    private ReindexFormLayout reindexGpe;
    private ReindexFormLayout reindexRecommendedLinks;

    public ReindexLayout() {
        createReindexWeb();
        createReindexGpe();
        createReindexRecommendedLinks();

        addMember(reindexWeb);
        addMember(reindexGpe);
        addMember(reindexRecommendedLinks);
    }

    private void createReindexWeb() {
        String title = SearchWeb.getConstants().reindexWeb();
        String description = SearchWeb.getConstants().reindexWebDescription();
        reindexWeb = new ReindexFormLayout(title, description);
    }

    private void createReindexGpe() {
        String title = SearchWeb.getConstants().reindexGpe();
        String description = SearchWeb.getConstants().reindexGpeDescription();
        reindexGpe = new ReindexFormLayout(title, description);
    }

    private void createReindexRecommendedLinks() {
        String title = SearchWeb.getConstants().reindexRecommendedLinks();
        String description = SearchWeb.getConstants().reindexRecommendedLinksDescription();
        reindexRecommendedLinks = new ReindexFormLayout(title, description);
    }

    public void setCronWebExpression(String cronWebExpression) {
        reindexWeb.setCronExpression(cronWebExpression);
    }

    public void setCronGpeExpression(String cronGpeExpression) {
        reindexGpe.setCronExpression(cronGpeExpression);
    }

    public void setCronRecommendedLinksExpression(String cronWebExpression) {
        reindexRecommendedLinks.setCronExpression(cronWebExpression);
    }

    public ToolStripButton getReindexWebStartButton() {
        return reindexWeb.getReindexStartButton();
    }

    public ToolStripButton getReindexGpeStartButton() {
        return reindexGpe.getReindexStartButton();
    }

    public Canvas getReindexRecommendedLinksStartButton() {
        return reindexRecommendedLinks.getReindexStartButton();
    }

    public void setIndexationWebStatus(IndexationStatusDto indexationStatus) {
        reindexWeb.setIndexationStatus(indexationStatus);
    }

    public void setIndexationGpeStatus(IndexationStatusDto indexationStatus) {
        reindexGpe.setIndexationStatus(indexationStatus);
    }

    public void setIndexationRecommendedLinksStatus(IndexationStatusDto indexationStatus) {
        reindexRecommendedLinks.setIndexationStatus(indexationStatus);
    }

    public String getIndexationWebStatus() {
        return reindexWeb.getStatus();
    }

    public String getIndexationGpeStatus() {
        return reindexGpe.getStatus();
    }

    public String getIndexationRecommendedLinksStatus() {
        return reindexRecommendedLinks.getStatus();
    }

}
