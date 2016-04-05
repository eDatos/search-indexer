package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;;

public class RecommendedLinkPaginatedListGrid extends PaginatedCheckListGrid {

    public RecommendedLinkPaginatedListGrid() {
        // TODO paginated action
        super(LISTGRID_MAX_RESULTS, new RecommendedLinkListGrid(), null);
    }
}
