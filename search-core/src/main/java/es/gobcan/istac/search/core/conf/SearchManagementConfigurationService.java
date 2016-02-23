package es.gobcan.istac.searchmanagement.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface SearchManagementConfigurationService extends ConfigurationService {

    String retrieveHelpUrl() throws MetamacException;
}
