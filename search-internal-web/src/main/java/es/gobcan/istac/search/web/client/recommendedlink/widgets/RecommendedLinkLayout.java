package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import java.util.List;

import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;
import es.gobcan.istac.search.web.client.utils.CommonUtils;
import es.gobcan.istac.search.web.client.utils.RecordUtils;

public class RecommendedLinkLayout extends VLayout {

    private VLayout                     panel;

    private ToolStrip                   recommendedLinkToolStrip;
    private MainFormLayout              recommendedLinkMainFormLayout;

    private GroupDynamicForm            viewRecommendedLinkForm;
    private GroupDynamicForm            editRecommendedLinkForm;

    private List<RecommendedKeywordDto> recommendedKeywordList;

    public RecommendedLinkLayout() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        createRecommendedLinkToolStrip();
        createRecommendedLinkMainFormLayout();

        panel.addMember(recommendedLinkToolStrip);
        panel.addMember(recommendedLinkMainFormLayout);

        addMember(panel);
    }

    private void createRecommendedLinkMainFormLayout() {
        recommendedLinkMainFormLayout = new MainFormLayout();
        createViewRecommendedLinkForm();
        createEditRecommendedLinkForm();

        recommendedLinkMainFormLayout.addViewCanvas(viewRecommendedLinkForm);
        recommendedLinkMainFormLayout.addEditionCanvas(editRecommendedLinkForm);
    }

    private void createEditRecommendedLinkForm() {
        editRecommendedLinkForm = new GroupDynamicForm(getConstants().recommendedLink());
        RequiredSelectItem keyword = new RequiredSelectItem(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID, getConstants().recommendedKeywordKeyword());
        RequiredTextItem url = new RequiredTextItem(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        RequiredTextItem title = new RequiredTextItem(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        CustomTextItem description = new CustomTextItem(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());
        editRecommendedLinkForm.setFields(keyword, url, title, description);
    }

    private void createViewRecommendedLinkForm() {
        viewRecommendedLinkForm = new GroupDynamicForm(getConstants().recommendedLink());
        ViewTextItem recommendedKeywordName = new ViewTextItem(RecommendedLinkDS.RECOMMENDED_KEYWORD_NAME, getConstants().recommendedKeywordKeyword());
        ViewTextItem url = new ViewTextItem(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        ViewTextItem title = new ViewTextItem(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        ViewTextItem description = new ViewTextItem(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());
        viewRecommendedLinkForm.setFields(recommendedKeywordName, url, title, description);
    }

    private void createRecommendedLinkToolStrip() {
        recommendedLinkToolStrip = new ToolStrip();
        recommendedLinkToolStrip.setWidth100();
    }

    public boolean validateForm() {
        return editRecommendedLinkForm.validate();
    }

    public ToolStrip getRecommendedLinkToolStrip() {
        return recommendedLinkToolStrip;
    }

    public MainFormLayout getRecommendedLinkMainFormLayout() {
        return recommendedLinkMainFormLayout;
    }

    public RecommendedLinkDto getRecommendedLink() {
        RecommendedLinkRecord recommendedLinkRecord = getValuesAsRecord(editRecommendedLinkForm);
        return RecordUtils.getRecommendedLinkDto(recommendedLinkRecord, getRecommendedKeywordList());
    }

    // DynamicForm getValuesAsRecord don´t take into account field modifications
    private RecommendedLinkRecord getValuesAsRecord(GroupDynamicForm editRecommendedLinkForm) {
        return new RecommendedLinkRecord(editRecommendedLinkForm);
    }

    public void setRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordList) {
        this.recommendedKeywordList = recommendedKeywordList;
        editRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID).setValueMap(CommonUtils.getRecommendedKeywordsHashMap(recommendedKeywordList));
    }

    public List<RecommendedKeywordDto> getRecommendedKeywordList() {
        return recommendedKeywordList;
    }

    public RecommendedLinkDto getRecommendedLinkDto(RecommendedLinkRecord record) {
        return RecordUtils.getRecommendedLinkDto(record, getRecommendedKeywordList());
    }

    public void setRecommendedLink(RecommendedLinkDto recommendedLinkDto) {
        if (recommendedLinkDto != null) {
            RecommendedLinkRecord record = RecordUtils.getRecommendedLinkRecord(recommendedLinkDto);
            editRecommendedLinkForm.editRecord(record);
            viewRecommendedLinkForm.editRecord(record);
        } else {
            editRecommendedLinkForm.editNewRecord();
            viewRecommendedLinkForm.editNewRecord();
        }
    }

}
