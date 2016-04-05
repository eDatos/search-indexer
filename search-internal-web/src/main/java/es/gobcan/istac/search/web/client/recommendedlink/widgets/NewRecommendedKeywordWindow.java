package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.RecordUtils;
import es.gobcan.istac.search.web.client.widgets.ModalWindow;

public class NewRecommendedKeywordWindow extends ModalWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 350;
    private static final String FIELD_SAVE             = "save-rec-keyword";

    private CustomDynamicForm   form;

    public NewRecommendedKeywordWindow(RecommendedLinkListUiHandlers recommendedLinkListUiHandlers) {
        super(SearchWeb.getConstants().actionNewKeyword());
        setAutoSize(true);

        createEditRecommendedKeywordForm();

        addItem(form);
        show();
    }

    private void createEditRecommendedKeywordForm() {

        RequiredTextItem keyword = new RequiredTextItem(RecommendedKeywordDS.KEYWORD, getConstants().recommendedKeywordKeyword());
        keyword.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().actionNewKeyword());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(keyword, saveItem);
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public RecommendedKeywordDto getRecommendedKeyword() {
        return RecordUtils.getRecommendedKeywordDto(new RecommendedKeywordRecord(form.getValuesAsRecord()));
    }

    public boolean validateForm() {
        return form.validate(false);
    }
}
