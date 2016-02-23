package es.gobcan.istac.searchmanagement.web.client.gin;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import es.gobcan.istac.searchmanagement.web.client.NameTokens;
import es.gobcan.istac.searchmanagement.web.client.SearchManagementPlaceManager;
import es.gobcan.istac.searchmanagement.web.client.main.presenter.ErrorPagePresenter;
import es.gobcan.istac.searchmanagement.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.searchmanagement.web.client.main.presenter.ToolStripPresenterWidget;
import es.gobcan.istac.searchmanagement.web.client.main.presenter.UnauthorizedPagePresenter;
import es.gobcan.istac.searchmanagement.web.client.main.view.ErrorPageViewImpl;
import es.gobcan.istac.searchmanagement.web.client.main.view.MainPageViewImpl;
import es.gobcan.istac.searchmanagement.web.client.main.view.ToolStripViewImpl;
import es.gobcan.istac.searchmanagement.web.client.main.view.UnauthorizedPageViewImpl;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Map EventBus, TokenFormatter, RootPresenter, PlaceManager y GoogleAnalytics
        install(new DefaultModule(SearchManagementPlaceManager.class));

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.mainPage);

        // Main presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainView.class, MainPageViewImpl.class, MainPagePresenter.MainProxy.class);

        // PresenterWidgets
        bindSingletonPresenterWidget(ToolStripPresenterWidget.class, ToolStripPresenterWidget.ToolStripView.class, ToolStripViewImpl.class);

        // Gate keeper
        bind(es.gobcan.istac.searchmanagement.web.client.LoggedInGatekeeper.class).in(Singleton.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);
    }
}
