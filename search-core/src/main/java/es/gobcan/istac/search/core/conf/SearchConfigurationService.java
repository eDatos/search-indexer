package es.gobcan.istac.search.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface SearchConfigurationService extends ConfigurationService {

    String retrieveHelpUrl() throws MetamacException;
}
