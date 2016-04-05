package es.gobcan.istac.search.core.idxmanager.service.mapper;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapper;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public interface Dto2DoMapper extends BaseDto2DoMapper {

    RecommendedLink recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkDto source) throws MetamacException;

    RecommendedKeyword recommendedKeywordDtoToDo(ServiceContext ctx, RecommendedKeywordDto source) throws MetamacException;

}
