package es.gobcan.istac.search.web.client.reindex.presenter;

import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.search.core.dto.IndexationStatusDto;
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
import es.gobcan.istac.search.web.shared.GetIndexationStatusGpeAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusGpeResult;
import es.gobcan.istac.search.web.shared.GetIndexationStatusRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusRecommendedLinksResult;
import es.gobcan.istac.search.web.shared.GetIndexationStatusStatisticalResourcesAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusStatisticalResourcesResult;
import es.gobcan.istac.search.web.shared.GetIndexationStatusWebAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusWebResult;
import es.gobcan.istac.search.web.shared.ReindexGpeAction;
import es.gobcan.istac.search.web.shared.ReindexGpeResult;
import es.gobcan.istac.search.web.shared.ReindexRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.ReindexRecommendedLinksResult;
import es.gobcan.istac.search.web.shared.ReindexStatisticalResourcesAction;
import es.gobcan.istac.search.web.shared.ReindexStatisticalResourcesResult;
import es.gobcan.istac.search.web.shared.ReindexWebAction;
import es.gobcan.istac.search.web.shared.ReindexWebResult;

public class ReindexPresenter extends Presenter<ReindexPresenter.ReindexView, ReindexPresenter.ReindexProxy> implements ReindexUiHandlers {

    private final DispatchAsync dispatcher;

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

        void setIndexationWebStatus(IndexationStatusDto indexationStatus);
        void setIndexationGpeStatus(IndexationStatusDto indexationStatus);
        void setIndexationRecommendedLinksStatus(IndexationStatusDto indexationStatus);
        void setIndexationStatisticalResourcesStatus(IndexationStatusDto indexationStatus);

        void disableReindexWebStartButton();
        void disableReindexGpeStartButton();
        void disableReindexRecommendedLinksStartButton();
        void disableReindexStatisticalResourcesStartButton();

        void updateReindexGpeButtonStartDisability(IndexationStatusDto indexationStatus);
        void updateReindexRecommendedLinksButtonStartDisability(IndexationStatusDto indexationStatus);
        void updateReindexWebButtonStartDisability(IndexationStatusDto indexationStatus);
        void updateReindexStatisticalResourcesButtonStartDisability(IndexationStatusDto indexationStatus);
    }

    @Inject
    public ReindexPresenter(EventBus eventBus, ReindexView view, ReindexProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    private void startPollingIndexationStatus() {

        Timer refreshTimer = new Timer() {

            @Override
            public void run() {
                updateIndexationStatus();
            }
        };

        refreshTimer.scheduleRepeating(4000); // ms
    }

    void updateIndexationStatus() {
        updateIndexationWebStatus();
        updateIndexationGpeStatus();
        updateIndexationRecommendedLinksStatus();
        updateIndexationStatisticalResourcesStatus();
    }

    private void updateIndexationWebStatus() {
        dispatcher.execute(new GetIndexationStatusWebAction(), new AsyncCallback<GetIndexationStatusWebResult>() {

            @Override
            public void onSuccess(GetIndexationStatusWebResult result) {
                getView().setIndexationWebStatus(result.getIndexationStatus());
            }

            @Override
            public void onFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(ReindexPresenter.this, caught);
            }
        });
    }

    private void updateIndexationGpeStatus() {
        dispatcher.execute(new GetIndexationStatusGpeAction(), new AsyncCallback<GetIndexationStatusGpeResult>() {

            @Override
            public void onSuccess(GetIndexationStatusGpeResult result) {
                getView().setIndexationGpeStatus(result.getIndexationStatus());
            }

            @Override
            public void onFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(ReindexPresenter.this, caught);
                getView().updateReindexGpeButtonStartDisability(null);
            }
        });
    }

    private void updateIndexationRecommendedLinksStatus() {
        dispatcher.execute(new GetIndexationStatusRecommendedLinksAction(), new AsyncCallback<GetIndexationStatusRecommendedLinksResult>() {

            @Override
            public void onSuccess(GetIndexationStatusRecommendedLinksResult result) {
                getView().setIndexationRecommendedLinksStatus(result.getIndexationStatus());
            }

            @Override
            public void onFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(ReindexPresenter.this, caught);
                getView().updateReindexRecommendedLinksButtonStartDisability(null);
            }
        });
    }

    private void updateIndexationStatisticalResourcesStatus() {
        dispatcher.execute(new GetIndexationStatusStatisticalResourcesAction(), new AsyncCallback<GetIndexationStatusStatisticalResourcesResult>() {

            @Override
            public void onSuccess(GetIndexationStatusStatisticalResourcesResult result) {
                getView().setIndexationStatisticalResourcesStatus(result.getIndexationStatus());
            }

            @Override
            public void onFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(ReindexPresenter.this, caught);
                getView().updateReindexStatisticalResourcesButtonStartDisability(null);
            }
        });
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

        updateIndexationStatus();
        startPollingIndexationStatus();

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
        getView().disableReindexWebStartButton();

        dispatcher.execute(new ReindexWebAction(), new WaitingAsyncCallbackHandlingError<ReindexWebResult>(this) {

            @Override
            public void onWaitSuccess(ReindexWebResult result) {
                updateIndexationWebStatus();
            }
        });
    }

    @Override
    public void reindexGpe() {
        getView().disableReindexGpeStartButton();

        dispatcher.execute(new ReindexGpeAction(), new WaitingAsyncCallbackHandlingError<ReindexGpeResult>(this) {

            @Override
            public void onWaitSuccess(ReindexGpeResult result) {
                updateIndexationGpeStatus();
            }
        });
    }

    @Override
    public void reindexRecommendedLinks() {
        getView().disableReindexRecommendedLinksStartButton();

        dispatcher.execute(new ReindexRecommendedLinksAction(), new WaitingAsyncCallbackHandlingError<ReindexRecommendedLinksResult>(this) {

            @Override
            public void onWaitSuccess(ReindexRecommendedLinksResult result) {
                updateIndexationRecommendedLinksStatus();
            }

        });
    }

    @Override
    public void reindexStatisticalResources() {
        getView().disableReindexStatisticalResourcesStartButton();

        dispatcher.execute(new ReindexStatisticalResourcesAction(), new WaitingAsyncCallbackHandlingError<ReindexStatisticalResourcesResult>(this) {

            @Override
            public void onWaitSuccess(ReindexStatisticalResourcesResult result) {
                updateIndexationStatisticalResourcesStatus();
            }

        });
    }

    @Override
    public void showMessageIfStatusChangedToFinished(IndexationStatusDto indexationStatus, String oldStatus, String message) {
        if (indexationStatus != null && indexationStatus.isStopped()) {
            if (oldStatus != null && !IndexacionStatusDomain.PARADO.getSiglas().equals(oldStatus)) {
                ShowMessageEvent.fireSuccessMessage(ReindexPresenter.this, message);
            }
        }
    }

}
