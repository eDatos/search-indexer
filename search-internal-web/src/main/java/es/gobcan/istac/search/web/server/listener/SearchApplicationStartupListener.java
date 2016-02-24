package es.gobcan.istac.search.web.server.listener;

import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

import es.gobcan.istac.search.core.constants.SearchConfigurationConstants;

public class SearchApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
        checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_URL);
        checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_DRIVER_NAME);
        checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_USERNAME);
        checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_PASSWORD);
    }

    @Override
    public String projectName() {
        return "search-internal";
    }

    @Override
    public void checkWebApplicationsProperties() {

    }

    @Override
    public void checkApiProperties() {

    }

    @Override
    public void checkOtherModuleProperties() {
    }
}
