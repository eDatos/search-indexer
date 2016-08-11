package es.gobcan.istac.search.web.client.main.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.HideMessageEvent;
import org.siemac.metamac.web.common.client.events.HideMessageEvent.HideMessageHandler;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.SetTitleEvent.SetTitleHandler;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent.ShowMessageHandler;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.client.widgets.MasterHead;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.CloseSessionResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import es.gobcan.istac.search.web.client.events.SelectMainSectionEvent;
import es.gobcan.istac.search.web.client.events.SelectMainSectionEvent.SelectMainSectionEventHandler;
import es.gobcan.istac.search.web.client.main.handlers.MainPageUiHandlers;
import es.gobcan.istac.search.web.client.navigation.NameTokens;
import es.gobcan.istac.search.web.client.widgets.SearchToolStripPresenterWidget;
import es.gobcan.istac.search.web.shared.GetHelpUrlAction;
import es.gobcan.istac.search.web.shared.GetHelpUrlResult;

public class MainPagePresenter extends Presenter<MainPagePresenter.MainView, MainPagePresenter.MainProxy>
        implements
            ShowMessageHandler,
            HideMessageHandler,
            MainPageUiHandlers,
            SetTitleHandler,
            SelectMainSectionEventHandler {

    private static Logger                             logger                 = Logger.getLogger(MainPagePresenter.class.getName());

    private static MasterHead                         masterHead;

    private final DispatchAsync                       dispatcher;

    private final SearchToolStripPresenterWidget      toolStripPresenterWidget;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetNoticesToolBar = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> CONTENT_SLOT           = new Type<RevealContentHandler<?>>();

    public interface MainView extends View, HasUiHandlers<MainPageUiHandlers> {

        void showMessage(Throwable throwable, String message, MessageTypeEnum type);

        void hideMessages();

        void setTitle(String title);

        MasterHead getMasterHead();
    }

    @ProxyStandard
    @NameToken(NameTokens.MAIN_PAGE)
    @NoGatekeeper
    public interface MainProxy extends Proxy<MainPagePresenter>, Place {
    }

    @Inject
    public MainPagePresenter(EventBus eventBus, MainView view, MainProxy proxy, DispatchAsync dispatcher, SearchToolStripPresenterWidget toolStripPresenterWidget) {
        super(eventBus, view, proxy);
        this.toolStripPresenterWidget = toolStripPresenterWidget;
        getView().setUiHandlers(this);
        this.dispatcher = dispatcher;
        MainPagePresenter.masterHead = getView().getMasterHead();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(ShowMessageEvent.getType(), this);
    }

    @ProxyEvent
    @Override
    public void onShowMessage(ShowMessageEvent event) {
        getView().showMessage(event.getThrowable(), event.getMessage(), event.getMessageType());
    }

    @ProxyEvent
    @Override
    public void onHideMessage(HideMessageEvent event) {
        hideMessages();
    }

    @ProxyEvent
    @Override
    public void onSetTitle(SetTitleEvent event) {
        getView().setTitle(event.getTitle());
    }

    @Override
    public void closeSession() {
        dispatcher.execute(new CloseSessionAction(), new AsyncCallback<CloseSessionResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error closing session");
                ShowMessageEvent.fireErrorMessage(MainPagePresenter.this, caught);
            }

            @Override
            public void onSuccess(CloseSessionResult result) {
                Window.Location.assign(result.getLogoutPageUrl());
            }
        });
    }

    private void hideMessages() {
        getView().hideMessages();
    }

    @Override
    public void openHelpUrl() {
        dispatcher.execute(new GetHelpUrlAction(), new WaitingAsyncCallbackHandlingError<GetHelpUrlResult>(this) {

            @Override
            public void onWaitSuccess(GetHelpUrlResult result) {
                Window.open(result.getHelpUrl(), "_blank", "");
            }
        });
    }

    public static MasterHead getMasterHead() {
        return masterHead;
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        setInSlot(TYPE_SetNoticesToolBar, toolStripPresenterWidget);
    }

    @ProxyEvent
    @Override
    public void onSelectMainSection(SelectMainSectionEvent event) {
        toolStripPresenterWidget.deselectButtons();
        toolStripPresenterWidget.selectButton(event.getButtonId().getValue());
    }
}
