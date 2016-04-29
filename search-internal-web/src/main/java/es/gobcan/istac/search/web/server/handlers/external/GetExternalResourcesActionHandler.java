package es.gobcan.istac.search.web.server.handlers.external;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Categories;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.service.RestApiLocator;
import es.gobcan.istac.search.core.service.SrmRestInternalFacade;
import es.gobcan.istac.search.web.server.rest.utils.RestQueryUtils;
import es.gobcan.istac.search.web.server.utils.ExternalItemUtils;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesAction;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesResult;

@Component
public class GetExternalResourcesActionHandler extends SecurityActionHandler<GetExternalResourcesAction, GetExternalResourcesResult> {

    @Autowired
    private SrmRestInternalFacade      srmRestInternalFacade;

    @Autowired
    RestApiLocator                     restApiLocator;

    @Autowired
    private RestExceptionUtils         restExceptionUtils;

    @Autowired
    private SearchConfigurationService searchConfigurationService;

    public GetExternalResourcesActionHandler() {
        super(GetExternalResourcesAction.class);
    }

    @Override
    public GetExternalResourcesResult executeSecurityAction(GetExternalResourcesAction action) throws ActionException {

        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();
        SrmItemRestCriteria srmItemRestCriteria = (SrmItemRestCriteria) action.getExternalResourceWebCriteria();

        ExternalItemsResult result = null;
        try {
            switch (action.getExternalResourceWebCriteria().getExternalArtifactType()) {
                case CATEGORY:
                    result = findCategories(serviceContext, srmItemRestCriteria, action.getFirstResult(), action.getMaxResults());
                    break;
                default:
                    throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "An unknown exception has ocurred. Please contact system administrator.");
            }
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        return new GetExternalResourcesResult(result);
    }

    private ExternalItemsResult findCategories(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException, MetamacException {
        itemWebCriteria.setItemSchemeUrn(searchConfigurationService.retrieveDefaultCategorySchemeUrn());
        String query = RestQueryUtils.buildCategoryQuery(itemWebCriteria);
        try {
            Categories categories = srmRestInternalFacade.findCategories(serviceContext, query, firstResult, maxResults);
            return ExternalItemUtils.getCategoriesAsExternalItemsResult(categories);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    private MetamacWebException manageSrmInternalRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_SRM_INTERNAL, restApiLocator.getSrmRestInternalFacadeV10());
    }
}
