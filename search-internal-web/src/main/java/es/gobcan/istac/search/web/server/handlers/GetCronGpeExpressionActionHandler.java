package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionResult;

@Component
public class GetCronGpeExpressionActionHandler extends SecurityActionHandler<GetCronGpeExpressionAction, GetCronGpeExpressionResult> {

    @Autowired
    private SearchConfigurationService configurationService;

    public GetCronGpeExpressionActionHandler() {
        super(GetCronGpeExpressionAction.class);
    }

    @Override
    public GetCronGpeExpressionResult executeSecurityAction(GetCronGpeExpressionAction action) throws ActionException {
        try {
            return new GetCronGpeExpressionResult(configurationService.retrieveIndexationGpeCron());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
