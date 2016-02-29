package es.gobcan.istac.search.web.client.reindex.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import es.gobcan.istac.search.web.client.reindex.presenter.ReindexPresenter;
import es.gobcan.istac.search.web.client.reindex.view.handlers.ReindexUiHandlers;
import es.gobcan.istac.search.web.client.reindex.widgets.ReindexLayout;

public class ReindexViewImpl extends ViewWithUiHandlers<ReindexUiHandlers> implements ReindexPresenter.ReindexView {

    private VLayout       panel;
    private ReindexLayout reindexLayout;

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

}
