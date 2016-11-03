package es.gobcan.istac.search.web.client.recommendedlink.presenter;

import static es.gobcan.istac.search.web.client.SearchWeb.getMessages;

import java.util.List;

import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

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
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.web.client.SearchLoggedInGatekeeper;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.enums.SearchToolStripButtonEnum;
import es.gobcan.istac.search.web.client.events.SelectMainSectionEvent;
import es.gobcan.istac.search.web.client.main.presenter.MainPagePresenter;
import es.gobcan.istac.search.web.client.navigation.NameTokens;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.CommonUtils;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;
import es.gobcan.istac.search.web.shared.recommendedlink.CreateRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.CreateRecommendedLinksResult;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedLinkListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksResult;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedLinkListResult;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedLinkAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedLinkResult;

public class RecommendedLinkListPresenter extends Presenter<RecommendedLinkListPresenter.RecommendedLinkListView, RecommendedLinkListPresenter.RecommendedLinkListProxy>
        implements
            RecommendedLinkListUiHandlers {

    private final DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.RECOMMENDED_LINK_LIST_PAGE)
    @UseGatekeeper(SearchLoggedInGatekeeper.class)
    public interface RecommendedLinkListProxy extends Proxy<RecommendedLinkListPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return SearchWeb.getConstants().recommendedLinks();
    }

    public interface RecommendedLinkListView extends View, HasUiHandlers<RecommendedLinkListUiHandlers> {

        void setRecommendedLinkList(List<RecommendedLinkDto> recommendedLinkList, int firstResult, int totalResults);
        void setRecommendedKeywordListForField(String fieldName, List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults);
    }

    @Inject
    public RecommendedLinkListPresenter(EventBus eventBus, RecommendedLinkListView view, RecommendedLinkListProxy proxy, DispatchAsync dispatcher) {
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

        SelectMainSectionEvent.fire(this, SearchToolStripButtonEnum.RECOMMENDED_LINKS);
        MainPagePresenter.getMasterHead().setTitleLabel(SearchWeb.getConstants().recommendedLinks());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        retrieveRecommendedLinkList();
    }

    @Override
    public void retrieveRecommendedLinkList(RecommendedLinkWebCriteria criteria) {
        dispatcher.execute(new GetRecommendedLinkListAction(criteria), new WaitingAsyncCallbackHandlingError<GetRecommendedLinkListResult>(this) {

            @Override
            public void onWaitSuccess(GetRecommendedLinkListResult result) {
                getView().setRecommendedLinkList(result.getRecommendedLinkList(), result.getFirstResult(), result.getTotalResults());
            }
        });
    }

    public void retrieveRecommendedLinkList() {
        retrieveRecommendedLinkList(new RecommendedLinkWebCriteria());
    }

    @Override
    public void retrieveRecommendedKeywordListForField(final String fieldName, RecommendedKeywordWebCriteria criteria) {
        dispatcher.execute(new GetRecommendedKeywordListAction(criteria), new WaitingAsyncCallbackHandlingError<GetRecommendedKeywordListResult>(this) {

            @Override
            public void onWaitSuccess(GetRecommendedKeywordListResult result) {
                getView().setRecommendedKeywordListForField(fieldName, result.getRecommendedKeywordList(), result.getFirstResult(), result.getTotalResults());
            }
        });
    }

    @Override
    public void recommendedLinksImportationSucceed(String message) {
        ShowMessageEvent.fireSuccessMessage(RecommendedLinkListPresenter.this, message);
        retrieveRecommendedLinkList();
    }

    @Override
    public void recommendedLinksImportationFailed(String error) {
        ShowMessageEvent.fireErrorMessage(RecommendedLinkListPresenter.this, error);
        retrieveRecommendedLinkList();
    }

    @Override
    public void saveRecommendedLink(RecommendedLinkDto recommendedLink) {
        dispatcher.execute(new SaveRecommendedLinkAction(recommendedLink), new WaitingAsyncCallbackHandlingError<SaveRecommendedLinkResult>(this) {

            @Override
            public void onWaitSuccess(SaveRecommendedLinkResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedLinkListPresenter.this, getMessages().messageCreateRecommendedLinkSuccess());
                retrieveRecommendedLinkList();
            }
        });
    }

    @Override
    public void createRecommendedLinks(RecommendedLinkGroupedKeywordsDto recommendedLinkGroupedKeywordsDto) {
        dispatcher.execute(new CreateRecommendedLinksAction(recommendedLinkGroupedKeywordsDto), new WaitingAsyncCallbackHandlingError<CreateRecommendedLinksResult>(this) {

            @Override
            public void onWaitSuccess(CreateRecommendedLinksResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedLinkListPresenter.this, getMessages().messageCreateRecommendedLinksSuccess());
                retrieveRecommendedLinkList();
            }
        });
    }

    @Override
    public void exportRecommendedLinks() {
        dispatcher.execute(new ExportRecommendedLinksAction(), new WaitingAsyncCallbackHandlingError<ExportRecommendedLinksResult>(this) {

            @Override
            public void onWaitSuccess(ExportRecommendedLinksResult result) {
                CommonUtils.downloadFile(result.getFileName());
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedLinkListPresenter.this, caught);
            }

        });
    }

    @Override
    public void exportRecommendedLinks(List<Long> recommendedLinkListIds) {
        dispatcher.execute(new ExportRecommendedLinksListAction(recommendedLinkListIds), new WaitingAsyncCallbackHandlingError<ExportRecommendedLinksListResult>(this) {

            @Override
            public void onWaitSuccess(ExportRecommendedLinksListResult result) {
                CommonUtils.downloadFile(result.getFileName());
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedLinkListPresenter.this, caught);
            }

        });
    }

    @Override
    public void deleteRecommendedLinks(List<Long> recommendedLinkListIds) {
        dispatcher.execute(new DeleteRecommendedLinkListAction(recommendedLinkListIds), new WaitingAsyncCallbackHandlingError<DeleteRecommendedLinkListResult>(this) {

            @Override
            public void onWaitSuccess(DeleteRecommendedLinkListResult result) {
                ShowMessageEvent.fireSuccessMessage(RecommendedLinkListPresenter.this, getMessages().messageDeleteRecommendedLinkSuccess());
                retrieveRecommendedLinkList();
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(RecommendedLinkListPresenter.this, caught);
                retrieveRecommendedLinkList();
            }

        });
    }
}
