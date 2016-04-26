package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.RecordUtils;
import es.gobcan.istac.search.web.client.widgets.ModalWindow;
import es.gobcan.istac.search.web.client.widgets.form.fields.external.SearchSrmCategoryLinkItem;

public class NewRecommendedKeywordWindow extends ModalWindow {

    private static final int              FORM_ITEM_CUSTOM_WIDTH = 350;
    private static final String           FIELD_SAVE             = "save-rec-keyword";

    private CustomDynamicForm             form;

    private SearchSrmCategoryLinkItem     subjectItem;

    private RecommendedLinkListUiHandlers uiHandlers;

    public NewRecommendedKeywordWindow() {
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

        subjectItem = new SearchSrmCategoryLinkItem(RecommendedKeywordDS.CATEGORY, getConstants().recommendedKeywordCategory()) {

            @Override
            protected void retrieveItems(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults) {
                itemRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CATEGORY);
                getUiHandlers().retrieveSrmItems(RecommendedKeywordDS.CATEGORY, itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults) {
                // Do nothing
            }
        };

        subjectItem.setRequired(true);

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(keyword, subjectItem, saveItem);
    }

    protected RecommendedLinkListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public RecommendedKeywordDto getRecommendedKeyword() {
        return RecordUtils.getRecommendedKeywordDto(form, new RecommendedKeywordRecord(form.getValuesAsRecord()));
    }

    public boolean validateForm() {
        return form.validate(false);
    }

    public void setSrmItems(String formItemName, ExternalItemsResult result) {
        ((SearchSrmCategoryLinkItem) form.getField(formItemName)).setItems(result);
    }

    public void setUiHandlers(RecommendedLinkListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
