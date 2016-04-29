package es.gobcan.istac.search.web.client.utils;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;

public class ResourceListFieldUtils {

    public static CustomListGridField[] getRecommendedLinkFields() {
        CustomListGridField recommendedKeywordKeyword = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD, getConstants().recommendedKeywordKeyword());
        recommendedKeywordKeyword.setWidth("10%");

        CustomListGridField recommendedKeywordCategory = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_CATEGORY, getConstants().recommendedKeywordCategory());
        recommendedKeywordCategory.setWidth("15%");
        recommendedKeywordCategory.setHidden(true);

        CustomListGridField url = new CustomListGridField(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        url.setWidth("30%");
        CustomListGridField title = new CustomListGridField(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        title.setWidth("15%");
        CustomListGridField description = new CustomListGridField(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());
        description.setWidth("30%");

        CustomListGridField recommendedKeywordId = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID, "");
        recommendedKeywordId.setHidden(true);

        return new CustomListGridField[]{recommendedKeywordKeyword, recommendedKeywordCategory, url, title, description};
    }

    public static CustomListGridField[] getRecommendedKeywordFields() {
        CustomListGridField recommendedKeywordKeyword = new CustomListGridField(RecommendedKeywordDS.KEYWORD, getConstants().recommendedKeywordKeyword());
        recommendedKeywordKeyword.setWidth("30%");

        CustomListGridField recommendedKeywordCategory = new CustomListGridField(RecommendedKeywordDS.CATEGORY, getConstants().recommendedKeywordCategory());
        recommendedKeywordCategory.setWidth("70%");

        CustomListGridField recommendedKeywordId = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID, "");
        recommendedKeywordId.setHidden(true);

        return new CustomListGridField[]{recommendedKeywordKeyword, recommendedKeywordCategory};
    }

}
