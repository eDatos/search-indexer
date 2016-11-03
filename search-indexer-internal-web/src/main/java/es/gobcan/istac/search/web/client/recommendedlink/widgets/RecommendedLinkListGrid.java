package es.gobcan.istac.search.web.client.recommendedlink.widgets;

import static es.gobcan.istac.search.web.client.utils.SearchWebConstants.LISTGRID_MAX_RESULTS;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableListGrid;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.GroupStartOpen;

import es.gobcan.istac.search.web.client.model.ds.RecommendedLinkDS;
import es.gobcan.istac.search.web.client.utils.ResourceListFieldUtils;

public class RecommendedLinkListGrid extends BaseNavigableListGrid<ExternalItemDto> {

    public RecommendedLinkListGrid() {
        super();
        ListGridUtils.setCheckBoxSelectionType(this);
        
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

    @Override
    protected List<PlaceRequest> buildLocation(ExternalItemDto relatedResourceDto) {
        // Not needed for ExternalItemDto. See BaseNavigableListGrid:43
        return null;
    }
}
