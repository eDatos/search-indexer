package es.gobcan.istac.search.core.recommendedlink.repositoryimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;

/**
 * Repository implementation for RecommendedKeyword
 */
@Repository("recommendedKeywordRepository")
public class RecommendedKeywordRepositoryImpl extends RecommendedKeywordRepositoryBase {

    public RecommendedKeywordRepositoryImpl() {
    }

    @Override
    public void deleteAll() {
        List<RecommendedKeyword> recommendedKeywords = findAll();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            delete(recommendedKeyword);
        }
    }
}
