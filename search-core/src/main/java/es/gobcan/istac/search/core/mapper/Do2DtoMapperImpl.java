package es.gobcan.istac.search.core.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.domain.JodaAuditable;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.AuditableDto;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

@Component
public class Do2DtoMapperImpl extends BaseDo2DtoMapperImpl implements Do2DtoMapper {

    @Autowired
    private BaseDo2DtoMapper baseDo2DtoMapper;

    @Override
    public RecommendedLinkDto recommendedLinkDoToDto(RecommendedLink source) throws MetamacException {
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
    public RecommendedKeywordDto recommendedKeywordDoToDto(RecommendedKeyword source) throws MetamacException {
        RecommendedKeywordDto target = new RecommendedKeywordDto();

        // Common info
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        auditableDo2Dto(source, target);
        target.setOptimisticLockingVersion(source.getVersion());

        target.setKeyword(source.getKeyword());
        target.setCategory(externalItemToDto(source.getCategory()));

        return target;
    }

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

    @Override
    public List<RecommendedKeywordDto> recommendedKeywordListDoToDto(List<RecommendedKeyword> recommendedKeywords) throws MetamacException {
        List<RecommendedKeywordDto> recommendedKeywordsDto = new ArrayList<RecommendedKeywordDto>();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            recommendedKeywordsDto.add(recommendedKeywordDoToDto(recommendedKeyword));
        }
        return recommendedKeywordsDto;
    }

    @Override
    public List<RecommendedLinkDto> recommendedLinkListDoToDto(List<RecommendedLink> recommendedLinks) throws MetamacException {
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

    // ------------------------------------------------------------
    // COMMON DO2DTO METHODS
    // ------------------------------------------------------------

    private ExternalItemDto externalItemToDto(ExternalItem source) throws MetamacException {
        ExternalItemDto target = externalItemDoToDtoWithoutUrls(source);
        if (target != null) {
            target.setUri(baseDo2DtoMapper.externalItemApiUrlDoToDto(source.getType(), source.getUri()));
            target.setManagementAppUrl(baseDo2DtoMapper.externalItemWebAppUrlDoToDto(source.getType(), source.getManagementAppUrl()));
        }
        return target;
    }

    private ExternalItemDto externalItemDoToDtoWithoutUrls(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringDoToDto(source.getTitle()));
        return target;
    }

    private InternationalStringDto internationalStringDoToDto(InternationalString source) {
        if (source == null) {
            return null;
        }
        InternationalStringDto target = new InternationalStringDto();
        target.getTexts().addAll(localisedStringDoToDto(source.getTexts()));
        return target;
    }

    private Set<LocalisedStringDto> localisedStringDoToDto(Set<LocalisedString> sources) {
        Set<LocalisedStringDto> targets = new HashSet<LocalisedStringDto>();
        for (LocalisedString source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
            target.setIsUnmodifiable(source.getIsUnmodifiable());
            targets.add(target);
        }
        return targets;
    }
}
