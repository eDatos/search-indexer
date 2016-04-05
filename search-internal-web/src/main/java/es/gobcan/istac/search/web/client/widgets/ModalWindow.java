package es.gobcan.istac.search.web.client.widgets;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

// See org.siemac.metamac.web.common.client.widgets.Search
public class ModalWindow extends Window {

    public ModalWindow(String title) {
        super();

        setWidth(380);
        setHeight(130);
        setTitle(title);
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        setAutoCenter(true);
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClickEvent event) {
                hide();
            }
        });
    }
}
