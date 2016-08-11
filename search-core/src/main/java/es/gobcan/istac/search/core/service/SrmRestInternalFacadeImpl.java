package es.gobcan.istac.search.core.service;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.WILDCARD_ALL;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.exception.ServiceExceptionUtils;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private RestApiLocator             restApiLocator;

    @Autowired
    private SearchConfigurationService searchConfigurationService;

    @Override
    public Categories findCategories(ServiceContext serviceContext, String query, int firstResult, int maxResults) throws MetamacException {
        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;

        try {
            return restApiLocator.getSrmRestInternalFacadeV10().findCategories(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset);
        } catch (Exception e) {
            ServiceExceptionUtils.manageSrmInternalRestException(e, restApiLocator.getSrmRestInternalFacadeV10());
        }
        return null;
    }

    @Override
    public Category retrieveCategoryByCode(String code) throws MetamacException {
        String[] splittedUrn = UrnUtils.splitUrnItemScheme(searchConfigurationService.retrieveDefaultCategorySchemeUrn());
        String agencyID = splittedUrn[0];
        String resourceID = splittedUrn[1];
        String version = splittedUrn[2];
        try {
            return restApiLocator.getSrmRestInternalFacadeV10().retrieveCategory(agencyID, resourceID, version, code);
        } catch (Exception e) {
            throw ServiceExceptionUtils.manageSrmInternalRestException(e, restApiLocator.getSrmRestInternalFacadeV10());
        }
    }

}
