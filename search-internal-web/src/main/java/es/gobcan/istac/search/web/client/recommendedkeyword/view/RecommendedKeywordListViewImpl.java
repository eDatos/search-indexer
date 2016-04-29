package es.gobcan.istac.search.web.client.recommendedkeyword.view;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.recommendedkeyword.presenter.RecommendedKeywordListPresenter.RecommendedKeywordListView;
import es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers.RecommendedKeywordListUiHandlers;
import es.gobcan.istac.search.web.client.recommendedkeyword.widgets.ChangeCategoryRecommendedKeywordWindow;
import es.gobcan.istac.search.web.client.recommendedkeyword.widgets.NewRecommendedKeywordWindow;
import es.gobcan.istac.search.web.client.recommendedkeyword.widgets.RecommendedKeywordListLayout;
import es.gobcan.istac.search.web.client.utils.RecordUtils;

public class RecommendedKeywordListViewImpl extends ViewWithUiHandlers<RecommendedKeywordListUiHandlers> implements RecommendedKeywordListView {

    private VLayout                      panel;
    private RecommendedKeywordListLayout recommendedKeywordListLayout;

    public RecommendedKeywordListViewImpl() {
        recommendedKeywordListLayout = new RecommendedKeywordListLayout();

        createNewRecommendedKeywordWindow();
        createEditRecommendedKeywordWindow();
        attachHandlers();

        panel = new VLayout();
        panel.addMember(recommendedKeywordListLayout);
    }

    private void createNewRecommendedKeywordWindow() {

        NewRecommendedKeywordWindow newRecommendedKeywordWindow = new NewRecommendedKeywordWindow();
        recommendedKeywordListLayout.setNewRecommendedKeywordWindow(newRecommendedKeywordWindow);
        recommendedKeywordListLayout.getNewRecommendedKeywordWindow().hide();
        recommendedKeywordListLayout.getNewRecommendedKeywordWindow().getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                if (recommendedKeywordListLayout.getNewRecommendedKeywordWindow().validateForm()) {
                    getUiHandlers().saveRecommendedKeyword(recommendedKeywordListLayout.getNewRecommendedKeywordWindow().getRecommendedKeyword());
                    recommendedKeywordListLayout.getNewRecommendedKeywordWindow().hide();
                }
            }
        });
    }

    private void createEditRecommendedKeywordWindow() {

        ChangeCategoryRecommendedKeywordWindow editRecommendedKeywordWindow = new ChangeCategoryRecommendedKeywordWindow();
        recommendedKeywordListLayout.setEditRecommendedKeywordWindow(editRecommendedKeywordWindow);
        recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().hide();
        recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                if (recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().validateForm()) {

                    List<RecommendedKeywordDto> recommendedKeywordListDto = RecordUtils.getRecommendedKeywordListDto(recommendedKeywordListLayout.getRecommendedKeywordListGrid().getSelectedRecords());
                    ExternalItemDto category = recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().getCategory();
                    if (category != null) {
                        for (RecommendedKeywordDto recommendedKeywordDto : recommendedKeywordListDto) {
                            recommendedKeywordDto.setCategory(category);
                        }
                        getUiHandlers().updateRecommendedKeywordList(recommendedKeywordListDto);
                    }
                    recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().hide();
                }
            }

        });
    }

    private void attachHandlers() {

        final BaseCustomListGrid recommendedKeywordListGrid = recommendedKeywordListLayout.getRecommendedKeywordListGrid();

        recommendedKeywordListLayout.getNewRecommendedKeywordToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                recommendedKeywordListLayout.getNewRecommendedKeywordWindow().show();
            }
        });

        recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().clearForm();
                recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().show();
            }
        });

        recommendedKeywordListLayout.getDeleteRecommendedKeywordToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (recommendedKeywordListGrid.getSelectedRecords() != null) {
                    getUiHandlers().deleteRecommendedKeywords(RecordUtils.getRecommendedKeywordListIds(recommendedKeywordListGrid.getSelectedRecords()));
                }
            }
        });

        recommendedKeywordListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {

                if (recommendedKeywordListGrid.getSelectedRecords() == null) {
                    recommendedKeywordListLayout.deselectRecommendedKeyword();
                } else {
                    if (recommendedKeywordListGrid.getSelectedRecords().length != 1) {
                        recommendedKeywordListLayout.deselectRecommendedKeyword();
                    } else {
                        RecommendedKeywordRecord record = (RecommendedKeywordRecord) recommendedKeywordListGrid.getSelectedRecord();
                        recommendedKeywordListLayout.selectRecommendedKeyword(record.getRecommendedKeywordDto());
                    }

                    if (recommendedKeywordListGrid.getSelectedRecords().length > 0) {
                        recommendedKeywordListLayout.getDeleteRecommendedKeywordToolStripButton().show();
                        recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordToolStripButton().show();
                    }
                }
            }

        });

    }

    @Override
    public void setUiHandlers(RecommendedKeywordListUiHandlers handlers) {
        super.setUiHandlers(handlers);
        recommendedKeywordListLayout.setUiHandlers(handlers);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults) {
        recommendedKeywordListLayout.setRecommendedKeywordList(recommendedKeywordList, firstResult, totalResults);

        recommendedKeywordListLayout.deselectRecommendedKeyword();
    }

    @Override
    public void setSrmItems(String formItemName, ExternalItemsResult externalItemsResult) {
        recommendedKeywordListLayout.getNewRecommendedKeywordWindow().setSrmItems(formItemName, externalItemsResult);
        recommendedKeywordListLayout.getChangeCategoryRecommendedKeywordWindow().setSrmItems(formItemName, externalItemsResult);
    }
}
