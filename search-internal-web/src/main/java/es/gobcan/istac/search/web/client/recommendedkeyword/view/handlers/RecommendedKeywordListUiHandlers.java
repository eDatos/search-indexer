package es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers;

import java.util.List;

import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.view.handlers.SearchUiHandlers;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

public interface RecommendedKeywordListUiHandlers extends SearchUiHandlers {

    void retrieveRecommendedKeywordList(RecommendedKeywordWebCriteria criteria);
    void saveRecommendedKeyword(RecommendedKeywordDto recommendedKeyword);
    void updateRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordListDto);
    void deleteRecommendedKeywords(List<Long> recommendedKeywordListIds);

    void retrieveSrmItems(String formItemName, SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);
}
