package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableListGrid;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.GroupStartOpen;

import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;
import es.gobcan.istac.search.web.client.utils.ResourceListFieldUtils;

public class RecommendedKeywordListGrid extends BaseNavigableListGrid<ExternalItemDto> {

    public RecommendedKeywordListGrid() {
        super();
        ListGridUtils.setCheckBoxSelectionType(this);

        setShowGroupSummary(true);
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(RecommendedKeywordDS.CATEGORY);
        setGroupNodeStyle("customGroupNode");
        setCanSort(false);

        setAutoFitMaxRecords(LISTGRID_MAX_RESULTS);
        setAutoFitData(Autofit.VERTICAL);
        setFields(ResourceListFieldUtils.getRecommendedKeywordFields());
    }

    @Override
    protected List<PlaceRequest> buildLocation(ExternalItemDto relatedResourceDto) {
        // Not needed for ExternalItemDto. See BaseNavigableListGrid:43
        return null;
    }
}
