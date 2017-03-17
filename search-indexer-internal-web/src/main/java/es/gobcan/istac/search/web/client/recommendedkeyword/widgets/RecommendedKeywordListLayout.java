package es.gobcan.istac.search.web.client.recommendedkeyword.widgets;

import java.util.List;

import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers.RecommendedKeywordListUiHandlers;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.RecommendedKeywordListSearchSectionStack;
import es.gobcan.istac.search.web.client.utils.ClientSecurityUtils;
import es.gobcan.istac.search.web.client.utils.RecordUtils;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

public class RecommendedKeywordListLayout extends VLayout {

    private VLayout                                  panel;

    private RecommendedKeywordPaginatedListGrid      recommendedKeywordPaginatedListGrid;

    private ToolStrip                                recommendedKeywordListToolStrip;
    private ToolStripButton                          newRecommendedKeywordToolStripButton;
    private ToolStripButton                          changeCategoryRecommendedKeywordToolStripButton;
    private ToolStripButton                          deleteRecommendedKeywordToolStripButton;

    private NewRecommendedKeywordWindow              newRecommendedKeywordWindow;
    private ChangeCategoryRecommendedKeywordWindow   changeCategoryRecommendedKeywordWindow;
    private DeleteRecommendedKeywordWindow           deleteRecommendedKeywordWindow;

    private RecommendedKeywordListSearchSectionStack recommendedKeywordListSearchSectionStack;

    private RecommendedKeywordListUiHandlers         uiHandlers;

    public RecommendedKeywordListLayout() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        createRecommendedKeywordListToolStrip();
        createRecommendedKeywordListSearchSectionStack();
        createRecommendedKeywordListPaginatedListGrid();

        panel.addMember(recommendedKeywordListSearchSectionStack);
        panel.addMember(recommendedKeywordListToolStrip);
        panel.addMember(recommendedKeywordPaginatedListGrid);

        addMember(panel);
    }

    private void createRecommendedKeywordListSearchSectionStack() {
        recommendedKeywordListSearchSectionStack = new RecommendedKeywordListSearchSectionStack();
    }

    private void createRecommendedKeywordListPaginatedListGrid() {
        recommendedKeywordPaginatedListGrid = new RecommendedKeywordPaginatedListGrid(new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                RecommendedKeywordWebCriteria criteria = recommendedKeywordListSearchSectionStack.getRecommendedKeywordListWebCriteria();
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);
                getUiHandlers().retrieveRecommendedKeywordList(criteria);
            }
        });
    }

    private void createRecommendedKeywordListToolStrip() {
        recommendedKeywordListToolStrip = new ToolStrip();
        recommendedKeywordListToolStrip.setWidth100();

        newRecommendedKeywordToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionNewKeyword(), GlobalResources.RESOURCE.newListGrid().getURL());
        newRecommendedKeywordToolStripButton.setVisible(ClientSecurityUtils.canCreateRecommendedKeyword());

        changeCategoryRecommendedKeywordToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionChangeCategoryKeyword(), GlobalResources.RESOURCE.editListGrid().getURL());
        changeCategoryRecommendedKeywordToolStripButton.hide();

        deleteRecommendedKeywordToolStripButton = new ToolStripButton(SearchWeb.getConstants().actionDeleteKeyword(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        deleteRecommendedKeywordToolStripButton.hide();

        recommendedKeywordListToolStrip.addButton(newRecommendedKeywordToolStripButton);
        recommendedKeywordListToolStrip.addButton(changeCategoryRecommendedKeywordToolStripButton);
        recommendedKeywordListToolStrip.addButton(deleteRecommendedKeywordToolStripButton);

    }

    public void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults) {
        RecommendedKeywordRecord[] records = RecordUtils.getRecommendedKeywordListRecords(recommendedKeywordList);
        recommendedKeywordPaginatedListGrid.getListGrid().setData(records);
        recommendedKeywordPaginatedListGrid.refreshPaginationInfo(firstResult, records.length, totalResults);
    }

    public ToolStrip getRecommendedKeywordListToolStrip() {
        return recommendedKeywordListToolStrip;
    }

    public ToolStripButton getNewRecommendedKeywordToolStripButton() {
        return newRecommendedKeywordToolStripButton;
    }

    public ToolStripButton getChangeCategoryRecommendedKeywordToolStripButton() {
        return changeCategoryRecommendedKeywordToolStripButton;
    }

    public ToolStripButton getDeleteRecommendedKeywordToolStripButton() {
        return deleteRecommendedKeywordToolStripButton;
    }

    public NewRecommendedKeywordWindow getNewRecommendedKeywordWindow() {
        return newRecommendedKeywordWindow;
    }

    public void setNewRecommendedKeywordWindow(NewRecommendedKeywordWindow newRecommendedKeywordWindow) {
        this.newRecommendedKeywordWindow = newRecommendedKeywordWindow;
    }

    public ChangeCategoryRecommendedKeywordWindow getChangeCategoryRecommendedKeywordWindow() {
        return changeCategoryRecommendedKeywordWindow;
    }

    public void setEditRecommendedKeywordWindow(ChangeCategoryRecommendedKeywordWindow editRecommendedKeywordWindow) {
        changeCategoryRecommendedKeywordWindow = editRecommendedKeywordWindow;
    }

    public DeleteRecommendedKeywordWindow getDeleteRecommendedKeywordWindow() {
        return deleteRecommendedKeywordWindow;
    }

    public void setDeleteRecommendedKeywordWindow(DeleteRecommendedKeywordWindow deleteRecommendedKeywordWindow) {
        this.deleteRecommendedKeywordWindow = deleteRecommendedKeywordWindow;
    }

    public BaseCustomListGrid getRecommendedKeywordListGrid() {
        return recommendedKeywordPaginatedListGrid.getListGrid();
    }

    public void selectRecommendedKeyword(RecommendedKeywordDto RecommendedKeywordDto) {
        showChangeCategoryRecommendedKeywordButton();
        showDeleteRecommendedKeywordButton();
    }

    public void showChangeCategoryRecommendedKeywordButton() {
        if (ClientSecurityUtils.canUpdateRecommendedKeyword()) {
            changeCategoryRecommendedKeywordToolStripButton.show();
        }
    }

    public void showDeleteRecommendedKeywordButton() {
        if (ClientSecurityUtils.canDeleteRecommendedKeyword()) {
            deleteRecommendedKeywordToolStripButton.show();
        }
    }

    public void deselectRecommendedKeyword() {
        changeCategoryRecommendedKeywordToolStripButton.hide();
        deleteRecommendedKeywordToolStripButton.hide();
    }

    public RecommendedKeywordListSearchSectionStack getRecommendedKeywordListSearchSectionStack() {
        return recommendedKeywordListSearchSectionStack;
    }

    private RecommendedKeywordListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(RecommendedKeywordListUiHandlers handlers) {
        newRecommendedKeywordWindow.setUiHandlers(handlers);
        changeCategoryRecommendedKeywordWindow.setUiHandlers(handlers);
        recommendedKeywordListSearchSectionStack.setUiHandlers(handlers);
        uiHandlers = handlers;
    }

}
