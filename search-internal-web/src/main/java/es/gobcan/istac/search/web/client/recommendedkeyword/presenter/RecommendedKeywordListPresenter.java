package es.gobcan.istac.search.web.client.recommendedkeyword.presenter;

import static es.gobcan.istac.search.web.client.SearchWeb.getMessages;

import java.util.List;

import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

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

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchLoggedInGatekeeper;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.enums.SearchToolStripButtonEnum;
import es.gobcan.istac.search.web.client.events.SelectMainSectionEvent;
import es.gobcan.istac.search.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.search.web.client.navigation.NameTokens;
import es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers.RecommendedKeywordListUiHandlers;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesAction;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesResult;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedKeywordListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordResult;
import es.gobcan.istac.search.web.shared.recommendedlink.UpdateRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.UpdateRecommendedKeywordListResult;

public class RecommendedKeywordListPresenter extends Presenter<RecommendedKeywordListPresenter.RecommendedKeywordListView, RecommendedKeywordListPresenter.RecommendedKeywordListProxy>
        implements
            RecommendedKeywordListUiHandlers {

    private final DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.RECOMMENDED_KEYWORD_LIST_PAGE)
    @UseGatekeeper(SearchLoggedInGatekeeper.class)
    public interface RecommendedKeywordListProxy extends Proxy<RecommendedKeywordListPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return SearchWeb.getConstants().recommendedKeywords();
    }

    public interface RecommendedKeywordListView extends View, HasUiHandlers<RecommendedKeywordListUiHandlers> {

        void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults);
        void setSrmItems(String formItemName, ExternalItemsResult externalItemsResult);

    }

    @Inject
    public RecommendedKeywordListPresenter(EventBus eventBus, RecommendedKeywordListView view, RecommendedKeywordListProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.CONTENT_SLOT, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        SelectMainSectionEvent.fire(this, SearchToolStripButtonEnum.RECOMMENDED_KEYWORDS);
        MainPagePresenter.getMasterHead().setTitleLabel(SearchWeb.getConstants().recommendedKeywords());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        retrieveRecommendedKeywordList();
    }

    @Override
    public void retrieveRecommendedKeywordList(RecommendedKeywordWebCriteria criteria) {
        dispatcher.execute(new GetRecommendedKeywordListAction(criteria), new WaitingAsyncCallbackHandlingError<GetRecommendedKeywordListResult>(this) {

            @Override
            public void onWaitSuccess(GetRecommendedKeywordListResult result) {
                getView().setRecommendedKeywordList(result.getRecommendedKeywordList(), result.getFirstResult(), result.getTotalResults());
            }
        });
    }

    @Override
    public void saveRecommendedKeyword(RecommendedKeywordDto recommendedKeyword) {
        dispatcher.execute(new SaveRecommendedKeywordAction(recommendedKeyword), new WaitingAsyncCallbackHandlingError<SaveRecommendedKeywordResult>(this) {

            @Override
            public void onWaitSuccess(SaveRecommendedKeywordResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedKeywordListPresenter.this, getMessages().messageCreateRecommendedKeywordSuccess());
                retrieveRecommendedKeywordList();
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedKeywordListPresenter.this, caught);
                retrieveRecommendedKeywordList();
            }
        });
    }

    @Override
    public void deleteRecommendedKeywords(List<Long> recommendedKeywordListIds) {
        dispatcher.execute(new DeleteRecommendedKeywordListAction(recommendedKeywordListIds), new WaitingAsyncCallbackHandlingError<DeleteRecommendedKeywordListResult>(this) {

            @Override
            public void onWaitSuccess(DeleteRecommendedKeywordListResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedKeywordListPresenter.this, getMessages().messageDeleteRecommendedKeywordSuccess());
                retrieveRecommendedKeywordList();
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedKeywordListPresenter.this, caught);
                retrieveRecommendedKeywordList();
            }
        });
    }

    @Override
    public void retrieveSrmItems(final String formItemName, SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(itemRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetExternalResourcesResult>(this) {

            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setSrmItems(formItemName, result.getExternalItemsResult());
            }
        });
    }

    @Override
    public void updateRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordListDto) {
        dispatcher.execute(new UpdateRecommendedKeywordListAction(recommendedKeywordListDto), new WaitingAsyncCallbackHandlingError<UpdateRecommendedKeywordListResult>(this) {

            @Override
            public void onWaitSuccess(UpdateRecommendedKeywordListResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedKeywordListPresenter.this, getMessages().messageUpdateRecommendedKeywordSuccess());
                retrieveRecommendedKeywordList();
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedKeywordListPresenter.this, caught);
                retrieveRecommendedKeywordList();
            }
        });
    }

    private void retrieveRecommendedKeywordList() {
        retrieveRecommendedKeywordList(new RecommendedKeywordWebCriteria());
    }
}
