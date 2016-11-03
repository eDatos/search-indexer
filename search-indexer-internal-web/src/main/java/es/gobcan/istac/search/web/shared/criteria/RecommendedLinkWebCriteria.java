package es.gobcan.istac.search.web.shared.criteria;

import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import es.gobcan.istac.search.web.client.utils.SearchWebConstants;

public class RecommendedLinkWebCriteria extends PaginationWebCriteria {

    private static final long serialVersionUID = 1L;
    private String            keyword;

    public RecommendedLinkWebCriteria() {
        setFirstResult(0);
        setMaxResults(SearchWebConstants.LISTGRID_MAX_RESULTS);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

}
