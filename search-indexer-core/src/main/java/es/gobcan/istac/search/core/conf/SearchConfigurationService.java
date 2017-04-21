package es.gobcan.istac.search.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface SearchConfigurationService extends ConfigurationService {

    String retrieveHelpUrl() throws MetamacException;

    String retrieveSolrEndpoint() throws MetamacException;
    String retrieveSolrCoreOrCollection() throws MetamacException;
    boolean retrieveSolrCloudServerEnabled() throws MetamacException;

    String retrieveAlfrescoUrl() throws MetamacException;
    String retrieveAlfrescoUsername() throws MetamacException;
    String retrieveAlfrescoPassword() throws MetamacException;
    String retrieveAlfrescoPath() throws MetamacException;

    String retrieveIndexationWebCron() throws MetamacException;
    String retrieveIndexationWebUrl() throws MetamacException;
    String retrieveIndexationGpeCron() throws MetamacException;

    String retrieveKafkaDatasetGroup() throws MetamacException;
    String retrieveKafkaDatasetRecoverGroup() throws MetamacException;
    String retrieveKafkaPublicationGroup() throws MetamacException;
    String retrieveKafkaPublicationRecoverGroup() throws MetamacException;

    String retrieveDefaultCategorySchemeUrn() throws MetamacException;
}
