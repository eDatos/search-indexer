package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import org.siemac.metamac.web.common.client.widgets.CustomListGrid;

import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.GroupStartOpen;

import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.utils.ResourceListFieldUtils;

public class RecommendedKeywordListGrid extends CustomListGrid {

    public RecommendedKeywordListGrid() {
        super();

        setShowGroupSummary(true);
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(RecommendedKeywordDS.CATEGORY);
        setGroupTitleField(RecommendedKeywordDS.CATEGORY);
        setGroupNodeStyle("customGroupNode");
        setCanSort(false);

        setAutoFitMaxRecords(LISTGRID_MAX_RESULTS);
        setAutoFitData(Autofit.VERTICAL);
        setFields(ResourceListFieldUtils.getRecommendedKeywordFields());
    }
}
