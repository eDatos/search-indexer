package es.gobcan.istac.search.web.client.recommendedlink.view;

import java.util.List;

import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.recommendedlink.presenter.RecommendedLinkListPresenter;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.ImportRecommendedLinksWindow;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.RecommendedLinkListLayout;
import es.gobcan.istac.search.web.client.utils.RecordUtils;

public class RecommendedLinkListViewImpl extends ViewWithUiHandlers<RecommendedLinkListUiHandlers> implements RecommendedLinkListPresenter.RecommendedLinkListView {

    private VLayout                   panel;
    private RecommendedLinkListLayout recommendedLinkListLayout;

    public RecommendedLinkListViewImpl() {
        recommendedLinkListLayout = new RecommendedLinkListLayout();

        attachHandlers();

        panel = new VLayout();
        panel.addMember(recommendedLinkListLayout);
    }

    private void attachHandlers() {

        final BaseCustomListGrid recommendedLinkListGrid = recommendedLinkListLayout.getRecommendedLinkListGrid();

        recommendedLinkListLayout.getNewRecommendedLinkToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                recommendedLinkListLayout.newRecommendedLink();
            }
        });

        recommendedLinkListLayout.getDeleteRecommendedLinkToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (recommendedLinkListGrid.getSelectedRecords() != null) {
                    getUiHandlers().deleteRecommendedLinks(RecordUtils.getRecommendedLinkListIds(recommendedLinkListGrid.getSelectedRecords()));
                }
            }
        });

        recommendedLinkListLayout.getExportToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (recommendedLinkListGrid.getSelectedRecords() != null && recommendedLinkListGrid.getSelectedRecords().length > 0) {
                    getUiHandlers().exportRecommendedLinks(RecordUtils.getRecommendedLinkListIds(recommendedLinkListGrid.getSelectedRecords()));
                } else {
                    getUiHandlers().exportRecommendedLinks();
                }
            }
        });

        recommendedLinkListLayout.getImportToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ImportRecommendedLinksWindow importRecommendedLinksWindow = new ImportRecommendedLinksWindow() {

                    @Override
                    protected void uploadSuccess(String message) {
                        getUiHandlers().recommendedLinksImportationSucceed(message);
                    }

                    @Override
                    protected void uploadFailed(String error) {
                        getUiHandlers().recommendedLinksImportationFailed(error);
                    }
                };

                importRecommendedLinksWindow.show();
            }
        });

        recommendedLinkListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {

                if (recommendedLinkListGrid.getSelectedRecords() == null) {
                    recommendedLinkListLayout.deselectRecommendedLink();
                } else {
                    if (recommendedLinkListGrid.getSelectedRecords().length != 1) {
                        recommendedLinkListLayout.deselectRecommendedLink();
                    } else {
                        RecommendedLinkRecord record = (RecommendedLinkRecord) recommendedLinkListGrid.getSelectedRecord();
                        recommendedLinkListLayout.selectRecommendedLink(record.getRecommendedLinkdDto());
                    }

                    if (recommendedLinkListGrid.getSelectedRecords().length > 0) {
                        recommendedLinkListLayout.getDeleteRecommendedLinkToolStripButton().show();
                    }
                }
            }
        });
    }

    @Override
    public void setUiHandlers(RecommendedLinkListUiHandlers handlers) {
        super.setUiHandlers(handlers);
        recommendedLinkListLayout.setUiHandlers(handlers);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setRecommendedLinkList(List<RecommendedLinkDto> recommendedLinkList, int firstResult, int totalResults) {
        recommendedLinkListLayout.setRecommendedLinkList(recommendedLinkList, firstResult, totalResults);
        recommendedLinkListLayout.deselectRecommendedLink();
    }

    @Override
    public void setRecommendedKeywordListForField(String fieldName, List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults) {
        recommendedLinkListLayout.getRecommendedLinkLayout().setRecommendedKeywordListForField(fieldName, recommendedKeywordList, firstResult, totalResults);
    }
}
