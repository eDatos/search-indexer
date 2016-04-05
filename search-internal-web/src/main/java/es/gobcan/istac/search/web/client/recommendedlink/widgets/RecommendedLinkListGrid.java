package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import org.siemac.metamac.web.common.client.widgets.CustomListGrid;

import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.GroupStartOpen;

import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;
import es.gobcan.istac.search.web.client.utils.ResourceListFieldUtils;

public class RecommendedLinkListGrid extends CustomListGrid {

    public RecommendedLinkListGrid() {
        super();

        setShowGroupSummary(true);
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD);
        setGroupTitleField(RecommendedLinkDS.RECOMMENDED_KEYWORD_KEYWORD);
        setGroupNodeStyle("customGroupNode");
        setCanSort(false);

        setAutoFitMaxRecords(LISTGRID_MAX_RESULTS);
        setAutoFitData(Autofit.VERTICAL);
        setFields(ResourceListFieldUtils.getRecommendedLinkFields());
    }
}
