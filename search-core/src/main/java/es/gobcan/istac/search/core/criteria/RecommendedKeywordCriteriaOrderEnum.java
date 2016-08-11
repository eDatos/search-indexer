package es.gobcan.istac.search.core.criteria;

public enum RecommendedKeywordCriteriaOrderEnum {

    CREATED_DATE, KEYWORD;

    public String value() {
        return name();
    }

    public static RecommendedKeywordCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }
}
