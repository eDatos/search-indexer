package es.gobcan.istac.search.core.facade.serviceimpl;

import java.io.File;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.criteria.mapper.MetamacCriteria2SculptorCriteriaMapper;
import es.gobcan.istac.search.core.criteria.mapper.SculptorCriteria2MetamacCriteriaMapper;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.enume.domain.RoleEnum;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.mapper.Do2DtoMapper;
import es.gobcan.istac.search.core.mapper.Dto2DoMapper;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.security.SearchSecurityUtils;

/**
 * Implementation of RecommendedLinksServiceFacade.
 */
@Service("recommendedLinksServiceFacade")
public class RecommendedLinksServiceFacadeImpl extends RecommendedLinksServiceFacadeImplBase {

    @Autowired
    private Dto2DoMapper                           dto2DoMapper;

    @Autowired
    private Do2DtoMapper                           do2DtoMapper;

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private RecomendadosIndexerService             recomendadosIndexerService;

    public RecommendedLinksServiceFacadeImpl() {
    }

    @Override
    public RecommendedLinkDto retrieveRecommendedLinkById(ServiceContext ctx, Long id) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        RecommendedLink recommendedLink = getRecommendedLinksService().findRecommendedLinkById(ctx, id);

        // Transform to Dto
        return do2DtoMapper.recommendedLinkDoToDto(recommendedLink);
    }

    @Override
    public RecommendedLinkDto createRecommendedLink(ServiceContext ctx, RecommendedLinkDto recommendedLinkDto) throws MetamacException {
        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ADMINISTRADOR);

        // Transform to Entity
        RecommendedLink recommendedLink = dto2DoMapper.recommendedLinkDtoToDo(ctx, recommendedLinkDto);

        // Service call
        recommendedLink = getRecommendedLinksService().createRecommendedLink(ctx, recommendedLink);

        // Automatically reindex on creating a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);

        // Transform to Dto
        return do2DtoMapper.recommendedLinkDoToDto(recommendedLink);
    }

    @Override
    public RecommendedLinkDto updateRecommendedLink(ServiceContext ctx, RecommendedLinkDto recommendedLinkDto) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ADMINISTRADOR);

        // Transform to Entity
        RecommendedLink recommendedLink = dto2DoMapper.recommendedLinkDtoToDo(ctx, recommendedLinkDto);

        // Service call
        recommendedLink = getRecommendedLinksService().updateRecommendedLink(ctx, recommendedLink);

        // Automatically reindex on updating a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);

        return do2DtoMapper.recommendedLinkDoToDto(recommendedLink);
    }

    @Override
    public void deleteRecommendedLink(ServiceContext ctx, Long id) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ADMINISTRADOR);

        getRecommendedLinksService().deleteRecommendedLink(ctx, id);

        // Automatically reindex on deleting a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public List<RecommendedLinkDto> findAllRecommendedLinks(ServiceContext ctx) throws MetamacException {
        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ANY_ROLE_ALLOWED);
        List<RecommendedLink> recommendedLinks = getRecommendedLinksService().findAllRecommendedLinks(ctx);
        return do2DtoMapper.recommendedLinkListDoToDto(recommendedLinks);
    }

    @Override
    public MetamacCriteriaResult<RecommendedLinkDto> findRecommendedLinks(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ANY_ROLE_ALLOWED);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getRecommendedLinkCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<RecommendedLink> result = getRecommendedLinksService().findRecommendedLinksByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        return sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultRecommendedLinkDto(result, sculptorCriteria.getPageSize());
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ANY_ROLE_ALLOWED);

        return getRecommendedLinksService().exportRecommendedLinks(ctx);
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx, List<Long> ids) throws MetamacException {

        // Security
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ANY_ROLE_ALLOWED);

        return getRecommendedLinksService().exportRecommendedLinks(ctx, ids);
    }

    @Override
    public void importByReplacingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        // SECURITY
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ADMINISTRADOR);

        getRecommendedLinksService().importByReplacingRecommendedLinks(ctx, file, fileName);
        
        // Automatically reindex on import
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public void importByAddingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        // SECURITY
        SearchSecurityUtils.isSearchRoleAllowed(ctx, RoleEnum.ADMINISTRADOR);

        getRecommendedLinksService().importByAddingRecommendedLinks(ctx, file, fileName);
        
        // Automatically reindex on import
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }
}
