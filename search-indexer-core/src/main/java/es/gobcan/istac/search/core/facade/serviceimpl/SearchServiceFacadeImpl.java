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
import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.web.WebIndexerService;
import es.gobcan.istac.search.core.mapper.Do2DtoMapper;
import es.gobcan.istac.search.core.mapper.Dto2DoMapper;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.security.SearchSecurityUtils;

/**
 * Implementation of SearchServiceFacade.
 */
@Service("searchServiceFacade")
public class SearchServiceFacadeImpl extends SearchServiceFacadeImplBase {

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

    @Autowired
    private NucleoIstacIndexerService              nucleoIstacIndexerService;

    @Autowired
    private WebIndexerService                      webIndexerService;

    public SearchServiceFacadeImpl() {
    }

    //
    // Reindex
    //

    @Override
    public void reindexWeb(ServiceContext ctx) throws ServiceExcepcion, MetamacException {

        // Security
        SearchSecurityUtils.canReindexWeb(ctx);

        // Create
        webIndexerService.reindexWeb(ctx);
    }

    @Override
    public void reindexGpe(ServiceContext ctx) throws ServiceExcepcion, MetamacException {

        // Security
        SearchSecurityUtils.canReindexGpe(ctx);

        // Create
        nucleoIstacIndexerService.reindexarGPEelementos(ctx);
    }

    @Override
    public void reindexRecommendedLinks(ServiceContext ctx) throws ServiceExcepcion, MetamacException {

        // Security
        SearchSecurityUtils.canReindexRecommendedLinks(ctx);

        // Create
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    //
    // Recommended links
    //

    @Override
    public RecommendedLinkDto retrieveRecommendedLinkById(ServiceContext ctx, Long id) throws MetamacException {

        // Security
        SearchSecurityUtils.canRetrieveRecommendedLink(ctx);

        // Service call
        RecommendedLink recommendedLink = getRecommendedLinksService().findRecommendedLinkById(ctx, id);

        // Transform to Dto
        return do2DtoMapper.recommendedLinkDoToDto(recommendedLink);
    }

    @Override
    public RecommendedLinkDto createRecommendedLink(ServiceContext ctx, RecommendedLinkDto recommendedLinkDto) throws MetamacException {
        // Security
        SearchSecurityUtils.canCreateRecommendedLink(ctx);

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
    public void createRecommendedLinks(ServiceContext ctx, RecommendedLinkGroupedKeywordsDto recommendedLinkGroupedKeywordsDto) throws MetamacException {
        // Security
        SearchSecurityUtils.canCreateRecommendedLink(ctx);

        // Transform to Entity
        List<RecommendedLink> recommendedLinks = dto2DoMapper.recommendedLinkDtoToDo(ctx, recommendedLinkGroupedKeywordsDto);

        // Service call
        for (RecommendedLink recommendedLink : recommendedLinks) {
            getRecommendedLinksService().createRecommendedLink(ctx, recommendedLink);
        }

        // Automatically reindex on creating a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public RecommendedLinkDto updateRecommendedLink(ServiceContext ctx, RecommendedLinkDto recommendedLinkDto) throws MetamacException {

        // Security
        SearchSecurityUtils.canUpdateRecommendedLink(ctx);

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
        SearchSecurityUtils.canDeleteRecommendedLink(ctx);

        getRecommendedLinksService().deleteRecommendedLink(ctx, id);

        // Automatically reindex on deleting a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public void deleteRecommendedLink(ServiceContext ctx, List<Long> ids) throws MetamacException {
        // Security
        SearchSecurityUtils.canDeleteRecommendedLink(ctx);

        getRecommendedLinksService().deleteRecommendedLink(ctx, ids);

        // Automatically reindex on deleting a recommended link
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public List<RecommendedLinkDto> findAllRecommendedLinks(ServiceContext ctx) throws MetamacException {
        // Security
        SearchSecurityUtils.canFindRecommendedLinks(ctx);
        List<RecommendedLink> recommendedLinks = getRecommendedLinksService().findAllRecommendedLinks(ctx);
        return do2DtoMapper.recommendedLinkListDoToDto(recommendedLinks);
    }

    @Override
    public MetamacCriteriaResult<RecommendedLinkDto> findRecommendedLinks(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        SearchSecurityUtils.canFindRecommendedLinks(ctx);

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
        SearchSecurityUtils.canExportRecommendedLinks(ctx);

        return getRecommendedLinksService().exportRecommendedLinks(ctx);
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx, List<Long> ids) throws MetamacException {

        // Security
        SearchSecurityUtils.canExportRecommendedLinks(ctx);

        return getRecommendedLinksService().exportRecommendedLinks(ctx, ids);
    }

    @Override
    public void importByReplacingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        // SECURITY
        SearchSecurityUtils.canImportRecommendedLinks(ctx);

        getRecommendedLinksService().importByReplacingRecommendedLinks(ctx, file, fileName);

        // Automatically reindex on import
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public void importByAddingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        // SECURITY
        SearchSecurityUtils.canImportRecommendedLinks(ctx);

        getRecommendedLinksService().importByAddingRecommendedLinks(ctx, file, fileName);

        // Automatically reindex on import
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    //
    // Recommended keyowrds
    //

    @Override
    public RecommendedKeywordDto retrieveRecommendedKeywordById(ServiceContext ctx, Long id) throws MetamacException {

        // Security
        SearchSecurityUtils.canRetrieveRecommendedKeyword(ctx);

        // Service call
        RecommendedKeyword recommendedKeyword = getRecommendedKeywordsService().findRecommendedKeywordById(ctx, id);

        // Transform to Dto
        return do2DtoMapper.recommendedKeywordDoToDto(recommendedKeyword);
    }

    @Override
    public RecommendedKeywordDto createRecommendedKeyword(ServiceContext ctx, RecommendedKeywordDto recommendedKeywordDto) throws MetamacException {

        // Security
        SearchSecurityUtils.canCreateRecommendedKeyword(ctx);

        // Transform
        RecommendedKeyword recommendedKeyword = dto2DoMapper.recommendedKeywordDtoToDo(ctx, recommendedKeywordDto);

        // Service call
        recommendedKeyword = getRecommendedKeywordsService().createRecommendedKeyword(ctx, recommendedKeyword);

        return do2DtoMapper.recommendedKeywordDoToDto(recommendedKeyword);
    }

    @Override
    public RecommendedKeywordDto updateRecommendedKeyword(ServiceContext ctx, RecommendedKeywordDto recommendedKeywordDto) throws MetamacException {

        // Security
        SearchSecurityUtils.canUpdateRecommendedKeyword(ctx);

        // Transform to Entity
        RecommendedKeyword recommendedKeyword = dto2DoMapper.recommendedKeywordDtoToDo(ctx, recommendedKeywordDto);

        // Service call
        recommendedKeyword = getRecommendedKeywordsService().updateRecommendedKeyword(ctx, recommendedKeyword);

        // Automatically reindex on updating a recommended keyword
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);

        return do2DtoMapper.recommendedKeywordDoToDto(recommendedKeyword);

    }

    @Override
    public List<RecommendedKeywordDto> updateRecommendedKeyword(ServiceContext ctx, List<RecommendedKeywordDto> recommendedKeywordDtos) throws MetamacException {
        // Security
        SearchSecurityUtils.canUpdateRecommendedKeyword(ctx);

        // Transform to Entity
        List<RecommendedKeyword> recommendedKeywords = dto2DoMapper.recommendedKeywordListDtoToDo(ctx, recommendedKeywordDtos);

        // Service call
        recommendedKeywords = getRecommendedKeywordsService().updateRecommendedKeyword(ctx, recommendedKeywords);

        // Automatically reindex on updating a recommended keyword
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);

        return do2DtoMapper.recommendedKeywordListDoToDto(recommendedKeywords);
    }

    @Override
    public void deleteRecommendedKeyword(ServiceContext ctx, Long id) throws MetamacException {

        // Security
        SearchSecurityUtils.canDeleteRecommendedKeyword(ctx);

        getRecommendedKeywordsService().deleteRecommendedKeyword(ctx, id);

        // Automatically reindex on deleting a recommended keyword
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public void deleteRecommendedKeyword(ServiceContext ctx, List<Long> ids) throws MetamacException {
        // Security
        SearchSecurityUtils.canDeleteRecommendedKeyword(ctx);

        getRecommendedKeywordsService().deleteRecommendedKeyword(ctx, ids);

        // Automatically reindex on deleting a recommended keyword
        recomendadosIndexerService.reindexRecommendedKeywords(ctx);
    }

    @Override
    public List<RecommendedKeywordDto> findAllRecommendedKeywords(ServiceContext ctx) throws MetamacException {
        // Security
        SearchSecurityUtils.canFindRecommendedKeywords(ctx);

        // Service call
        List<RecommendedKeyword> recommendedKeywords = getRecommendedKeywordsService().findAllRecommendedKeywords(ctx);

        // Transform to Dto
        return do2DtoMapper.recommendedKeywordListDoToDto(recommendedKeywords);
    }

    @Override
    public MetamacCriteriaResult<RecommendedKeywordDto> findRecommendedKeywords(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        SearchSecurityUtils.canFindRecommendedKeywords(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getRecommendedKeywordCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<RecommendedKeyword> result = getRecommendedKeywordsService().findRecommendedKeywordsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        return sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultRecommendedKeywordDto(result, sculptorCriteria.getPageSize());
    }
}
