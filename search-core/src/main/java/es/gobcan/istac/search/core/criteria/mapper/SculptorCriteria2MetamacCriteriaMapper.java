package es.gobcan.istac.search.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface SculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<RecommendedLinkDto> pageResultToMetamacCriteriaResultRecommendedLinkDto(PagedResult<RecommendedLink> source, Integer pageSize) throws MetamacException;
    public MetamacCriteriaResult<RecommendedKeywordDto> pageResultToMetamacCriteriaResultRecommendedKeywordDto(PagedResult<RecommendedKeyword> source, Integer pageSize) throws MetamacException;
}
