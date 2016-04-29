package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;

import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

import es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers.RecommendedKeywordListUiHandlers;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

public class RecommendedKeywordListSearchSectionStack extends SearchSectionStack {

    private RecommendedKeywordListUiHandlers handlers;

    public RecommendedKeywordListSearchSectionStack() {
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
        getUiHandlers().retrieveRecommendedKeywordList(getRecommendedKeywordListWebCriteria());
    }

    public RecommendedKeywordWebCriteria getRecommendedKeywordListWebCriteria() {
        RecommendedKeywordWebCriteria recommendedKeywordWebCriteria = new RecommendedKeywordWebCriteria();
        recommendedKeywordWebCriteria.setCriteria(getSearchCriteria());
        return recommendedKeywordWebCriteria;
    }

    private RecommendedKeywordListUiHandlers getUiHandlers() {
        return handlers;
    }

    public void setUiHandlers(RecommendedKeywordListUiHandlers handlers) {
        this.handlers = handlers;
    }

}
