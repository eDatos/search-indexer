package es.gobcan.istac.search.core.criteria;

public enum RecommendedKeywordCriteriaPropertyEnum {

    KEYWORD, CATEGORY;

    public String value() {
        return name();
    }
    public static RecommendedKeywordCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
