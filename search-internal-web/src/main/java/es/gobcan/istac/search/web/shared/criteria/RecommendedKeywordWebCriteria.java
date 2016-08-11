package es.gobcan.istac.search.web.shared.criteria;

import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import es.gobcan.istac.search.web.client.utils.SearchWebConstants;

public class RecommendedKeywordWebCriteria extends PaginationWebCriteria {

    private static final long serialVersionUID = 1L;
    private String            keyword;

    public RecommendedKeywordWebCriteria() {
        setFirstResult(0);
        setMaxResults(SearchWebConstants.LISTGRID_MAX_RESULTS);
    }

    public RecommendedKeywordWebCriteria(int maxResults) {
        setMaxResults(maxResults);
    }

    public RecommendedKeywordWebCriteria(String criteria, int firstResult, int maxResults) {
        super(criteria, firstResult, maxResults);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
