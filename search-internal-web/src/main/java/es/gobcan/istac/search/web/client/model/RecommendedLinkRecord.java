package es.gobcan.istac.search.web.client.model;

import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;
import es.gobcan.istac.search.web.client.utils.CommonUtils;

public class RecommendedLinkRecord extends ListGridRecord {

    public RecommendedLinkRecord(Record record) {
        super(record.getJsObj());
    }
    public RecommendedLinkRecord() {
        super();
    }
    public RecommendedLinkRecord(GroupDynamicForm editRecommendedLinkForm) {
        super();
        setRecommendedLinkDto((RecommendedLinkDto) editRecommendedLinkForm.getValue(RecommendedLinkDS.RECOMMENDED_LINK_DTO));
        setUrl(editRecommendedLinkForm.getValueAsString(RecommendedLinkDS.URL));
        setTitle(editRecommendedLinkForm.getValueAsString(RecommendedLinkDS.TITLE));
        setDescription(editRecommendedLinkForm.getValueAsString(RecommendedLinkDS.DESCRIPTION));
        setRecommendedKeywordId(editRecommendedLinkForm.getValueAsString(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID));
    }

    public void setRecommendedLinkDto(RecommendedLinkDto value) {
        setAttribute(RecommendedLinkDS.RECOMMENDED_LINK_DTO, value);
    }
    public void setUrl(String value) {
        setAttribute(RecommendedLinkDS.URL, value);
    }
    public void setTitle(String value) {
        setAttribute(RecommendedLinkDS.TITLE, value);
    }
    public void setDescription(String value) {
        setAttribute(RecommendedLinkDS.DESCRIPTION, value);
    }
    public void setRecommendedKeywordId(String value) {
        setAttribute(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID, value);
    }
    public void setRecommendedKeywordId(Long value) {
        setRecommendedKeywordId(value.toString());
    }
    public void setRecommendedKeywordName(RecommendedKeywordDto recommendedKeyword) {
        setAttribute(RecommendedLinkDS.RECOMMENDED_KEYWORD_NAME, CommonUtils.getRecommendedKeywordLinkedName(recommendedKeyword));
    }

    public void setRecommendedKeywordKeyword(String value) {
        setAttribute(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD, value);
    }

    public void setRecommendedKeywordCategory(String value) {
        setAttribute(RecommendedLinkDS.RECOMMENDED_KEYWORD_CATEGORY, value);
    }

    public RecommendedLinkDto getRecommendedLinkdDto() {
        RecommendedLinkDto recommendedLinkDto = (RecommendedLinkDto) getAttributeAsObject(RecommendedLinkDS.RECOMMENDED_LINK_DTO);
        if (recommendedLinkDto != null) {
            return recommendedLinkDto;
        } else {
            return new RecommendedLinkDto();
        }
    }
    public String getUrl() {
        return getAttributeAsString(RecommendedLinkDS.URL);
    }
    public String getTitle() {
        return getAttributeAsString(RecommendedLinkDS.TITLE);
    }
    public String getDescription() {
        return getAttributeAsString(RecommendedLinkDS.DESCRIPTION);
    }
    public Long getRecommendedKeywordId() {
        String id = getAttributeAsString(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID);
        if (id != null) {
            return Long.valueOf(id);
        } else {
            return null;
        }
    }

}
