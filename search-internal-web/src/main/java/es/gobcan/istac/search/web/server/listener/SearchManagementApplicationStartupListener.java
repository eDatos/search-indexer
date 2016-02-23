package es.gobcan.istac.searchmanagement.web.server.listener;

import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

import es.gobcan.istac.searchmanagement.core.constants.SearchManagementConfigurationConstants;

public class SearchManagementApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
        checkRequiredProperty(SearchManagementConfigurationConstants.DB_SEARCH_MANAGEMENT_URL);
        checkRequiredProperty(SearchManagementConfigurationConstants.DB_SEARCH_MANAGEMENT_DRIVER_NAME);
        checkRequiredProperty(SearchManagementConfigurationConstants.DB_SEARCH_MANAGEMENT_USERNAME);
        checkRequiredProperty(SearchManagementConfigurationConstants.DB_SEARCH_MANAGEMENT_PASSWORD);
    }

    @Override
    public String projectName() {
        return "search-management-internal";
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
