package es.gobcan.istac.search.core.idxmanager.service.mapper;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedLinksService;

@Component
public class Dto2DoMapperImpl extends BaseDto2DoMapperImpl implements Dto2DoMapper {

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
        target.setRecommendedKeyword(recommendedKeywordDtoToDo(ctx, source.getRecommendedKeyword()));

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
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

    private RecommendedKeyword recommendedKeywordDtoToDo(RecommendedKeywordDto source, RecommendedKeyword target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.RECOMMENDED_KEYWORD);
        }

        // Non modifiables after creation

        // Attributes modifiables
        target.setKeyword(source.getKeyword());

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
    }

}
