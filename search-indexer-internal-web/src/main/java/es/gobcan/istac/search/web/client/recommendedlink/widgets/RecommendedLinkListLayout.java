package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import java.util.List;

import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.ClientSecurityUtils;
import es.gobcan.istac.search.web.client.utils.RecordUtils;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public class RecommendedLinkListLayout extends VLayout {

    private VLayout                               panel;

    private RecommendedLinkPaginatedListGrid      recommendedLinkPaginatedListGrid;

    private ToolStrip                             recommendedLinkListToolStrip;
    private ToolStripButton                       newRecommendedLinkToolStripButton;
    private ToolStripButton                       deleteRecommendedLinkToolStripButton;
    private ToolStripButton                       exportToolStripButton;
    private ToolStripButton                       importToolStripButton;

    private RecommendedLinkListSearchSectionStack recommendedLinkListSearchSectionStack;

    private RecommendedLinkLayout                 recommendedLinkLayout;

    private RecommendedLinkListUiHandlers         uiHandlers;

    public RecommendedLinkLayout getRecommendedLinkLayout() {
        return recommendedLinkLayout;
    }

    public RecommendedLinkListLayout() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        createRecommendedLinkListToolStrip();
        createRecommendedLinkListSearchSectionStack();
        createRecommendedLinkListPaginatedListGrid();
        createRecommendedLinkLayout();

        panel.addMember(recommendedLinkListSearchSectionStack);
        panel.addMember(recommendedLinkListToolStrip);
        panel.addMember(recommendedLinkPaginatedListGrid);
        panel.addMember(recommendedLinkLayout);

        addMember(panel);
    }

    private void createRecommendedLinkLayout() {
        recommendedLinkLayout = new RecommendedLinkLayout();
        recommendedLinkLayout.hide();
    }

    private void createRecommendedLinkListSearchSectionStack() {
        recommendedLinkListSearchSectionStack = new RecommendedLinkListSearchSectionStack();
    }

    private void createRecommendedLinkListPaginatedListGrid() {
        recommendedLinkPaginatedListGrid = new RecommendedLinkPaginatedListGrid(new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                RecommendedLinkWebCriteria criteria = recommendedLinkListSearchSectionStack.getRecommendedLinkListWebCriteria();
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);
                getUiHandlers().retrieveRecommendedLinkList(criteria);
            }
        });
    }

    private void createRecommendedLinkListToolStrip() {
        recommendedLinkListToolStrip = new ToolStrip();
        recommendedLinkListToolStrip.setWidth100();

        newRecommendedLinkToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionNewRecommendedLink(), GlobalResources.RESOURCE.newListGrid().getURL());
        newRecommendedLinkToolStripButton.setVisible(ClientSecurityUtils.canCreateRecommendedLink());

        deleteRecommendedLinkToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionDeleteRecommendedLink(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        deleteRecommendedLinkToolStripButton.hide();

        exportToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionExport(), GlobalResources.RESOURCE.exportResource().getURL());
        exportToolStripButton.setVisible(ClientSecurityUtils.canExportRecommendedLinks());

        importToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionImport(), GlobalResources.RESOURCE.importResource().getURL());
        importToolStripButton.setVisible(ClientSecurityUtils.canImportRecommendedLinks());

        recommendedLinkListToolStrip.addButton(newRecommendedLinkToolStripButton);
        recommendedLinkListToolStrip.addButton(deleteRecommendedLinkToolStripButton);
        recommendedLinkListToolStrip.addSeparator();
        recommendedLinkListToolStrip.addButton(exportToolStripButton);
        recommendedLinkListToolStrip.addButton(importToolStripButton);
        recommendedLinkListToolStrip.addSeparator();

    }

    public void setRecommendedLinkList(List<RecommendedLinkDto> recommendedLinkList, int firstResult, int totalResults) {
        RecommendedLinkRecord[] records = RecordUtils.getRecommendedLinkListRecords(recommendedLinkList);
        recommendedLinkPaginatedListGrid.getListGrid().setData(records);
        recommendedLinkPaginatedListGrid.refreshPaginationInfo(firstResult, records.length, totalResults);
    }

    public ToolStrip getRecommendedLinkListToolStrip() {
        return recommendedLinkListToolStrip;
    }

    public ToolStripButton getNewRecommendedLinkToolStripButton() {
        return newRecommendedLinkToolStripButton;
    }

    public ToolStripButton getDeleteRecommendedLinkToolStripButton() {
        return deleteRecommendedLinkToolStripButton;
    }

    public ToolStripButton getExportToolStripButton() {
        return exportToolStripButton;
    }

    public ToolStripButton getImportToolStripButton() {
        return importToolStripButton;
    }

    public BaseCustomListGrid getRecommendedLinkListGrid() {
        return recommendedLinkPaginatedListGrid.getListGrid();
    }

    public void selectRecommendedLink(RecommendedLinkDto recommendedLinkDto) {
        showDeleteRecommendedLinkButton();
        recommendedLinkLayout.editRecommendedLink(recommendedLinkDto);
    }

    public void showDeleteRecommendedLinkButton() {
        if (ClientSecurityUtils.canDeleteRecommendedLink()) {
            deleteRecommendedLinkToolStripButton.show();
        }
    }

    public void deselectRecommendedLink() {
        recommendedLinkLayout.hide();
        deleteRecommendedLinkToolStripButton.hide();
    }

    public void newRecommendedLink() {
        recommendedLinkPaginatedListGrid.getListGrid().deselectAllRecords();
        deleteRecommendedLinkToolStripButton.hide();
        recommendedLinkLayout.newRecommendedLink();
    }

    public RecommendedLinkListSearchSectionStack getRecommendedLinkListSearchSectionStack() {
        return recommendedLinkListSearchSectionStack;
    }

    private RecommendedLinkListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(RecommendedLinkListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
        recommendedLinkListSearchSectionStack.setUiHandlers(uiHandlers);
        recommendedLinkLayout.setUiHandlers(uiHandlers);
    }
}
