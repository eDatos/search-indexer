package es.gobcan.istac.search.core.criteria;

public enum RecommendedLinkCriteriaPropertyEnum {

    TITLE, URL, DESCRIPTION, KEYWORD, /*SUBJECT,*/;

    public String value() {
        return name();
    }
    public static RecommendedLinkCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
