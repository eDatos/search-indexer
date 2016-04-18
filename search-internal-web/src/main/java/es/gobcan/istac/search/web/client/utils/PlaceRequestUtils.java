package es.gobcan.istac.search.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.web.common.client.utils.CommonPlaceRequestUtils;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import es.gobcan.istac.search.web.client.navigation.NameTokens;

public class PlaceRequestUtils extends CommonPlaceRequestUtils {

    public static List<PlaceRequest> buildAbsoluteReindexPlaceRequest() {
        return buildAbsolutePlaceRequest(NameTokens.REINDEX_PAGE);
    }

    public static List<PlaceRequest> buildAbsoluteRecommendedLinkListPlaceRequest() {
        return buildAbsolutePlaceRequest(NameTokens.RECOMMENDED_LINK_LIST_PAGE);
    }

    private static List<PlaceRequest> buildAbsolutePlaceRequest(String page) {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(page);
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }
}
