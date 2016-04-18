package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;
import static es.gobcan.istac.search.web.client.SearchWeb.getMessages;

import java.util.LinkedHashMap;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.UploadResourceWithPreviewWindow;
import org.siemac.metamac.web.common.client.widgets.WarningLabel;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;

import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.shared.ImportableResourceTypeEnum;
import es.gobcan.istac.search.web.shared.utils.SearchSharedTokens;

public abstract class ImportRecommendedLinksWindow extends UploadResourceWithPreviewWindow {

    private static final int FORM_ITEM_WIDTH = 800;
    protected WarningLabel   warningLabel;

    public ImportRecommendedLinksWindow() {
        super(getConstants().actionImport());
        setAutoSize(true);
        setWidth(FORM_ITEM_WIDTH);

        buildWarningLabel();
    }

    private void buildWarningLabel() {
        warningLabel = new WarningLabel(getMessages().errorFileRequired());
        warningLabel.setWidth(getWidth());
        warningLabel.setMargin(5);
        warningLabel.hide();
        body.addMember(warningLabel, 0);
    }

    @Override
    protected UploadForm buildMainUploadForm() {
        return new UploadRecommendedLinksForm();
    }

    @Override
    protected CustomDynamicForm buildExtraForm() {

        CustomButtonItem uploadButton = new CustomButtonItem("button-import", MetamacWebCommon.getConstants().accept());

        uploadButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                submitIfValid();
            }

        });
        RadioGroupItem importMode = new RadioGroupItem(SearchSharedTokens.UPLOAD_PARAM_MODE);
        importMode.setTitle(getConstants().importMode());
        importMode.setValueMap(getImportModeValueMap());
        importMode.setRequired(true);

        CustomDynamicForm form = new CustomDynamicForm();
        form.setItems(importMode, uploadButton);
        return form;
    }

    private LinkedHashMap<String, String> getImportModeValueMap() {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(SearchSharedTokens.UPLOAD_PARAM_MODE_ADDITIVE, getConstants().importModeAdditive());
        valueMap.put(SearchSharedTokens.UPLOAD_PARAM_MODE_SUSTITUTIVE, getConstants().importModeSustitutive());
        return valueMap;
    }

    @Override
    protected void copyHiddenValuesToMainForm(UploadForm mainForm, DynamicForm extraForm) {
        mainForm.setValue(SearchSharedTokens.UPLOAD_PARAM_MODE, extraForm.getValueAsString(SearchSharedTokens.UPLOAD_PARAM_MODE));
    }

    @Override
    public String getRelativeURL(String url) {
        return SearchWeb.getRelativeURL(url);
    }

    @Override
    protected void onPreviewComplete(String response) {
    }

    @Override
    protected void onPreviewFailed(String errorMessage) {
        uploadFailed(errorMessage);
    }

    @Override
    protected void onSubmitComplete(String response) {
        uploadSuccess(response);
    }

    @Override
    protected void onSubmitFailed(String errorMessage) {
        uploadFailed(errorMessage);
    }

    protected abstract void uploadFailed(String error);
    protected abstract void uploadSuccess(String message);

    private class UploadRecommendedLinksForm extends UploadForm {

        private UploadItem uploadItem;

        public UploadRecommendedLinksForm() {
            super();

            uploadItem = new UploadItem("file-name");
            uploadItem.setTitle(getConstants().actionImport());
            uploadItem.setWidth(300);
            // uploadItem.setRequired(true); // Don't use, is bugged
            uploadItem.setTitleStyle("requiredFormLabel");

            uploadItem.addChangeHandler(new com.smartgwt.client.widgets.form.fields.events.ChangeHandler() {

                @Override
                public void onChange(com.smartgwt.client.widgets.form.fields.events.ChangeEvent event) {
                    warningLabel.hide();
                }
            });

            HiddenItem fileTypeItem = new HiddenItem(SearchSharedTokens.UPLOAD_PARAM_FILE_TYPE);
            fileTypeItem.setDefaultValue(ImportableResourceTypeEnum.RECOMMENDED_LINKS.name());

            HiddenItem importMode = new HiddenItem(SearchSharedTokens.UPLOAD_PARAM_MODE);

            setFields(uploadItem, fileTypeItem, importMode);
        }

        @Override
        public UploadItem getUploadItem() {
            return uploadItem;
        }
    }

    @Override
    protected void submitIfValid() {
        if (!StringUtils.isBlank(mainForm.getUploadItem().getDisplayValue())) {
            super.submitIfValid();
        } else {
            warningLabel.show();
        }
    }

}
