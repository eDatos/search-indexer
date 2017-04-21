package es.gobcan.istac.search.web.client.reindex.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;

public interface ReindexUiHandlers extends UiHandlers {

    void reindexGpe();
    void reindexWeb();
    void reindexRecommendedLinks();
    void reindexStatisticalResources();
    void showMessageIfStatusChangedToFinished(IndexationStatusDto indexationStatus, String oldStatus, String message);

}
