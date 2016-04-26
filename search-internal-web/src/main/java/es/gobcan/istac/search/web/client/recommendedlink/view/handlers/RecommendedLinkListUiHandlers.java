package es.gobcan.istac.search.web.client.recommendedlink.view.handlers;

import java.util.List;

import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public interface RecommendedLinkListUiHandlers extends UiHandlers {

    void retrieveRecommendedLinkList();
    void retrieveRecommendedLinkList(RecommendedLinkWebCriteria criteria);
    void retrieveRecommendedKeywordList();

    void recommendedLinksImportationSucceed(String message);
    void recommendedLinksImportationFailed(String error);

    void saveRecommendedLink(RecommendedLinkDto recommendedLinkDto);
    void saveRecommendedKeyword(RecommendedKeywordDto recommendedKeyword);

    void exportRecommendedLinks();
    void exportRecommendedLinks(List<Long> recommendedLinkListIds);

    void deleteRecommendedLinks(List<Long> recommendedLinkListIds);
    void deleteRecommendedKeyword(Long recommendedKeywordId);

    void retrieveSrmItems(String formItemName, SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);

}
