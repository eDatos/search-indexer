package es.gobcan.istac.search.security.shared;

import static es.gobcan.istac.search.core.enume.domain.RoleEnum.ADMINISTRADOR;
import static es.gobcan.istac.search.core.enume.domain.RoleEnum.ANY_ROLE_ALLOWED;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.utils.SecurityUtils;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.enume.domain.RoleEnum;

public class SharedSecurityUtils extends SecurityUtils {

    /**
     * Checks if logged user has one of the allowed roles
     *
     * @param roles
     * @return
     */
    public static boolean isSearchRoleAllowed(MetamacPrincipal metamacPrincipal, RoleEnum... roles) {
        // Administration has total control
        if (SharedSecurityUtils.isAdministrador(metamacPrincipal)) {
            return true;
        }
        // Checks user has any role of requested
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                RoleEnum role = roles[i];
                if (SharedSecurityUtils.isUserInSearchRol(metamacPrincipal, role)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks user has any role
     */
    protected static boolean isUserInSearchRol(MetamacPrincipal metamacPrincipal, RoleEnum role) {
        if (ANY_ROLE_ALLOWED.equals(role)) {
            return isAnySearchRole(metamacPrincipal);
        } else {
            return isRoleInAccesses(metamacPrincipal, role);
        }
    }

    /**
     * Checks if user has access to an operation. To have access, any access must exists to specified role and operation, or has any access with
     * role and operation with 'null' value
     */
    protected static boolean haveAccessToOperationInRol(MetamacPrincipal metamacPrincipal, RoleEnum role, String operation) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (SearchConstants.SECURITY_APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                if (metamacPrincipalAccess.getOperation() == null || metamacPrincipalAccess.getOperation().equals(operation)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean isAdministrador(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, ADMINISTRADOR);
    }

    /**
     * Checks if user has access with role
     */
    protected static boolean isRoleInAccesses(MetamacPrincipal metamacPrincipal, RoleEnum role) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (SearchConstants.SECURITY_APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isAnySearchRole(MetamacPrincipal metamacPrincipal) {
        return isAdministrador(metamacPrincipal);
    }

    public static boolean canReindexWeb(MetamacPrincipal metamacPrincipal) {
        return isAnySearchRole(metamacPrincipal);
    }

    public static boolean canReindexGpe(MetamacPrincipal metamacPrincipal) {
        return isAnySearchRole(metamacPrincipal);
    }

    public static boolean canReindexRecommendedLinks(MetamacPrincipal metamacPrincipal) {
        return isAnySearchRole(metamacPrincipal);
    }

}
