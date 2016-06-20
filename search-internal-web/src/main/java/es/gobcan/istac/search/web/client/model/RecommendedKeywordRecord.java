package es.gobcan.istac.search.web.client.model;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.NavigableListGridRecord;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;

public class RecommendedKeywordRecord extends NavigableListGridRecord {

    public void setKeyword(String value) {
        setAttribute(RecommendedKeywordDS.KEYWORD, value);
    }

    public String getKeyword() {
        return getAttributeAsString(RecommendedKeywordDS.KEYWORD);
    }

    public void setCategory(ExternalItemDto value) {
        if (value != null) {
            setExternalItem(RecommendedKeywordDS.CATEGORY, value);
        }
    }

    public RecommendedKeywordDto getRecommendedKeywordDto() {
        if (getAttributeAsObject(RecommendedKeywordDS.RECOMMENDED_KEYWORD_DTO) == null) {
            return new RecommendedKeywordDto();
        } else {
            return (RecommendedKeywordDto) getAttributeAsObject(RecommendedKeywordDS.RECOMMENDED_KEYWORD_DTO);
        }
    }

    public void setRecommendedKeywordDto(RecommendedKeywordDto value) {
        setAttribute(RecommendedKeywordDS.RECOMMENDED_KEYWORD_DTO, value);
    }

}
