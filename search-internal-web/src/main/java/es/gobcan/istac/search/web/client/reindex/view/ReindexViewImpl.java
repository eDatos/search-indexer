package es.gobcan.istac.search.web.client.reindex.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.web.client.reindex.presenter.ReindexPresenter;
import es.gobcan.istac.search.web.client.reindex.view.handlers.ReindexUiHandlers;
import es.gobcan.istac.search.web.client.reindex.widgets.ReindexLayout;

public class ReindexViewImpl extends ViewWithUiHandlers<ReindexUiHandlers> implements ReindexPresenter.ReindexView {

    private VLayout       panel;
    private ReindexLayout reindexLayout;

    @Inject
    public ReindexViewImpl() {
        reindexLayout = new ReindexLayout();

        panel = new VLayout();
        panel.addMember(reindexLayout);

        reindexLayout.getReindexWebStartButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().reindexWeb();
            }
        });

        reindexLayout.getReindexGpeStartButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().reindexGpe();
            }
        });

        reindexLayout.getReindexRecommendedLinksStartButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().reindexRecommendedLinks();
            }
        });
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setCronGpeExpression(String cronGpeExpression) {
        reindexLayout.setCronGpeExpression(cronGpeExpression);
    }

    @Override
    public void setCronWebExpression(String cronWebExpression) {
        reindexLayout.setCronWebExpression(cronWebExpression);
    }

    @Override
    public void setCronRecommendedLinksExpression(String cronRecommendedLinksExpression) {
        reindexLayout.setCronRecommendedLinksExpression(cronRecommendedLinksExpression);
    }

    @Override
    public void setIndexationWebStatus(IndexationStatusDto indexationStatus) {
        updateReindexWebButtonStartDisability(indexationStatus);
        getUiHandlers().showMessageIfStatusChangedToFinished(indexationStatus, reindexLayout.getIndexationWebStatus(), "Indexación web realizada con éxito");
        reindexLayout.setIndexationWebStatus(indexationStatus);
    }

    @Override
    public void setIndexationGpeStatus(IndexationStatusDto indexationStatus) {
        updateReindexGpeButtonStartDisability(indexationStatus);
        getUiHandlers().showMessageIfStatusChangedToFinished(indexationStatus, reindexLayout.getIndexationGpeStatus(), "Indexación gpe realizada con éxito");
        reindexLayout.setIndexationGpeStatus(indexationStatus);
    }

    @Override
    public void setIndexationRecommendedLinksStatus(IndexationStatusDto indexationStatus) {
        updateReindexRecommendedLinksButtonStartDisability(indexationStatus);
        getUiHandlers().showMessageIfStatusChangedToFinished(indexationStatus, reindexLayout.getIndexationRecommendedLinksStatus(), "Indexación links recomendados realizada con éxito");
        reindexLayout.setIndexationRecommendedLinksStatus(indexationStatus);
    }

    @Override
    public void disableReindexWebStartButton() {
        reindexLayout.getReindexWebStartButton().disable();
    }

    @Override
    public void disableReindexGpeStartButton() {
        reindexLayout.getReindexGpeStartButton().disable();
    }

    @Override
    public void disableReindexRecommendedLinksStartButton() {
        reindexLayout.getReindexRecommendedLinksStartButton().disable();
    }

    @Override
    public void updateReindexWebButtonStartDisability(IndexationStatusDto indexationStatus) {
        if (indexationStatus == null || !indexationStatus.isIndexing()) {
            reindexLayout.getReindexWebStartButton().enable();
        } else {
            disableReindexWebStartButton();
        }
    }

    @Override
    public void updateReindexGpeButtonStartDisability(IndexationStatusDto indexationStatus) {
        if (indexationStatus == null || !indexationStatus.isIndexing()) {
            reindexLayout.getReindexGpeStartButton().enable();
        } else {
            disableReindexGpeStartButton();
        }
    }

    @Override
    public void updateReindexRecommendedLinksButtonStartDisability(IndexationStatusDto indexationStatus) {
        if (indexationStatus == null || !indexationStatus.isIndexing()) {
            reindexLayout.getReindexRecommendedLinksStartButton().enable();
        } else {
            disableReindexRecommendedLinksStartButton();
        }
    }

}
