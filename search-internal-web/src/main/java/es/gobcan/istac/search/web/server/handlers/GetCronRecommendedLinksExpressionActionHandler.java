package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.web.shared.GetCronRecommendedLinksExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronRecommendedLinksExpressionResult;

@Component
public class GetCronRecommendedLinksExpressionActionHandler extends SecurityActionHandler<GetCronRecommendedLinksExpressionAction, GetCronRecommendedLinksExpressionResult> {

    @Autowired
    private SearchConfigurationService configurationService = null;

    public GetCronRecommendedLinksExpressionActionHandler() {
        super(GetCronRecommendedLinksExpressionAction.class);
    }

    @Override
    public GetCronRecommendedLinksExpressionResult executeSecurityAction(GetCronRecommendedLinksExpressionAction action) throws ActionException {
        // try {
        // FIXME - BUSCAISTAC-33, pending configurationService.retrieveCronWebExpression
        return new GetCronRecommendedLinksExpressionResult("not implemented cron recommended links expression");
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }
    }
}
