package es.gobcan.istac.search.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;

import es.gobcan.istac.search.security.shared.SharedSecurityUtils;

public class SearchSecurityUtils extends SecurityUtils {

    // Recommended keywords

    public static void canRetrieveRecommendedKeyword(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canRetrieveRecommendedKeyword(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateRecommendedKeyword(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canCreateRecommendedKeyword(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateRecommendedKeyword(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canUpdateRecommendedKeyword(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteRecommendedKeyword(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canDeleteRecommendedKeyword(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindRecommendedKeywords(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canFindRecommendedKeywords(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // Recommended links

    public static void canRetrieveRecommendedLink(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canRetrieveRecommendedLink(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateRecommendedLink(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canCreateRecommendedLink(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateRecommendedLink(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canUpdateRecommendedLink(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteRecommendedLink(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canDeleteRecommendedLink(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindRecommendedLinks(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canFindRecommendedLinks(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canExportRecommendedLinks(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canExportRecommendedLinks(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canImportRecommendedLinks(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canImportRecommendedLinks(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // Reindex

    public static void canReindexWeb(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canReindexWeb(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canReindexGpe(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canReindexGpe(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canReindexRecommendedLinks(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canReindexRecommendedLinks(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
