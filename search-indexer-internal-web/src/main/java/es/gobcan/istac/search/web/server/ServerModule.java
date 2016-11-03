package es.gobcan.istac.search.web.server;

import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.LoadConfigurationPropertiesActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

import es.gobcan.istac.search.web.server.handlers.GetCronGpeExpressionActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetCronRecommendedLinksExpressionActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetCronWebExpressionActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetHelpUrlActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetIndexationStatusGpeActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetIndexationStatusRecommendedLinksActionHandler;
import es.gobcan.istac.search.web.server.handlers.GetIndexationStatusWebActionHandler;
import es.gobcan.istac.search.web.server.handlers.ReindexGpeActionHandler;
import es.gobcan.istac.search.web.server.handlers.ReindexRecommendedLinksActionHandler;
import es.gobcan.istac.search.web.server.handlers.ReindexWebActionHandler;
import es.gobcan.istac.search.web.server.handlers.ValidateTicketActionHandler;
import es.gobcan.istac.search.web.server.handlers.external.GetExternalResourcesActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.CreateRecommendedLinksActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.DeleteRecommendedKeywordListActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.DeleteRecommendedLinkListActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.ExportRecommendedLinksActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.ExportRecommendedLinksListActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.GetRecommendedKeywordListActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.GetRecommendedLinkListActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.SaveRecommendedKeywordActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.SaveRecommendedLinkActionHandler;
import es.gobcan.istac.search.web.server.handlers.recommendedlink.UpdateRecommendedKeywordListActionHandler;
import es.gobcan.istac.search.web.shared.GetCronGpeExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronRecommendedLinksExpressionAction;
import es.gobcan.istac.search.web.shared.GetCronWebExpressionAction;
import es.gobcan.istac.search.web.shared.GetHelpUrlAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusGpeAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.GetIndexationStatusWebAction;
import es.gobcan.istac.search.web.shared.ReindexGpeAction;
import es.gobcan.istac.search.web.shared.ReindexRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.ReindexWebAction;
import es.gobcan.istac.search.web.shared.external.GetExternalResourcesAction;
import es.gobcan.istac.search.web.shared.recommendedlink.CreateRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.DeleteRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksAction;
import es.gobcan.istac.search.web.shared.recommendedlink.ExportRecommendedLinksListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedKeywordListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.GetRecommendedLinkListAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedKeywordAction;
import es.gobcan.istac.search.web.shared.recommendedlink.SaveRecommendedLinkAction;
import es.gobcan.istac.search.web.shared.recommendedlink.UpdateRecommendedKeywordListAction;

@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    @Override
    protected void configureHandlers() {

        // Other
        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);
        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);
        bindHandler(GetHelpUrlAction.class, GetHelpUrlActionHandler.class);

        bindHandler(GetCronGpeExpressionAction.class, GetCronGpeExpressionActionHandler.class);
        bindHandler(GetCronWebExpressionAction.class, GetCronWebExpressionActionHandler.class);
        bindHandler(GetCronRecommendedLinksExpressionAction.class, GetCronRecommendedLinksExpressionActionHandler.class);

        bindHandler(ReindexWebAction.class, ReindexWebActionHandler.class);
        bindHandler(ReindexGpeAction.class, ReindexGpeActionHandler.class);
        bindHandler(ReindexRecommendedLinksAction.class, ReindexRecommendedLinksActionHandler.class);

        bindHandler(GetIndexationStatusWebAction.class, GetIndexationStatusWebActionHandler.class);
        bindHandler(GetIndexationStatusGpeAction.class, GetIndexationStatusGpeActionHandler.class);
        bindHandler(GetIndexationStatusRecommendedLinksAction.class, GetIndexationStatusRecommendedLinksActionHandler.class);

        bindHandler(GetRecommendedLinkListAction.class, GetRecommendedLinkListActionHandler.class);
        bindHandler(SaveRecommendedLinkAction.class, SaveRecommendedLinkActionHandler.class);
        bindHandler(CreateRecommendedLinksAction.class, CreateRecommendedLinksActionHandler.class);
        bindHandler(DeleteRecommendedLinkListAction.class, DeleteRecommendedLinkListActionHandler.class);
        bindHandler(ExportRecommendedLinksAction.class, ExportRecommendedLinksActionHandler.class);
        bindHandler(ExportRecommendedLinksListAction.class, ExportRecommendedLinksListActionHandler.class);

        bindHandler(GetRecommendedKeywordListAction.class, GetRecommendedKeywordListActionHandler.class);
        bindHandler(SaveRecommendedKeywordAction.class, SaveRecommendedKeywordActionHandler.class);
        bindHandler(DeleteRecommendedKeywordListAction.class, DeleteRecommendedKeywordListActionHandler.class);
        bindHandler(UpdateRecommendedKeywordListAction.class, UpdateRecommendedKeywordListActionHandler.class);

        bindHandler(GetExternalResourcesAction.class, GetExternalResourcesActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}
