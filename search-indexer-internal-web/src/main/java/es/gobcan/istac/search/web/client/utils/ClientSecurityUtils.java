package es.gobcan.istac.search.web.client.utils;

import org.siemac.metamac.sso.client.MetamacPrincipal;

import es.gobcan.istac.search.security.shared.SharedSecurityUtils;
import es.gobcan.istac.search.web.client.SearchWeb;

public class ClientSecurityUtils {

    // Reindex

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

    // Recommended links

    public static boolean canRetrieveRecommendedLink() {
        return SharedSecurityUtils.canRetrieveRecommendedLink(getMetamacPrincipal());
    }

    public static boolean canCreateRecommendedLink() {
        return SharedSecurityUtils.canCreateRecommendedLink(getMetamacPrincipal());
    }

    public static boolean canUpdateRecommendedLink() {
        return SharedSecurityUtils.canUpdateRecommendedLink(getMetamacPrincipal());
    }

    public static boolean canDeleteRecommendedLink() {
        return SharedSecurityUtils.canDeleteRecommendedLink(getMetamacPrincipal());
    }

    public static boolean canFindRecommendedLinks() {
        return SharedSecurityUtils.canFindRecommendedLinks(getMetamacPrincipal());
    }

    public static boolean canExportRecommendedLinks() {
        return SharedSecurityUtils.canExportRecommendedLinks(getMetamacPrincipal());
    }

    public static boolean canImportRecommendedLinks() {
        return SharedSecurityUtils.canImportRecommendedLinks(getMetamacPrincipal());
    }

    // Recommended keywords

    public static boolean canRetrieveRecommendedKeyword() {
        return SharedSecurityUtils.canRetrieveRecommendedKeyword(getMetamacPrincipal());
    }

    public static boolean canCreateRecommendedKeyword() {
        return SharedSecurityUtils.canCreateRecommendedKeyword(getMetamacPrincipal());
    }

    public static boolean canUpdateRecommendedKeyword() {
        return SharedSecurityUtils.canUpdateRecommendedKeyword(getMetamacPrincipal());
    }

    public static boolean canDeleteRecommendedKeyword() {
        return SharedSecurityUtils.canDeleteRecommendedKeyword(getMetamacPrincipal());
    }

    public static boolean canFindRecommendedKeywords() {
        return SharedSecurityUtils.canFindRecommendedKeywords(getMetamacPrincipal());
    }
}
