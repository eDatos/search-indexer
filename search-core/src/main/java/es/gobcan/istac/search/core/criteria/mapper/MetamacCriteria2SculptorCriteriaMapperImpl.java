package es.gobcan.istac.search.core.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.criteria.RecommendedKeywordCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedKeywordCriteriaPropertyEnum;
import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaPropertyEnum;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<RecommendedLink>    recommendedLinkCriteriaMapper    = null;
    private MetamacCriteria2SculptorCriteria<RecommendedKeyword> recommendedKeywordCriteriaMapper = null;

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        recommendedLinkCriteriaMapper = new MetamacCriteria2SculptorCriteria<RecommendedLink>(RecommendedLink.class, RecommendedLinkCriteriaOrderEnum.class, RecommendedLinkCriteriaPropertyEnum.class,
                new RecommendedLinkCriteriaCallback());

        recommendedKeywordCriteriaMapper = new MetamacCriteria2SculptorCriteria<RecommendedKeyword>(RecommendedKeyword.class, RecommendedKeywordCriteriaOrderEnum.class,
                RecommendedKeywordCriteriaPropertyEnum.class, new RecommendedKeywordCriteriaCallback());
    }

    @Override
    public MetamacCriteria2SculptorCriteria<RecommendedLink> getRecommendedLinkCriteriaMapper() {
        return recommendedLinkCriteriaMapper;
    }

    @Override
    public MetamacCriteria2SculptorCriteria<RecommendedKeyword> getRecommendedKeywordCriteriaMapper() {
        return recommendedKeywordCriteriaMapper;
    }

}
