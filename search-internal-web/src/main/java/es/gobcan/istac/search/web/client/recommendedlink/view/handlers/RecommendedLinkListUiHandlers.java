package es.gobcan.istac.search.web.client.recommendedlink.view.handlers;

import java.util.List;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.view.handlers.SearchUiHandlers;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public interface RecommendedLinkListUiHandlers extends SearchUiHandlers {

    void retrieveRecommendedLinkList(RecommendedLinkWebCriteria criteria);
    void deleteRecommendedLinks(List<Long> recommendedLinkListIds);
    void saveRecommendedLink(RecommendedLinkDto recommendedLinkDto);
    void saveRecommendedKeyword(RecommendedKeywordDto recommendedKeyword);

    void recommendedLinksImportationSucceed(String message);
    void recommendedLinksImportationFailed(String error);

    void exportRecommendedLinks();
    void exportRecommendedLinks(List<Long> recommendedLinkListIds);
}
