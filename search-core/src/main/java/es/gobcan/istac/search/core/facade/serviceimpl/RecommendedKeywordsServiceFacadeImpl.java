package es.gobcan.istac.search.core.facade.serviceimpl;

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
import es.gobcan.istac.search.core.idxmanager.service.recomendados.RecomendadosIndexerService;
import es.gobcan.istac.search.core.mapper.Do2DtoMapper;
import es.gobcan.istac.search.core.mapper.Dto2DoMapper;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.security.SearchSecurityUtils;

/**
 * Implementation of RecommendedKeywordsServiceFacade.
 */
@Service("recommendedKeywordsServiceFacade")
public class RecommendedKeywordsServiceFacadeImpl extends RecommendedKeywordsServiceFacadeImplBase {

    @Autowired
    private Do2DtoMapper                           do2DtoMapper;

    @Autowired
    private Dto2DoMapper                           dto2DoMapper;

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private RecomendadosIndexerService             recomendadosIndexerService;

    public RecommendedKeywordsServiceFacadeImpl() {
    }

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
