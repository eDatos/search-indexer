package es.gobcan.istac.search.web.client;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.core.client.GWT;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.web.client.gin.SearchWebGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SearchWeb extends MetamacSecurityEntryPoint {

    private static final boolean         SECURITY_ENABLED = true;

    private static MetamacPrincipal      principal;
    private static SearchWebMessages     messages;
    private static SearchWebCoreMessages coreMessages;
    private static SearchWebConstants    constants;

    private final SearchWebGinjector     ginjector        = GWT.create(SearchWebGinjector.class);

    @Override
    public void onModuleLoad() {
        setUncaughtExceptionHandler();

        prepareApplication(SECURITY_ENABLED);
    }

    @Override
    protected void setPrincipal(MetamacPrincipal principal) {
        SearchWeb.principal = principal;
    }

    public static MetamacPrincipal getCurrentUser() {
        return SearchWeb.principal;
    }

    @Override
    protected MetamacPrincipal getPrincipal() {
        return SearchWeb.principal;
    }

    @Override
    protected String getApplicationTitle() {
        return getConstants().appTitle();
    }

    @Override
    protected String getBundleName() {
        return "messages-search-web";
    }

    public static SearchWebCoreMessages getCoreMessages() {
        if (coreMessages == null) {
            coreMessages = GWT.create(SearchWebCoreMessages.class);
        }
        return coreMessages;
    }

    public static SearchWebMessages getMessages() {
        if (messages == null) {
            messages = GWT.create(SearchWebMessages.class);
        }
        return messages;
    }

    public static SearchWebConstants getConstants() {
        if (constants == null) {
            constants = GWT.create(SearchWebConstants.class);
        }
        return constants;
    }

    @Override
    protected MetamacWebGinjector getWebGinjector() {
        return ginjector;
    }

    @Override
    protected String getSecurityApplicationId() {
        return SearchConstants.SECURITY_APPLICATION_ID;
    }
}
