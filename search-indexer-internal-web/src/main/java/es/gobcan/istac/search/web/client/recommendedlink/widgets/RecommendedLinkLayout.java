package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.model.ds.RelatedResourceBaseDS;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import es.gobcan.istac.search.core.dto.RecommendedKeywordBaseDto;
import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;
import es.gobcan.istac.search.web.client.recommendedlink.view.handlers.RecommendedLinkListUiHandlers;
import es.gobcan.istac.search.web.client.utils.ClientSecurityUtils;
import es.gobcan.istac.search.web.client.utils.CommonUtils;
import es.gobcan.istac.search.web.client.utils.RecordUtils;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

public class RecommendedLinkLayout extends VLayout {

    private VLayout                       panel;

    private ToolStrip                     recommendedLinkToolStrip;
    private MainFormLayout                recommendedLinkMainFormLayout;

    private GroupDynamicForm              viewRecommendedLinkForm;
    private GroupDynamicForm              creationRecommendedLinkForm;
    private GroupDynamicForm              editionRecommendedLinkForm;

    private VLayout                       creationEditionFormLayout;

    private RecommendedLinkDto            recommendedLinkDto;

    private RecommendedLinkListUiHandlers uiHandlers;

    public RecommendedLinkLayout() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.VISIBLE);

        createRecommendedLinkToolStrip();
        createRecommendedLinkMainFormLayout();

        panel.addMember(recommendedLinkToolStrip);
        panel.addMember(recommendedLinkMainFormLayout);

        addMember(panel);
    }

    private void createRecommendedLinkMainFormLayout() {
        recommendedLinkMainFormLayout = new MainFormLayout();
        recommendedLinkMainFormLayout.setCanEdit(ClientSecurityUtils.canUpdateRecommendedLink());
        createViewRecommendedLinkForm();
        createCreationRecommendedLinkForm();
        createEditionRecommendedLinkForm();

        creationEditionFormLayout = new VLayout();
        creationEditionFormLayout.addMember(creationRecommendedLinkForm);
        creationEditionFormLayout.addMember(editionRecommendedLinkForm);

        recommendedLinkMainFormLayout.addViewCanvas(viewRecommendedLinkForm);
        recommendedLinkMainFormLayout.addEditionCanvas(creationEditionFormLayout);

        recommendedLinkMainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (recommendedLinkDto == null) {
                    if (creationRecommendedLinkForm.validate(false)) {
                        uiHandlers.createRecommendedLinks(getNewRecommendedLinkGroupedKeywords());
                    }
                } else {
                    if (editionRecommendedLinkForm.validate(false)) {
                        uiHandlers.saveRecommendedLink(getEditedRecommendedLink());
                    }
                }
            }
        });

        recommendedLinkMainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // If the form is visible becase the user was creating a link, hide the complete mainFormLayout
                if (recommendedLinkDto == null) {
                    hide();
                }
            }
        });
    }

    private void createEditionRecommendedLinkForm() {
        editionRecommendedLinkForm = new GroupDynamicForm(getConstants().recommendedLink());

        final SearchExternalItemSimpleItem keywordItem = new SearchExternalItemSimpleItem(RecommendedLinkDS.RECOMMENDED_KEYWORD, getConstants().recommendedKeywordKeyword(),
                CommonWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveRecommendedKeywordListForField(RecommendedLinkDS.RECOMMENDED_KEYWORD, new RecommendedKeywordWebCriteria(webCriteria.getCriteria(), firstResult, maxResults));
            }
        };
        keywordItem.setColumnsToShow(new HashSet<String>(Arrays.asList(RelatedResourceBaseDS.TITLE)));
        keywordItem.setRequired(true);

        RequiredTextItem url = new RequiredTextItem(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        RequiredTextItem title = new RequiredTextItem(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        CustomTextItem description = new CustomTextItem(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());

        url.setValidators(CommonUtils.getLongTextLengthValidator());
        title.setValidators(CommonUtils.getShortTextLengthValidator());
        description.setValidators(CommonUtils.getLongTextLengthValidator());
        editionRecommendedLinkForm.setFields(keywordItem, url, title, description);
    }

    private void createCreationRecommendedLinkForm() {
        creationRecommendedLinkForm = new GroupDynamicForm(getConstants().recommendedLink());

        final SearchMultiExternalItemSimpleItem keywordsItem = new SearchMultiExternalItemSimpleItem(RecommendedLinkDS.RECOMMENDED_KEYWORDS, getConstants().recommendedLinkKeywords(),
                CommonWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveRecommendedKeywordListForField(RecommendedLinkDS.RECOMMENDED_KEYWORDS, new RecommendedKeywordWebCriteria(webCriteria.getCriteria(), firstResult, maxResults));
            }
        };
        keywordsItem.setColumnsToShow(new HashSet<String>(Arrays.asList(RelatedResourceBaseDS.TITLE)));
        keywordsItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return !keywordsItem.getExternalItemDtos().isEmpty();
            }
        });

        RequiredTextItem url = new RequiredTextItem(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        RequiredTextItem title = new RequiredTextItem(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        CustomTextItem description = new CustomTextItem(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());

        url.setValidators(CommonUtils.getLongTextLengthValidator());
        title.setValidators(CommonUtils.getShortTextLengthValidator());
        description.setValidators(CommonUtils.getLongTextLengthValidator());
        creationRecommendedLinkForm.setFields(keywordsItem, url, title, description);
    }

    private void createViewRecommendedLinkForm() {
        viewRecommendedLinkForm = new GroupDynamicForm(getConstants().recommendedLink());
        ViewTextItem recommendedKeywordKeyword = new ViewTextItem(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD, getConstants().recommendedKeywordKeyword());
        ExternalItemLinkItem recommendedKeywordCategory = new ExternalItemLinkItem(RecommendedLinkDS.RECOMMENDED_KEYWORD_CATEGORY, getConstants().recommendedKeywordCategory());
        ViewTextItem url = new ViewTextItem(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        ViewTextItem title = new ViewTextItem(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        ViewTextItem description = new ViewTextItem(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());
        viewRecommendedLinkForm.setFields(recommendedKeywordKeyword, url, title, description, recommendedKeywordCategory);
    }

    private void createRecommendedLinkToolStrip() {
        recommendedLinkToolStrip = new ToolStrip();
        recommendedLinkToolStrip.setWidth100();
    }

    public ToolStrip getRecommendedLinkToolStrip() {
        return recommendedLinkToolStrip;
    }

    public MainFormLayout getRecommendedLinkMainFormLayout() {
        return recommendedLinkMainFormLayout;
    }

    public RecommendedLinkGroupedKeywordsDto getNewRecommendedLinkGroupedKeywords() {
        RecommendedLinkGroupedKeywordsDto recommendedLinkGroupedKeywordsDto = new RecommendedLinkGroupedKeywordsDto();
        recommendedLinkGroupedKeywordsDto.getKeywords().addAll(getRecommendedKeywordsFromCreationForm());
        recommendedLinkGroupedKeywordsDto.setUrl(creationRecommendedLinkForm.getValueAsString(RecommendedLinkDS.URL));
        recommendedLinkGroupedKeywordsDto.setTitle(creationRecommendedLinkForm.getValueAsString(RecommendedLinkDS.TITLE));
        recommendedLinkGroupedKeywordsDto.setDescription(creationRecommendedLinkForm.getValueAsString(RecommendedLinkDS.DESCRIPTION));
        return recommendedLinkGroupedKeywordsDto;
    }

    public RecommendedLinkDto getEditedRecommendedLink() {
        ExternalItemDto keyword = ((SearchExternalItemSimpleItem) editionRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORD)).getExternalItemDto();
        recommendedLinkDto.setRecommendedKeyword(toRecommendedKeywordDto(keyword));
        recommendedLinkDto.setUrl(editionRecommendedLinkForm.getValueAsString(RecommendedLinkDS.URL));
        recommendedLinkDto.setTitle(editionRecommendedLinkForm.getValueAsString(RecommendedLinkDS.TITLE));
        recommendedLinkDto.setDescription(editionRecommendedLinkForm.getValueAsString(RecommendedLinkDS.DESCRIPTION));
        return recommendedLinkDto;
    }

    private List<RecommendedKeywordBaseDto> getRecommendedKeywordsFromCreationForm() {
        List<RecommendedKeywordBaseDto> keywords = new ArrayList<RecommendedKeywordBaseDto>();
        List<ExternalItemDto> externalItemDtos = ((ExternalItemListItem) creationRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORDS)).getExternalItemDtos();
        for (ExternalItemDto externalItemDto : externalItemDtos) {
            keywords.add(toRecommendedKeywordBaseDto(externalItemDto));
        }
        return keywords;
    }

    public void setRecommendedKeywordListForField(String fieldName, List<RecommendedKeywordDto> recommendedKeywordList, int firstResult, int totalResults) {
        if (RecommendedLinkDS.RECOMMENDED_KEYWORDS.equals(fieldName)) {
            ((SearchMultiExternalItemSimpleItem) creationRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORDS)).setResources(toExternalItemDtos(recommendedKeywordList), firstResult,
                    totalResults);
        } else if (RecommendedLinkDS.RECOMMENDED_KEYWORD.equals(fieldName)) {
            ((SearchExternalItemSimpleItem) editionRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORD)).setResources(toExternalItemDtos(recommendedKeywordList), firstResult,
                    totalResults);
        }
    }

    public void setRecommendedLink(RecommendedLinkDto recommendedLinkDto) {
        this.recommendedLinkDto = recommendedLinkDto;
        if (recommendedLinkDto != null) {
            RecommendedLinkRecord record = RecordUtils.getRecommendedLinkRecord(recommendedLinkDto);
            viewRecommendedLinkForm.editRecord(record);
            viewRecommendedLinkForm.setValue(RecommendedLinkDS.RECOMMENDED_KEYWORD_CATEGORY, recommendedLinkDto.getRecommendedKeyword().getCategory());
            editionRecommendedLinkForm.editRecord(record);
            ((SearchExternalItemSimpleItem) editionRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORD)).setExternalItem(toExternalItemDto(recommendedLinkDto.getRecommendedKeyword()));
        } else {
            creationRecommendedLinkForm.editNewRecord();
            ((SearchMultiExternalItemSimpleItem) creationRecommendedLinkForm.getItem(RecommendedLinkDS.RECOMMENDED_KEYWORDS)).setExternalItems(new ArrayList<ExternalItemDto>());
            viewRecommendedLinkForm.editNewRecord();
            editionRecommendedLinkForm.editNewRecord();
        }
    }

    public void newRecommendedLink() {
        getRecommendedLinkMainFormLayout().clearTitleLabelContents();
        creationRecommendedLinkForm.show();
        editionRecommendedLinkForm.hide();
        getRecommendedLinkMainFormLayout().setEditionMode();
        setRecommendedLink(null);
        show();
    }

    public void editRecommendedLink(RecommendedLinkDto recommendedLinkDto) {
        this.recommendedLinkDto = recommendedLinkDto;
        getRecommendedLinkMainFormLayout().clearTitleLabelContents();
        creationRecommendedLinkForm.hide();
        editionRecommendedLinkForm.show();
        getRecommendedLinkMainFormLayout().setViewMode();
        setRecommendedLink(recommendedLinkDto);
        show();
    }

    public void setUiHandlers(RecommendedLinkListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private List<ExternalItemDto> toExternalItemDtos(List<RecommendedKeywordDto> recommendedKeywordList) {
        List<ExternalItemDto> extendedExternalItemDtos = new ArrayList<ExternalItemDto>();
        for (RecommendedKeywordDto keyword : recommendedKeywordList) {
            extendedExternalItemDtos.add(toExternalItemDto(keyword));
        }
        return extendedExternalItemDtos;
    }

    private ExternalItemDto toExternalItemDto(RecommendedKeywordDto keyword) {
        ExternalItemDto extendedExternalItemDto = new ExternalItemDto();
        extendedExternalItemDto.setId(keyword.getId());
        extendedExternalItemDto.setCode(keyword.getId().toString());
        extendedExternalItemDto.setUrn(keyword.getId().toString());
        extendedExternalItemDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), keyword.getKeyword()));
        return extendedExternalItemDto;
    }

    private RecommendedKeywordBaseDto toRecommendedKeywordBaseDto(ExternalItemDto externalItemDto) {
        RecommendedKeywordBaseDto keyword = new RecommendedKeywordBaseDto();
        keyword.setId(externalItemDto.getId());
        keyword.setKeyword(InternationalStringUtils.getLocalisedString(externalItemDto.getTitle()));
        return keyword;
    }

    private RecommendedKeywordDto toRecommendedKeywordDto(ExternalItemDto externalItemDto) {
        RecommendedKeywordDto recommendedKeywordDto = new RecommendedKeywordDto();
        recommendedKeywordDto.setId(externalItemDto.getId());
        return recommendedKeywordDto;
    }
}
