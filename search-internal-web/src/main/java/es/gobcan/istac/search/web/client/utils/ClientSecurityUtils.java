package es.gobcan.istac.search.web.client.utils;

import org.siemac.metamac.sso.client.MetamacPrincipal;

import es.gobcan.istac.search.security.shared.SharedSecurityUtils;
import es.gobcan.istac.search.web.client.SearchWeb;

public class ClientSecurityUtils {

    public static boolean canReindexWeb() {
        return SharedSecurityUtils.canReindexWeb(getMetamacPrincipal());
    }

    public static boolean canReindexGpe() {
        return SharedSecurityUtils.canReindexGpe(getMetamacPrincipal());
    }

    public static boolean canReindexRecommendedLinks() {
        return SharedSecurityUtils.canReindexRecommendedLinks(getMetamacPrincipal());
    }

    private static MetamacPrincipal getMetamacPrincipal() {
        return SearchWeb.getCurrentUser();
    }
}
