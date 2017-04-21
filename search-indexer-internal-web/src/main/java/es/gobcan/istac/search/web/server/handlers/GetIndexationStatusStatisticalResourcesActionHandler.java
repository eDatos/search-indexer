package es.gobcan.istac.search.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;
import es.gobcan.istac.search.core.idxmanager.service.stream.KafkaConsumerLauncher;
import es.gobcan.istac.search.web.shared.GetIndexationStatusStatisticalResourcesAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusStatisticalResourcesResult;

@Component
public class GetIndexationStatusStatisticalResourcesActionHandler extends SecurityActionHandler<GetIndexationStatusStatisticalResourcesAction, GetIndexationStatusStatisticalResourcesResult> {

    @Autowired
    private KafkaConsumerLauncher kafkaConsumerLauncher;

    public GetIndexationStatusStatisticalResourcesActionHandler() {
        super(GetIndexationStatusStatisticalResourcesAction.class);
    }

    @Override
    public GetIndexationStatusStatisticalResourcesResult executeSecurityAction(GetIndexationStatusStatisticalResourcesAction action) throws ActionException {
        try {
            IndexationStatusDto indexationStatus = new IndexationStatusDto(kafkaConsumerLauncher.getKafkaRecoverStatus().getSiglas(), kafkaConsumerLauncher.getKafkaRecoverStatusLastDate());
            return new GetIndexationStatusStatisticalResourcesResult(indexationStatus);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
