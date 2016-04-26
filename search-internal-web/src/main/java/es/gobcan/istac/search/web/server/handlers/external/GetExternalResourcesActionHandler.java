package es.gobcan.istac.search.web.server.handlers.external;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.web.server.rest.SrmRestInternalFacade;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesAction;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesResult;

@Component
public class GetExternalResourcesActionHandler extends SecurityActionHandler<GetExternalResourcesAction, GetExternalResourcesResult> {

    @Autowired
    private SrmRestInternalFacade      srmRestInternalFacade;

    @Autowired
    private SearchConfigurationService searchConfigurationService;

    public GetExternalResourcesActionHandler() {
        super(GetExternalResourcesAction.class);
    }

    @Override
    public GetExternalResourcesResult executeSecurityAction(GetExternalResourcesAction action) throws ActionException {

        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

        SrmItemRestCriteria srmItemRestCriteria = (SrmItemRestCriteria) action.getExternalResourceWebCriteria();

        try {
            srmItemRestCriteria.setItemSchemeUrn(searchConfigurationService.retrieveDefaultCategorySchemeUrn());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        ExternalItemsResult result = null;
        switch (action.getExternalResourceWebCriteria().getExternalArtifactType()) {

            case CATEGORY:
                result = srmRestInternalFacade.findCategories(serviceContext, srmItemRestCriteria, action.getFirstResult(), action.getMaxResults());
                break;
            default:
                throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "An unknown exception has ocurred. Please contact system administrator.");
        }
        return new GetExternalResourcesResult(result);
    }
}
