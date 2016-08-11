package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;

import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public class RecommendedLinkListSearchSectionStack extends SearchSectionStack {

    private RecommendedLinkListUiHandlers handlers;

    public RecommendedLinkListSearchSectionStack() {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                retrieveResources();
            }

        });

        addSearchItemKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (StringUtils.equalsIgnoreCase(event.getKeyName(), CommonWebConstants.ENTER_KEY)) {
                    retrieveResources();
                }
            }
        });
    }

    protected void retrieveResources() {
        getUiHandlers().retrieveRecommendedLinkList(getRecommendedLinkListWebCriteria());
    }

    public RecommendedLinkWebCriteria getRecommendedLinkListWebCriteria() {
        RecommendedLinkWebCriteria recommendedLinkWebCriteria = new RecommendedLinkWebCriteria();
        recommendedLinkWebCriteria.setCriteria(getSearchCriteria());
        return recommendedLinkWebCriteria;
    }

    private RecommendedLinkListUiHandlers getUiHandlers() {
        return handlers;
    }

    public void setUiHandlers(RecommendedLinkListUiHandlers handlers) {
        this.handlers = handlers;
    }

}
