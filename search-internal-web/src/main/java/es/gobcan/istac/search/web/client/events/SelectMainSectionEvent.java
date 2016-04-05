package es.gobcan.istac.search.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import es.gobcan.istac.search.web.client.enums.SearchToolStripButtonEnum;

public class SelectMainSectionEvent extends GwtEvent<SelectMainSectionEvent.SelectMainSectionEventHandler> {

    public interface SelectMainSectionEventHandler extends EventHandler {

        void onSelectMainSection(SelectMainSectionEvent event);
    }

    private static Type<SelectMainSectionEventHandler> TYPE = new Type<SelectMainSectionEventHandler>();
    private final SearchToolStripButtonEnum            buttonId;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMainSectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, SearchToolStripButtonEnum buttonId) {
        if (TYPE != null) {
            source.fireEvent(new SelectMainSectionEvent(buttonId));
        }
    }

    public SelectMainSectionEvent(SearchToolStripButtonEnum buttonId) {
        this.buttonId = buttonId;
    }

    public SearchToolStripButtonEnum getButtonId() {
        return buttonId;
    }

    @Override
    protected void dispatch(SelectMainSectionEventHandler handler) {
        handler.onSelectMainSection(this);
    }

    public static Type<SelectMainSectionEventHandler> getType() {
        return TYPE;
    }
}
