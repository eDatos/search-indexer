package es.gobcan.istac.searchmanagement.web.client;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.core.client.GWT;

import es.gobcan.istac.searchmanagement.core.constants.SearchManagementConstants;
import es.gobcan.istac.searchmanagement.web.client.gin.SearchManagementWebGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SearchManagementWeb extends MetamacSecurityEntryPoint {

    private static final boolean                   SECURITY_ENABLED = true;

    private static MetamacPrincipal                principal;
    private static SearchManagementWebMessages     messages;
    private static SearchManagementWebCoreMessages coreMessages;
    private static SearchManagementWebConstants    constants;

    private final SearchManagementWebGinjector     ginjector        = GWT.create(SearchManagementWebGinjector.class);

    @Override
    public void onModuleLoad() {
        setUncaughtExceptionHandler();

        prepareApplication(SECURITY_ENABLED);
    }

    @Override
    protected void setPrincipal(MetamacPrincipal principal) {
        SearchManagementWeb.principal = principal;
    }

    public static MetamacPrincipal getCurrentUser() {
        return SearchManagementWeb.principal;
    }

    @Override
    protected MetamacPrincipal getPrincipal() {
        return SearchManagementWeb.principal;
    }

    @Override
    protected String getApplicationTitle() {
        return getConstants().appTitle();
    }

    @Override
    protected String getBundleName() {
        return "messages-search_management-web";
    }

    public static SearchManagementWebCoreMessages getCoreMessages() {
        if (coreMessages == null) {
            coreMessages = GWT.create(SearchManagementWebCoreMessages.class);
        }
        return coreMessages;
    }

    public static SearchManagementWebMessages getMessages() {
        if (messages == null) {
            messages = GWT.create(SearchManagementWebMessages.class);
        }
        return messages;
    }

    public static SearchManagementWebConstants getConstants() {
        if (constants == null) {
            constants = GWT.create(SearchManagementWebConstants.class);
        }
        return constants;
    }

    @Override
    protected MetamacWebGinjector getWebGinjector() {
        return ginjector;
    }

    @Override
    protected String getSecurityApplicationId() {
        return SearchManagementConstants.SECURITY_APPLICATION_ID;
    }
}
