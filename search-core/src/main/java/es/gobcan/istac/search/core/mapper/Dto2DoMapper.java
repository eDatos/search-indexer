package es.gobcan.istac.search.core.mapper;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapper;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface Dto2DoMapper extends BaseDto2DoMapper {

    RecommendedLink recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkDto source) throws MetamacException;
    List<RecommendedLink> recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkGroupedKeywordsDto source) throws MetamacException;

    RecommendedKeyword recommendedKeywordDtoToDo(ServiceContext ctx, RecommendedKeywordDto source) throws MetamacException;

    List<RecommendedKeyword> recommendedKeywordListDtoToDo(ServiceContext ctx, List<RecommendedKeywordDto> recommendedKeywordDtos) throws MetamacException;
}
