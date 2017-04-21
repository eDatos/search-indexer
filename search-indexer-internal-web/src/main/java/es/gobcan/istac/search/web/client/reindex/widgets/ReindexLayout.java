package es.gobcan.istac.search.web.client.reindex.widgets;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.utils.ClientSecurityUtils;

public class ReindexLayout extends VLayout {

    private ReindexFormLayout reindexWeb;
    private ReindexFormLayout reindexGpe;
    private ReindexFormLayout reindexRecommendedLinks;
    private ReindexFormLayout reindexStatisticalResources;

    public ReindexLayout() {
        createReindexWeb();
        createReindexGpe();
        createReindexRecommendedLinks();
        createStatisticalResources();

        addMember(reindexWeb);
        addMember(reindexGpe);
        addMember(reindexRecommendedLinks);
        addMember(reindexStatisticalResources);
    }

    private void createReindexWeb() {
        String title = SearchWeb.getConstants().reindexWeb();
        String description = SearchWeb.getConstants().reindexWebDescription();
        reindexWeb = new ReindexFormLayout(title, description);
        reindexWeb.getReindexStartButton().setVisible(ClientSecurityUtils.canReindexWeb());
    }

    private void createReindexGpe() {
        String title = SearchWeb.getConstants().reindexGpe();
        String description = SearchWeb.getConstants().reindexGpeDescription();
        reindexGpe = new ReindexFormLayout(title, description);
        reindexGpe.getReindexStartButton().setVisible(ClientSecurityUtils.canReindexGpe());
    }

    private void createReindexRecommendedLinks() {
        String title = SearchWeb.getConstants().reindexRecommendedLinks();
        String description = SearchWeb.getConstants().reindexRecommendedLinksDescription();
        reindexRecommendedLinks = new ReindexFormLayout(title, description);
        reindexRecommendedLinks.hideCronExpression();
        reindexRecommendedLinks.getReindexStartButton().setVisible(ClientSecurityUtils.canReindexRecommendedLinks());
    }

    private void createStatisticalResources() {
        String title = SearchWeb.getConstants().reindexStatisticalResources();
        String description = SearchWeb.getConstants().reindexStatisticalResourcesDescription();
        reindexStatisticalResources = new ReindexFormLayout(title, description);
        reindexStatisticalResources.hideCronExpression();
        reindexStatisticalResources.getReindexStartButton().setVisible(ClientSecurityUtils.canReindexStatisticalResources());
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

    public Canvas getReindexStatisticalResourcesStartButton() {
        return reindexStatisticalResources.getReindexStartButton();
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

    public void setIndexationStatisticalResourcesStatus(IndexationStatusDto indexationStatus) {
        reindexStatisticalResources.setIndexationStatus(indexationStatus);
    }

    public String getIndexationWebStatus() {
        return reindexWeb.getStatusKey();
    }

    public String getIndexationGpeStatus() {
        return reindexGpe.getStatusKey();
    }

    public String getIndexationRecommendedLinksStatus() {
        return reindexRecommendedLinks.getStatusKey();
    }

    public String getIndexationStatisticalResourcesStatus() {
        return reindexStatisticalResources.getStatusKey();
    }

}
