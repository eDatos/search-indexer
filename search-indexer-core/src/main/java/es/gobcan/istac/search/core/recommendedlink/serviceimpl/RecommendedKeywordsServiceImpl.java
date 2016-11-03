package es.gobcan.istac.search.core.recommendedlink.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.ConditionRoot;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeywordProperties;
import es.gobcan.istac.search.core.recommendedlink.exception.RecommendedKeywordNotFoundException;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedKeywordsServiceInvocationValidator;

/**
 * Implementation of RecommendedKeywordsService.
 */
@Service("recommendedKeywordsService")
public class RecommendedKeywordsServiceImpl extends RecommendedKeywordsServiceImplBase {

    private final static Logger                           logger = LoggerFactory.getLogger(RecommendedKeywordsServiceImpl.class);

    @Autowired
    private RecommendedKeywordsServiceInvocationValidator recommendedKeywordsServiceInvocationValidator;

    public RecommendedKeywordsServiceImpl() {
    }

    @Override
    public RecommendedKeyword findRecommendedKeywordById(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        recommendedKeywordsServiceInvocationValidator.checkFindRecommendedKeywordById(ctx, id);

        try {
            return getRecommendedKeywordRepository().findById(id);
        } catch (RecommendedKeywordNotFoundException e) {
            logger.error(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND.getCode(), e);
            throw new MetamacException(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND, id);
        }
    }

    @Override
    public RecommendedKeyword createRecommendedKeyword(ServiceContext ctx, RecommendedKeyword recommendedKeyword) throws MetamacException {

        // Validations
        recommendedKeywordsServiceInvocationValidator.checkCreateRecommendedKeyword(ctx, recommendedKeyword);
        checkRecommendedKeywordKeywordUnique(ctx, recommendedKeyword);

        recommendedKeyword = getRecommendedKeywordRepository().save(recommendedKeyword);

        return recommendedKeyword;
    }

    @Override
    public RecommendedKeyword updateRecommendedKeyword(ServiceContext ctx, RecommendedKeyword recommendedKeyword) throws MetamacException {
        recommendedKeywordsServiceInvocationValidator.checkUpdateRecommendedKeyword(ctx, recommendedKeyword);

        return getRecommendedKeywordRepository().save(recommendedKeyword);
    }

    @Override
    public List<RecommendedKeyword> updateRecommendedKeyword(ServiceContext ctx, List<RecommendedKeyword> recommendedKeywords) throws MetamacException {
        recommendedKeywordsServiceInvocationValidator.checkUpdateRecommendedKeyword(ctx, recommendedKeywords);

        List<RecommendedKeyword> recommendedKeywordsResult = new ArrayList<RecommendedKeyword>();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            recommendedKeywordsResult.add(updateRecommendedKeyword(ctx, recommendedKeyword));
        }
        return recommendedKeywordsResult;
    }

    @Override
    public void deleteRecommendedKeyword(ServiceContext ctx, Long id) throws MetamacException {

        recommendedKeywordsServiceInvocationValidator.checkDeleteRecommendedKeyword(ctx, id);

        RecommendedKeyword recommendedKeyword = findRecommendedKeywordById(ctx, id);

        getRecommendedKeywordRepository().delete(recommendedKeyword);

    }

    @Override
    public void deleteRecommendedKeyword(ServiceContext ctx, List<Long> ids) throws MetamacException {
        recommendedKeywordsServiceInvocationValidator.checkDeleteRecommendedKeyword(ctx, ids);

        for (Long id : ids) {
            deleteRecommendedKeyword(ctx, id);
        }
    }

    @Override
    public List<RecommendedKeyword> findAllRecommendedKeywords(ServiceContext ctx) throws MetamacException {

        recommendedKeywordsServiceInvocationValidator.checkFindAllRecommendedKeywords(ctx);

        return getRecommendedKeywordRepository().findAll();
    }

    @Override
    public PagedResult<RecommendedKeyword> findRecommendedKeywordsByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {

        recommendedKeywordsServiceInvocationValidator.checkFindRecommendedKeywordsByCondition(ctx, condition, pagingParameter);

        initCriteriaConditions(condition, RecommendedKeyword.class);

        return getRecommendedKeywordRepository().findByCondition(condition, pagingParameter);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ConditionalCriteria> initCriteriaConditions(List<ConditionalCriteria> conditions, Class entityClass) {
        List<ConditionalCriteria> conditionsEntity = ConditionalCriteriaBuilder.criteriaFor(entityClass).build();
        if (conditions != null) {
            conditionsEntity.addAll(conditions);
        }
        return conditionsEntity;
    }

    private void checkRecommendedKeywordKeywordUnique(ServiceContext ctx, RecommendedKeyword recommendedKeyword) throws MetamacException {

        // Prepare criteria
        PagingParameter pagingParameter = PagingParameter.pageAccess(1, 1);
        ConditionRoot<RecommendedKeyword> conditionRoot = ConditionalCriteriaBuilder.criteriaFor(RecommendedKeyword.class);
        conditionRoot.withProperty(RecommendedKeywordProperties.keyword()).ignoreCaseEq(recommendedKeyword.getKeyword());
        List<ConditionalCriteria> conditions = conditionRoot.distinctRoot().build();

        // Find
        PagedResult<RecommendedKeyword> result = getRecommendedKeywordRepository().findByCondition(conditions, pagingParameter);
        if (result.getValues().size() != 0) {
            throw new MetamacException(ServiceExceptionType.RECOMMENDED_KEYWORD_ALREADY_EXIST_KEYWORD_DUPLICATED, recommendedKeyword.getKeyword());
        }
    }

}
