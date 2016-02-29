package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionResult;

@Component
public class GetCronGpeExpressionActionHandler extends SecurityActionHandler<GetCronGpeExpressionAction, GetCronGpeExpressionResult> {

    @Autowired
    private SearchConfigurationService configurationService = null;

    public GetCronGpeExpressionActionHandler() {
        super(GetCronGpeExpressionAction.class);
    }

    @Override
    public GetCronGpeExpressionResult executeSecurityAction(GetCronGpeExpressionAction action) throws ActionException {
        // try {
        // FIXME - BUSCAISTAC-33, pending configurationService.retrieveCronGpeExpression
        return new GetCronGpeExpressionResult("not implemented cron gpe expression");
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }
    }
}
