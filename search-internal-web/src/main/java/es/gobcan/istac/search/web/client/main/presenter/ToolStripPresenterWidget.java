package es.gobcan.istac.search.web.client.main.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ToolStripPresenterWidget extends PresenterWidget<ToolStripPresenterWidget.ToolStripView> {

    public interface ToolStripView extends View {

        HasClickHandlers getExampleButton();
    }

    @Inject
    public ToolStripPresenterWidget(EventBus eventBus, ToolStripView toolStripView) {
        super(eventBus, toolStripView);
    }

    @Override
    protected void onBind() {
        super.onBind();
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }

    @Override
    protected void onHide() {
        super.onHide();
    }

    @Override
    protected void onUnbind() {
        super.onUnbind();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
