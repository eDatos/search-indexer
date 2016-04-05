package es.gobcan.istac.search.core.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;

import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface MetamacCriteria2SculptorCriteriaMapper {

    public MetamacCriteria2SculptorCriteria<RecommendedLink> getRecommendedLinkCriteriaMapper();
}
