package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import java.util.List;

import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.CommonUtils;
import es.gobcan.istac.search.web.client.widgets.ModalWindow;

public class DeleteRecommendedKeywordWindow extends ModalWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 350;
    private static final String FIELD_DELETE           = "delete-rec-keyword";

    private CustomDynamicForm   form;

    public DeleteRecommendedKeywordWindow(RecommendedLinkListUiHandlers recommendedLinkListUiHandlers) {
        super(SearchWeb.getConstants().actionDeleteKeyword());
        setAutoSize(true);

        createDeleteRecommendedKeywordForm();

        addItem(form);
        show();
    }

    private void createDeleteRecommendedKeywordForm() {
        RequiredSelectItem keyword = new RequiredSelectItem(RecommendedKeywordDS.ID, getConstants().recommendedKeywordKeyword());
        keyword.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem deleteItem = new CustomButtonItem(FIELD_DELETE, getConstants().actionDeleteKeyword());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(keyword, deleteItem);
    }

    public HasClickHandlers getDelete() {
        return form.getItem(FIELD_DELETE);
    }

    public boolean validateForm() {
        return form.validate(false);
    }

    public void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList) {
        form.getItem(RecommendedKeywordDS.ID).clearValue();
        form.getItem(RecommendedKeywordDS.ID).setValueMap(CommonUtils.getRecommendedKeywordsHashMap(recommendedKeywordList));
    }

    public Long getRecommendedKeywordId() {
        return Long.valueOf(form.getValueAsString(RecommendedKeywordDS.ID));
    }
}
