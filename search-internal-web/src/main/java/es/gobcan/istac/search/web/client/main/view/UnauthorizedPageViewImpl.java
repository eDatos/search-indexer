package es.gobcan.istac.search.web.client.main.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.main.handlers.UnauthorizedPageUiHandlers;
import es.gobcan.istac.search.web.client.main.presenter.UnauthorizedPagePresenter;

public class UnauthorizedPageViewImpl extends ViewWithUiHandlers<UnauthorizedPageUiHandlers> implements UnauthorizedPagePresenter.UnauthorizedPageView {

    private VLayout panel;

    public UnauthorizedPageViewImpl() {
        super();
        panel = new VLayout();

        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER);
        hLayout.setMargin(20);

        HTMLFlow htmlFlow = new HTMLFlow();
        htmlFlow.setWidth(430);
        htmlFlow.setMargin(40);
        htmlFlow.setStyleName("exampleTextBlock");
        StringBuffer contents = new StringBuffer();
        contents.append("<hr>").append(SearchWeb.getMessages().applicationAccessDenied(SearchWeb.getCurrentUser().getUserId())).append("<hr>");
        htmlFlow.setContents(contents.toString());
        htmlFlow.draw();

        hLayout.addMember(htmlFlow);

        panel.addMember(hLayout);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
