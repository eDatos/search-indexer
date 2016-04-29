package es.gobcan.istac.search.web.client.recommendedkeyword.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import es.gobcan.istac.search.web.client.recommendedlink.widgets.RecommendedKeywordListGrid;;

public class RecommendedKeywordPaginatedListGrid extends PaginatedCheckListGrid {

    public RecommendedKeywordPaginatedListGrid(PaginatedAction action) {
        super(LISTGRID_MAX_RESULTS, new RecommendedKeywordListGrid(), action);
    }
}
