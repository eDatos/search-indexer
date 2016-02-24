package es.gobcan.istac.search.web.client.main.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

import es.gobcan.istac.search.web.client.main.presenter.ToolStripPresenterWidget;

public class ToolStripViewImpl implements ToolStripPresenterWidget.ToolStripView {

    private ToolStrip       toolStrip;

    private ToolStripButton exampleButton;

    @Inject
    public ToolStripViewImpl() {
        super();
        toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        toolStrip.setAlign(Alignment.LEFT);

        exampleButton = new ToolStripButton("Ejemplo");

        // Add buttons to toolStrip
        toolStrip.addButton(exampleButton);
        toolStrip.addMember(new ToolStripSeparator());
    }

    @Override
    public void addToSlot(Object slot, Widget content) {
    }

    @Override
    public Widget asWidget() {
        return toolStrip;
    }

    @Override
    public void removeFromSlot(Object slot, Widget content) {

    }

    @Override
    public void setInSlot(Object slot, Widget content) {
    }

    @Override
    public HasClickHandlers getExampleButton() {
        return exampleButton;
    }
}
