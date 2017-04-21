package es.gobcan.istac.search.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.constants.SearchConfigurationConstants;

public class SearchConfigurationServiceImpl extends ConfigurationServiceImpl implements SearchConfigurationService {

    final String SEARCH_DATASET_GROUP             = "SEARCH_DATASET_GROUP";
    final String SEARCH_DATASET_RECOVER_GROUP     = "SEARCH_DATASET_RECOVER_GROUP";
    final String SEARCH_PUBLICATION_GROUP         = "SEARCH_PUBLICATION_GROUP";
    final String SEARCH_PUBLICATION_RECOVER_GROUP = "SEARCH_PUBLICATION_RECOVER_GROUP";

    @Override
    public String retrieveHelpUrl() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_HELP_URL);
    }

    @Override
    public String retrieveSolrEndpoint() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_SOLR_ENDPOINT);
    }

    @Override
    public String retrieveSolrCoreOrCollection() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_SOLR_CORE_OR_COLLECTION);
    }

    @Override
    public boolean retrieveSolrCloudServerEnabled() throws MetamacException {
        return retrievePropertyBoolean(SearchConfigurationConstants.SEARCH_SOLR_CLOUD_SERVER_ENABLED);
    }

    @Override
    public String retrieveAlfrescoUrl() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_URL);
    }

    @Override
    public String retrieveAlfrescoUsername() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_USERNAME);
    }

    @Override
    public String retrieveAlfrescoPassword() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_PASSWORD);
    }

    @Override
    public String retrieveAlfrescoPath() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_ALFRESCO_PATH);
    }

    @Override
    public String retrieveIndexationWebCron() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_INDEXATION_WEB_CRON);
    }

    @Override
    public String retrieveIndexationWebUrl() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_INDEXATION_WEB_URL);
    }

    @Override
    public String retrieveIndexationGpeCron() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_INDEXATION_GPE_CRON);
    }

    @Override
    public String retrieveDefaultCategorySchemeUrn() throws MetamacException {
        return retrieveProperty(SearchConfigurationConstants.SEARCH_DEFAULT_CATEGORY_SCHEME_URN);
    }

    @Override
    public String retrieveKafkaDatasetGroup() throws MetamacException {
        return SEARCH_DATASET_GROUP; // Hard coded for evit manual edition
    }

    @Override
    public String retrieveKafkaDatasetRecoverGroup() throws MetamacException {
        return SEARCH_DATASET_RECOVER_GROUP; // Hard coded for evit manual edition
    }

    @Override
    public String retrieveKafkaPublicationGroup() throws MetamacException {
        return SEARCH_PUBLICATION_GROUP; // Hard coded for evit manual edition
    }

    @Override
    public String retrieveKafkaPublicationRecoverGroup() throws MetamacException {
        return SEARCH_PUBLICATION_RECOVER_GROUP; // Hard coded for evit manual edition
    }

}
