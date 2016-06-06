package es.gobcan.istac.search.web.client.recommendedlink.view.handlers;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public interface RecommendedLinkListUiHandlers extends UiHandlers {

    void retrieveRecommendedLinkList(RecommendedLinkWebCriteria criteria);
    void deleteRecommendedLinks(List<Long> recommendedLinkListIds);
    void saveRecommendedLink(RecommendedLinkDto recommendedLinkDto);
    void createRecommendedLinks(RecommendedLinkGroupedKeywordsDto recommendedLinkGroupedKeywordsDto);

    void recommendedLinksImportationSucceed(String message);
    void recommendedLinksImportationFailed(String error);

    void exportRecommendedLinks();
    void exportRecommendedLinks(List<Long> recommendedLinkListIds);

    void retrieveRecommendedKeywordListForField(String fieldName, RecommendedKeywordWebCriteria criteria);
}
