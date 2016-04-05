package es.gobcan.istac.search.core.idxmanager.service.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.JodaAuditable;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.AuditableDto;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapperImpl;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

@Component
public class Do2DtoMapperImpl extends BaseDo2DtoMapperImpl implements Do2DtoMapper {

    @Override
    public RecommendedLinkDto recommendedLinkDoToDto(RecommendedLink source) {
        RecommendedLinkDto target = new RecommendedLinkDto();

        // Common info
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        auditableDo2Dto(source, target);
        target.setOptimisticLockingVersion(source.getVersion());

        target.setUrl(source.getUrl());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setRecommendedKeyword(recommendedKeywordDoToDto(source.getRecommendedKeyword()));

        return target;
    }

    @Override
    public RecommendedKeywordDto recommendedKeywordDoToDto(RecommendedKeyword source) {
        RecommendedKeywordDto target = new RecommendedKeywordDto();

        // Common info
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        auditableDo2Dto(source, target);
        target.setOptimisticLockingVersion(source.getVersion());

        target.setKeyword(source.getKeyword());

        return target;
    }

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

    @Override
    public List<RecommendedKeywordDto> recommendedKeywordListDoToDto(List<RecommendedKeyword> recommendedKeywords) {
        List<RecommendedKeywordDto> recommendedKeywordsDto = new ArrayList<RecommendedKeywordDto>();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            recommendedKeywordsDto.add(recommendedKeywordDoToDto(recommendedKeyword));
        }
        return recommendedKeywordsDto;
    }

    @Override
    public List<RecommendedLinkDto> recommendedLinkListDoToDto(List<RecommendedLink> recommendedLinks) {
        List<RecommendedLinkDto> recommendedLinksDto = new ArrayList<RecommendedLinkDto>();
        for (RecommendedLink recommendedLink : recommendedLinks) {
            recommendedLinksDto.add(recommendedLinkDoToDto(recommendedLink));
        }
        return recommendedLinksDto;
    }

    private void auditableDo2Dto(JodaAuditable source, AuditableDto target) {
        target.setCreatedDate(dateDoToDto(source.getCreatedDate()));
        target.setCreatedBy(source.getCreatedBy());
        target.setLastUpdated(dateDoToDto(source.getLastUpdated()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());
    }

}
