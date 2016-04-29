package es.gobcan.istac.search.web.client.widgets;

import org.siemac.metamac.web.common.client.widgets.toolstrip.presenter.MetamacToolStripPresenterWidget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

import es.gobcan.istac.search.web.client.utils.PlaceRequestUtils;

public class SearchToolStripPresenterWidget extends MetamacToolStripPresenterWidget<SearchToolStripPresenterWidget.SearchToolStripView> {

    public interface SearchToolStripView extends MetamacToolStripPresenterWidget.MetamacToolStripView {

        HasClickHandlers getGoReindex();
        HasClickHandlers getGoRecommendedLinkList();
        HasClickHandlers getGoRecommendedKeywordList();
    }

    @Inject
    public SearchToolStripPresenterWidget(EventBus eventBus, SearchToolStripView toolStripView, PlaceManager placeManager) {
        super(eventBus, toolStripView, placeManager);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoReindex().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteReindexPlaceRequest());
            }
        }));

        registerHandler(getView().getGoRecommendedLinkList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteRecommendedLinkListPlaceRequest());
            }
        }));

        registerHandler(getView().getGoRecommendedKeywordList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteRecommendedKeywordListPlaceRequest());
            }
        }));
    }
}
