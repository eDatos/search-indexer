package es.gobcan.istac.search.web.server.listener;

import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

import es.gobcan.istac.search.core.constants.SearchConfigurationConstants;

public class SearchApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
        // checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_URL);
        // checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_DRIVER_NAME);
        // checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_USERNAME);
        // checkRequiredProperty(SearchConfigurationConstants.DB_SEARCH_PASSWORD);

        checkRequiredPropertiesSolr();
        checkRequiredPropertiesAlfresco();
        checkRequiredPropertiesIndexation();
    }

    private void checkRequiredPropertiesSolr() {
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_SOLR_JAXI_URL);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_SOLR_ENDPOINT);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_SOLR_CORE_OR_COLLECTION);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_SOLR_CLOUD_SERVER_ENABLED);
    }

    private void checkRequiredPropertiesAlfresco() {
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_URL);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_USERNAME);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_PASSWORD);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_PATH);
    }

    private void checkRequiredPropertiesIndexation() {
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_INDEXATION_WEB_CRON);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_INDEXATION_WEB_URL);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_INDEXATION_GPE_CRON);
        checkRequiredProperty(SearchConfigurationConstants.SEARCH_INDEXATION_RECOMMENDED_LINKS);
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
