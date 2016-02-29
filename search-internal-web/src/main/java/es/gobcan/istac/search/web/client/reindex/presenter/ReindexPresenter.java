package es.gobcan.istac.search.web.client.reindex.presenter;

import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import es.gobcan.istac.search.web.client.SearchLoggedInGatekeeper;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.search.web.client.navigation.NameTokens;
import es.gobcan.istac.search.web.client.reindex.view.handlers.ReindexUiHandlers;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionResult;
import es.gobcan.istac.search.web.shared.GetCronRecommendedLinksExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronRecommendedLinksExpressionResult;
import es.gobcan.istac.search.web.shared.GetCronWebExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronWebExpressionResult;

public class ReindexPresenter extends Presenter<ReindexPresenter.ReindexView, ReindexPresenter.ReindexProxy> implements ReindexUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.REINDEX_PAGE)
    @UseGatekeeper(SearchLoggedInGatekeeper.class)
    public interface ReindexProxy extends Proxy<ReindexPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return SearchWeb.getConstants().reindex();
    }

    public interface ReindexView extends View, HasUiHandlers<ReindexUiHandlers> {

        void setCronGpeExpression(String cronGpeExpression);
        void setCronWebExpression(String cronWebExpression);
        void setCronRecommendedLinksExpression(String cronRecommendedLinksExpression);

    }

    @Inject
    public ReindexPresenter(EventBus eventBus, ReindexView view, ReindexProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.CONTENT_SLOT, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        MainPagePresenter.getMasterHead().setTitleLabel(SearchWeb.getConstants().reindex());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        dispatcher.execute(new GetCronWebExpressionAction(), new WaitingAsyncCallbackHandlingError<GetCronWebExpressionResult>(this) {

            @Override
            public void onWaitSuccess(GetCronWebExpressionResult result) {
                getView().setCronWebExpression(result.getCronWebExpression());
            }
        });

        dispatcher.execute(new GetCronGpeExpressionAction(), new WaitingAsyncCallbackHandlingError<GetCronGpeExpressionResult>(this) {

            @Override
            public void onWaitSuccess(GetCronGpeExpressionResult result) {
                getView().setCronGpeExpression(result.getCronGpeExpression());
            }
        });

        dispatcher.execute(new GetCronRecommendedLinksExpressionAction(), new WaitingAsyncCallbackHandlingError<GetCronRecommendedLinksExpressionResult>(this) {

            @Override
            public void onWaitSuccess(GetCronRecommendedLinksExpressionResult result) {
                getView().setCronRecommendedLinksExpression(result.getCronRecommendedLinksExpression());
            }
        });
    }

    @Override
    public void reindexWeb() {
        // TODO
        Window.alert("reindexWeb not implemented");
    }

    @Override
    public void reindexGpe() {
        // TODO
        Window.alert("reindexGpe not implemented");
    }

    @Override
    public void reindexRecommendedLinks() {
        // TODO
        Window.alert("reindexRecommendedLinks not implemented");
    }

}
