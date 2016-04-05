package es.gobcan.istac.search.core.idxmanager.service.mapper;

import java.util.List;

import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface Do2DtoMapper extends BaseDo2DtoMapper {

    RecommendedLinkDto recommendedLinkDoToDto(RecommendedLink source);

    RecommendedKeywordDto recommendedKeywordDoToDto(RecommendedKeyword source);

    List<RecommendedKeywordDto> recommendedKeywordListDoToDto(List<RecommendedKeyword> recommendedKeywords);

    List<RecommendedLinkDto> recommendedLinkListDoToDto(List<RecommendedLink> recommendedLinks);

}