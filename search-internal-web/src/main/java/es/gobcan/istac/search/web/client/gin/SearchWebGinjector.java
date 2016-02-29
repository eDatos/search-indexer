package es.gobcan.istac.search.web.client.gin;

import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

import es.gobcan.istac.search.web.client.SearchLoggedInGatekeeper;
import es.gobcan.istac.search.web.client.main.presenter.ErrorPagePresenter;
import es.gobcan.istac.search.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.search.web.client.main.presenter.UnauthorizedPagePresenter;
import es.gobcan.istac.search.web.client.reindex.presenter.ReindexPresenter;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface SearchWebGinjector extends MetamacWebGinjector {

    SearchLoggedInGatekeeper getLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();
    AsyncProvider<ReindexPresenter> getReindexPresenter();

    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();
}
