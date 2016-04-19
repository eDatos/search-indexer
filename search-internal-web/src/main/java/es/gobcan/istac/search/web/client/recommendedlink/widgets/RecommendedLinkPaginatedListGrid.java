package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;;

public class RecommendedLinkPaginatedListGrid extends PaginatedCheckListGrid {

    public RecommendedLinkPaginatedListGrid(PaginatedAction action) {
        super(LISTGRID_MAX_RESULTS, new RecommendedLinkListGrid(), action);
    }
}
