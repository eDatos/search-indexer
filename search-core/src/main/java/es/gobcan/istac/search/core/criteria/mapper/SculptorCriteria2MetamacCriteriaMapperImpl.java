package es.gobcan.istac.search.core.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.mapper.Do2DtoMapper;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

@Component
public class SculptorCriteria2MetamacCriteriaMapperImpl implements SculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private Do2DtoMapper do2DtoMapper;

    @Override
    public MetamacCriteriaResult<RecommendedLinkDto> pageResultToMetamacCriteriaResultRecommendedLinkDto(PagedResult<RecommendedLink> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<RecommendedLinkDto> target = new MetamacCriteriaResult<RecommendedLinkDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<RecommendedLinkDto>());
            for (RecommendedLink item : source.getValues()) {
                target.getResults().add(do2DtoMapper.recommendedLinkDoToDto(item));
            }
        }
        return target;
    }

    @Override
    public MetamacCriteriaResult<RecommendedKeywordDto> pageResultToMetamacCriteriaResultRecommendedKeywordDto(PagedResult<RecommendedKeyword> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<RecommendedKeywordDto> target = new MetamacCriteriaResult<RecommendedKeywordDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<RecommendedKeywordDto>());
            for (RecommendedKeyword item : source.getValues()) {
                target.getResults().add(do2DtoMapper.recommendedKeywordDoToDto(item));
            }
        }
        return target;
    }
}
