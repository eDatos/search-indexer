package es.gobcan.istac.search.web.client.recommendedlink.view;

import java.util.List;

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
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.recommendedlink.presenter.RecommendedLinkListPresenter;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.DeleteRecommendedKeywordWindow;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.ImportRecommendedLinksWindow;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.NewRecommendedKeywordWindow;
import es.gobcan.istac.search.web.client.recommendedlink.widgets.RecommendedLinkListLayout;
import es.gobcan.istac.search.web.client.utils.RecordUtils;

public class RecommendedLinkListViewImpl extends ViewWithUiHandlers<RecommendedLinkListUiHandlers> implements RecommendedLinkListPresenter.RecommendedLinkListView {

    private VLayout                   panel;
    private RecommendedLinkListLayout recommendedLinkListLayout;

    public RecommendedLinkListViewImpl() {
        recommendedLinkListLayout = new RecommendedLinkListLayout();

        createNewRecommendedKeywordWindow();
        createDeleteRecommendedKeywordWindow();
        attachHandlers();

        panel = new VLayout();
        panel.addMember(recommendedLinkListLayout);
    }

    private void createNewRecommendedKeywordWindow() {

        NewRecommendedKeywordWindow newRecommendedKeywordWindow = new NewRecommendedKeywordWindow();
        recommendedLinkListLayout.setNewRecommendedKeywordWindow(newRecommendedKeywordWindow);
        recommendedLinkListLayout.getNewRecommendedKeywordWindow().hide();
        recommendedLinkListLayout.getNewRecommendedKeywordWindow().getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                if (recommendedLinkListLayout.getNewRecommendedKeywordWindow().validateForm()) {
                    getUiHandlers().saveRecommendedKeyword(recommendedLinkListLayout.getNewRecommendedKeywordWindow().getRecommendedKeyword());
                    recommendedLinkListLayout.getNewRecommendedKeywordWindow().hide();
                }
            }
        });
    }

    private void createDeleteRecommendedKeywordWindow() {

        DeleteRecommendedKeywordWindow deleteRecommendedKeywordWindow = new DeleteRecommendedKeywordWindow(getUiHandlers());
        deleteRecommendedKeywordWindow.hide();
        recommendedLinkListLayout.setDeleteRecommendedKeywordWindow(deleteRecommendedKeywordWindow);
        recommendedLinkListLayout.getDeleteRecommendedKeywordWindow().getDelete().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                recommendedLinkListLayout.getDeleteRecommendedKeywordWindow().hide();
                getUiHandlers().deleteRecommendedKeyword(recommendedLinkListLayout.getDeleteRecommendedKeywordWindow().getRecommendedKeywordId());
                getUiHandlers().retrieveRecommendedLinkList();
            }
        });
    }

    private void attachHandlers() {

        final BaseCustomListGrid recommendedLinkListGrid = recommendedLinkListLayout.getRecommendedLinkListGrid();

        recommendedLinkListLayout.getNewKeywordToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                recommendedLinkListLayout.getNewRecommendedKeywordWindow().show();
            }
        });

        recommendedLinkListLayout.getDeleteRecommendedKeywordToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().retrieveRecommendedKeywordList();
                recommendedLinkListLayout.getDeleteRecommendedKeywordWindow().show();
            }
        });

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

        recommendedLinkListLayout.getRecommendedLinkLayout().getRecommendedLinkMainFormLayout().getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                RecommendedLinkDto recommendedLinkDto = recommendedLinkListLayout.getRecommendedLinkLayout().getRecommendedLink();

                if (recommendedLinkListLayout.getRecommendedLinkLayout().validateForm()) {
                    getUiHandlers().saveRecommendedLink(recommendedLinkDto);
                }
            }
        });

        recommendedLinkListLayout.getRecommendedLinkLayout().getRecommendedLinkMainFormLayout().getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // If it is a new user, hide mainFormLayout
                if (recommendedLinkListLayout.getRecommendedLinkLayout().getRecommendedLink().getId() == null) {
                    recommendedLinkListLayout.deselectRecommendedLink();
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
    public void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList) {
        recommendedLinkListLayout.getRecommendedLinkLayout().setRecommendedKeywordList(recommendedKeywordList);
        if (recommendedLinkListLayout.getDeleteRecommendedKeywordWindow() != null) {
            recommendedLinkListLayout.getDeleteRecommendedKeywordWindow().setRecommendedKeywordList(recommendedKeywordList);
        }
    }

    @Override
    public void setSrmItems(String formItemName, ExternalItemsResult externalItemsResult) {
        recommendedLinkListLayout.getNewRecommendedKeywordWindow().setSrmItems(formItemName, externalItemsResult);
    }
}
