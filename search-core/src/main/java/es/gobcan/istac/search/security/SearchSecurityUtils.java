package es.gobcan.istac.search.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;

import es.gobcan.istac.search.core.enume.domain.RoleEnum;
import es.gobcan.istac.search.security.shared.SharedSecurityUtils;

public class SearchSecurityUtils extends SecurityUtils {

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

    public static void isSearchRoleAllowed(ServiceContext ctx, RoleEnum role) throws MetamacException {
        if (!SharedSecurityUtils.isSearchRoleAllowed(getMetamacPrincipal(ctx), role)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

}
