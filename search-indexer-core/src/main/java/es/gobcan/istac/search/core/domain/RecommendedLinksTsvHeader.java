package es.gobcan.istac.search.core.domain;

public class RecommendedLinksTsvHeader {

    private int columnsSize;
    private int keywordPosition;
    private int titlePosition;
    private int urlPosition;
    private int descriptionPosition;
    private int keywordCategoryCodePosition;

    public int getColumnsSize() {
        return columnsSize;
    }

    public void setColumnsSize(int columnsSize) {
        this.columnsSize = columnsSize;
    }

    public int getKeywordPosition() {
        return keywordPosition;
    }

    public void setKeywordPosition(int keywordPosition) {
        this.keywordPosition = keywordPosition;
    }

    public int getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(int titlePosition) {
        this.titlePosition = titlePosition;
    }

    public int getUrlPosition() {
        return urlPosition;
    }

    public void setUrlPosition(int urlPosition) {
        this.urlPosition = urlPosition;
    }

    public int getDescriptionPosition() {
        return descriptionPosition;
    }

    public void setDescriptionPosition(int descriptionPosition) {
        this.descriptionPosition = descriptionPosition;
    }

    public int getKeywordCategoryCodePosition() {
        return keywordCategoryCodePosition;
    }

    public void setKeywordCategoryCodePosition(int keywordCategoryCodePosition) {
        this.keywordCategoryCodePosition = keywordCategoryCodePosition;
    }

}
