package es.gobcan.istac.search.core.criteria;

public enum RecommendedLinkCriteriaOrderEnum {

    CREATED_DATE;

    public String value() {
        return name();
    }

    public static RecommendedLinkCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }
}
