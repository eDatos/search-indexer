package es.gobcan.istac.search.core.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface Do2DtoMapper extends BaseDo2DtoMapper {

    RecommendedLinkDto recommendedLinkDoToDto(RecommendedLink source) throws MetamacException;

    RecommendedKeywordDto recommendedKeywordDoToDto(RecommendedKeyword source) throws MetamacException;

    List<RecommendedKeywordDto> recommendedKeywordListDoToDto(List<RecommendedKeyword> recommendedKeywords) throws MetamacException;

    List<RecommendedLinkDto> recommendedLinkListDoToDto(List<RecommendedLink> recommendedLinks) throws MetamacException;

}