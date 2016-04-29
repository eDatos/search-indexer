package es.gobcan.istac.search.core.service;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;

public interface SrmRestInternalFacade {

    Categories findCategories(ServiceContext serviceContext, String query, int firstResult, int maxResults) throws MetamacException;

    Category retrieveCategoryByCode(String code) throws MetamacException;

}
