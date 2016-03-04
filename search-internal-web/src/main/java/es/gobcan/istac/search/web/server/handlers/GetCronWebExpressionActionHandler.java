package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.web.shared.GetCronWebExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronWebExpressionResult;

@Component
public class GetCronWebExpressionActionHandler extends SecurityActionHandler<GetCronWebExpressionAction, GetCronWebExpressionResult> {

    @Autowired
    private SearchConfigurationService configurationService;

    public GetCronWebExpressionActionHandler() {
        super(GetCronWebExpressionAction.class);
    }

    @Override
    public GetCronWebExpressionResult executeSecurityAction(GetCronWebExpressionAction action) throws ActionException {
        try {
            return new GetCronWebExpressionResult(configurationService.retrieveIndexationWebCron());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
