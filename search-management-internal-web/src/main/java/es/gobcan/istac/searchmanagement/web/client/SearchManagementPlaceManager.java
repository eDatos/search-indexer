package es.gobcan.istac.searchmanagement.web.client;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import es.gobcan.istac.searchmanagement.web.client.gin.DefaultPlace;

public class SearchManagementPlaceManager extends PlaceManagerImpl {

    private final PlaceRequest defaultPlaceRequest;

    @Inject
    public SearchManagementPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
        super(eventBus, tokenFormatter);
        this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
    }

    @Override
    public void revealDefaultPlace() {
        revealPlace(defaultPlaceRequest);
    }

    @Override
    public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.unauthorizedAccessPage);
        placeRequest = placeRequest.with("redirect", unauthorizedHistoryToken);
        revealPlace(placeRequest);
    }

    @Override
    public void revealErrorPlace(String invalidHistoryToken) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.errorPage);
        revealPlace(placeRequest);
    }

}
