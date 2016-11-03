package es.gobcan.istac.search.web.client.recommendedkeyword.view.handlers;

import java.util.List;

import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

public interface RecommendedKeywordListUiHandlers extends UiHandlers {

    void saveRecommendedKeyword(RecommendedKeywordDto recommendedKeyword);
    void updateRecommendedKeywordList(List<RecommendedKeywordDto> recommendedKeywordListDto);
    void deleteRecommendedKeywords(List<Long> recommendedKeywordListIds);

    void retrieveRecommendedKeywordList(RecommendedKeywordWebCriteria criteria);

    void retrieveSrmItems(String formItemName, SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);
}
