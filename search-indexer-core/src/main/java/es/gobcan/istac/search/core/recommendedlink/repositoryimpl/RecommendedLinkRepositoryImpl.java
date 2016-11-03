package es.gobcan.istac.search.core.recommendedlink.repositoryimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

/**
 * Repository implementation for RecommendedLink
 */
@Repository("recommendedLinkRepository")
public class RecommendedLinkRepositoryImpl extends RecommendedLinkRepositoryBase {

    public RecommendedLinkRepositoryImpl() {
    }

    @Override
    public void deleteAll() {
        List<RecommendedLink> recommendedLinks = findAll();
        for (RecommendedLink recommendedLink : recommendedLinks) {
            delete(recommendedLink);
        }
    }
}
