package es.gobcan.istac.search.web.client.utils;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;

public class ResourceListFieldUtils {

    public static CustomListGridField[] getRecommendedLinkFields() {
        CustomListGridField recommendedKeywordKeyword = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD, getConstants().recommendedKeywordKeyword());
        recommendedKeywordKeyword.setWidth("15%");
        CustomListGridField url = new CustomListGridField(RecommendedLinkDS.URL, getConstants().recommendedLinkUrl());
        url.setWidth("35%");
        CustomListGridField title = new CustomListGridField(RecommendedLinkDS.TITLE, getConstants().recommendedLinkTitle());
        title.setWidth("15%");
        CustomListGridField description = new CustomListGridField(RecommendedLinkDS.DESCRIPTION, getConstants().recommendedLinkDescription());
        description.setWidth("35%");

        CustomListGridField recommendedKeywordId = new CustomListGridField(RecommendedLinkDS.RECOMMENDED_KEYWORD_ID, "");
        recommendedKeywordId.setHidden(true);

        return new CustomListGridField[]{recommendedKeywordKeyword, url, title, description};
    }

}
