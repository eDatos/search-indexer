package es.gobcan.istac.search.core.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapper;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.dto.RecommendedKeywordBaseDto;
import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedLinksService;

@Component
public class Dto2DoMapperImpl extends BaseDto2DoMapperImpl implements Dto2DoMapper {

    @Autowired
    private BaseDto2DoMapper              baseDto2DoMapper;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private SearchConfigurationService    searchConfigurationService;

    @Override
    public RecommendedLink recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        RecommendedLink target = new RecommendedLink();
        if (source.getId() != null) {
            target = getRecommendedLinksService().findRecommendedLinkById(ctx, source.getId());
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getOptimisticLockingVersion());
        }

        recommendedLinkDtoToDo(ctx, source, target);
        return target;
    }

    @Override
    public List<RecommendedLink> recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkGroupedKeywordsDto source) throws MetamacException {
        if (source == null) {
            return null;
        }
        List<RecommendedLink> targets = new ArrayList<RecommendedLink>();
        for (RecommendedKeywordBaseDto keyowordBaseDto : source.getKeywords()) {
            RecommendedLink target = new RecommendedLink();
            target.setTitle(source.getTitle());
            target.setDescription(source.getDescription());
            target.setUrl(source.getUrl());
            target.setRecommendedKeyword(getRecommendedKeywordsService().findRecommendedKeywordById(ctx, keyowordBaseDto.getId()));
            targets.add(target);
        }
        return targets;
    }

    @Override
    public RecommendedKeyword recommendedKeywordDtoToDo(ServiceContext ctx, RecommendedKeywordDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        RecommendedKeyword target = new RecommendedKeyword();
        if (source.getId() != null) {
            target = getRecommendedKeywordsService().findRecommendedKeywordById(ctx, source.getId());
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getOptimisticLockingVersion());
        }

        recommendedKeywordDtoToDo(source, target);
        return target;
    }

    @Override
    public List<RecommendedKeyword> recommendedKeywordListDtoToDo(ServiceContext ctx, List<RecommendedKeywordDto> recommendedKeywordDtos) throws MetamacException {
        List<RecommendedKeyword> recommendedKeywords = new ArrayList<RecommendedKeyword>();
        for (RecommendedKeywordDto recommendedKeywordDto : recommendedKeywordDtos) {
            recommendedKeywords.add(recommendedKeywordDtoToDo(ctx, recommendedKeywordDto));
        }
        return recommendedKeywords;
    }

    private RecommendedLinksService getRecommendedLinksService() {
        return (RecommendedLinksService) ApplicationContextProvider.getApplicationContext().getBean(RecommendedLinksService.BEAN_ID);
    }

    private RecommendedKeywordsService getRecommendedKeywordsService() {
        return (RecommendedKeywordsService) ApplicationContextProvider.getApplicationContext().getBean(RecommendedKeywordsService.BEAN_ID);
    }

    private RecommendedLink recommendedLinkDtoToDo(ServiceContext ctx, RecommendedLinkDto source, RecommendedLink target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.RECOMMENDED_LINK);
        }

        // Non modifiables after creation

        // Attributes modifiables
        target.setUrl(source.getUrl());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setRecommendedKeyword(getRecommendedKeywordsService().findRecommendedKeywordById(ctx, source.getRecommendedKeyword().getId()));

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
    }

    private RecommendedKeyword recommendedKeywordDtoToDo(RecommendedKeywordDto source, RecommendedKeyword target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.RECOMMENDED_KEYWORD);
        }

        // Non modifiables after creation

        // Attributes modifiables
        target.setKeyword(source.getKeyword());
        target.setCategory(externalItemCategoryDtoToExternalItem(source.getCategory(), target.getCategory()));

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
    }

    private ExternalItem externalItemCategoryDtoToExternalItem(ExternalItemDto source, ExternalItem target) throws MetamacException {
        ExternalItem newTarget = externalItemDtoToExternalItem(source, target, ServiceExceptionParameters.RECOMMENDED_KEYWORD__CATEGORY);

        if (newTarget != null) {
            // Cambiar statisticaloperations por structuralresources
            newTarget.setUri(CoreCommonUtil.externalItemUrlDtoToUrlDo(getSrmInternalApiUrlBase(), newTarget.getUri()));
            newTarget.setManagementAppUrl(CoreCommonUtil.externalItemUrlDtoToUrlDo(getSrmInternalWebApplicationUrlBase(), newTarget.getManagementAppUrl()));
        }
        return newTarget;
    }

    private String getSrmInternalWebApplicationUrlBase() throws MetamacException {
        return searchConfigurationService.retrieveSrmInternalWebApplicationUrlBase();
    }

    private String getSrmInternalApiUrlBase() throws MetamacException {
        return searchConfigurationService.retrieveSrmInternalApiUrlBase();
    }

    private ExternalItem externalItemDtoToExternalItem(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            // New
            target = new ExternalItem();
        }
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringToEntity(source.getTitle(), target.getTitle(), metadataName + ServiceExceptionParameters.EXTERNAL_ITEM_TITLE));

        return target;
    }

    private InternationalString internationalStringToEntity(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {

        // Skip html
        baseDto2DoMapper.internationalStringHtmlToTextPlain(source);

        // Check it is valid
        baseDto2DoMapper.checkInternationalStringDtoValid(source, metadataName);

        // Transform
        if (source == null) {
            if (target != null) {
                // Delete old entity
                internationalStringRepository.delete(target);
            }
            return null;
        }

        if (ValidationUtils.isEmpty(source)) {
            // international string is not complete
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        if (target == null) {
            target = new InternationalString();
        }
        Set<LocalisedString> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);
        return target;
    }

    private Set<LocalisedString> localisedStringDtoToDo(Set<LocalisedStringDto> sources, Set<LocalisedString> targets, InternationalString internationalStringTarget) {

        Set<LocalisedString> targetsBefore = targets;
        targets = new HashSet<LocalisedString>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (LocalisedString target : targetsBefore) {
                if (source.getLocale().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source, internationalStringTarget));
            }
        }
        return targets;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, InternationalString internationalStringTarget) {
        LocalisedString target = new LocalisedString();
        localisedStringDtoToDo(source, target, internationalStringTarget);
        return target;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, LocalisedString target, InternationalString internationalStringTarget) {
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        target.setIsUnmodifiable(source.getIsUnmodifiable());
        target.setInternationalString(internationalStringTarget);
        return target;
    }

}
