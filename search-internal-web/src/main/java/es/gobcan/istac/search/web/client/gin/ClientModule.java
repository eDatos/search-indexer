package es.gobcan.istac.search.web.client.gin;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import es.gobcan.istac.search.web.client.SearchLoggedInGatekeeper;
import es.gobcan.istac.search.web.client.SearchPlaceManager;
import es.gobcan.istac.search.web.client.SearchWebConstants;
import es.gobcan.istac.search.web.client.SearchWebMessages;
import es.gobcan.istac.search.web.client.main.presenter.ErrorPagePresenter;
import es.gobcan.istac.search.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.search.web.client.main.presenter.ToolStripPresenterWidget;
import es.gobcan.istac.search.web.client.main.presenter.UnauthorizedPagePresenter;
import es.gobcan.istac.search.web.client.main.view.ErrorPageViewImpl;
import es.gobcan.istac.search.web.client.main.view.MainPageViewImpl;
import es.gobcan.istac.search.web.client.main.view.ToolStripViewImpl;
import es.gobcan.istac.search.web.client.main.view.UnauthorizedPageViewImpl;
import es.gobcan.istac.search.web.client.navigation.NameTokens;
import es.gobcan.istac.search.web.client.reindex.presenter.ReindexPresenter;
import es.gobcan.istac.search.web.client.reindex.view.ReindexViewImpl;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Map EventBus, TokenFormatter, RootPresenter, PlaceManager y GoogleAnalytics
        install(new DefaultModule(SearchPlaceManager.class));

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.REINDEX_PAGE);

        // Main presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainView.class, MainPageViewImpl.class, MainPagePresenter.MainProxy.class);
        bindPresenter(ReindexPresenter.class, ReindexPresenter.ReindexView.class, ReindexViewImpl.class, ReindexPresenter.ReindexProxy.class);

        // PresenterWidgets
        bindSingletonPresenterWidget(ToolStripPresenterWidget.class, ToolStripPresenterWidget.ToolStripView.class, ToolStripViewImpl.class);

        // Gate keeper
        bind(SearchLoggedInGatekeeper.class).in(Singleton.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);

        // Interfaces
        bind(SearchWebConstants.class).in(Singleton.class);
        bind(SearchWebMessages.class).in(Singleton.class);
    }
}
