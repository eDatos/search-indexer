package es.gobcan.istac.search.core.recommendedlink.serviceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.ConditionRoot;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.mapper.CategoriesRest2DoMapperImpl;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeywordRepository;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLinkProperties;
import es.gobcan.istac.search.core.recommendedlink.exception.RecommendedLinkNotFoundException;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedLinksServiceInvocationValidator;
import es.gobcan.istac.search.core.service.SrmRestInternalFacade;
import es.gobcan.istac.search.core.utils.ExportUtils;
import es.gobcan.istac.search.core.utils.ImportUtils;

/**
 * Implementation of RecommendedLinksService.
 */
@Service("recommendedLinksService")
public class RecommendedLinksServiceImpl extends RecommendedLinksServiceImplBase {

    private static final Logger                        logger = LoggerFactory.getLogger(RecommendedLinksServiceImpl.class);

    @Autowired
    private RecommendedLinksServiceInvocationValidator recommendedLinksServiceInvocationValidator;

    @Autowired
    private RecommendedKeywordsService                 recommendedKeywordsService;

    @Autowired
    private RecommendedKeywordRepository               recommendedKeywordRepository;

    @Autowired
    private SearchConfigurationService                 searchConfigurationService;

    @Autowired
    private SrmRestInternalFacade                      srmRestInternalFacade;

    @Autowired
    private CategoriesRest2DoMapperImpl                categoriesRest2DoMapperImpl;

    public RecommendedLinksServiceImpl() {
    }

    @Override
    public RecommendedLink findRecommendedLinkById(ServiceContext ctx, Long id) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkFindRecommendedLinkById(ctx, id);

        try {
            return getRecommendedLinkRepository().findById(id);
        } catch (RecommendedLinkNotFoundException e) {
            logger.error(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND.getCode(), e);
            throw new MetamacException(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND, id);
        }
    }

    @Override
    public RecommendedLink createRecommendedLink(ServiceContext ctx, RecommendedLink recommendedLink) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkCreateRecommendedLink(ctx, recommendedLink);
        checkRecommendedLinkUnique(ctx, recommendedLink);

        recommendedLink = getRecommendedLinkRepository().save(recommendedLink);

        return recommendedLink;
    }

    @Override
    public RecommendedLink updateRecommendedLink(ServiceContext ctx, RecommendedLink recommendedLink) throws MetamacException {
        recommendedLinksServiceInvocationValidator.checkUpdateRecommendedLink(ctx, recommendedLink);

        return getRecommendedLinkRepository().save(recommendedLink);
    }

    @Override
    public void deleteRecommendedLink(ServiceContext ctx, Long id) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkDeleteRecommendedLink(ctx, id);

        RecommendedLink recommendedLink = findRecommendedLinkById(ctx, id);

        getRecommendedLinkRepository().delete(recommendedLink);
    }

    @Override
    public void deleteRecommendedLink(ServiceContext ctx, List<Long> ids) throws MetamacException {
        recommendedLinksServiceInvocationValidator.checkDeleteRecommendedLink(ctx, ids);

        for (Long id : ids) {
            RecommendedLink recommendedLink = findRecommendedLinkById(ctx, id);
            getRecommendedLinkRepository().delete(recommendedLink);
        }
    }

    @Override
    public List<RecommendedLink> findAllRecommendedLinks(ServiceContext ctx) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkFindAllRecommendedLinks(ctx);

        return getRecommendedLinkRepository().findAll();
    }

    @Override
    public PagedResult<RecommendedLink> findRecommendedLinksByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkFindRecommendedLinksByCondition(ctx, condition, pagingParameter);

        initCriteriaConditions(condition, RecommendedLink.class);

        return getRecommendedLinkRepository().findByCondition(condition, pagingParameter);
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx) throws MetamacException {
        logger.info("Exporting recommendedLinks");

        recommendedLinksServiceInvocationValidator.checkExportRecommendedLinks(ctx);

        List<RecommendedLink> recommendedLinks = findAllRecommendedLinks(ctx);

        return ExportUtils.exportRecommendedLinks(recommendedLinks, getLanguages());
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx, List<Long> ids) throws MetamacException {
        logger.info("Exporting selected recommendedLinks " + ids.toString());

        recommendedLinksServiceInvocationValidator.checkExportRecommendedLinks(ctx, ids);

        List<ConditionalCriteria> condition = ConditionalCriteriaBuilder.criteriaFor(RecommendedLink.class).withProperty(RecommendedLinkProperties.id()).in(ids).build();
        List<RecommendedLink> recommendedLinks = getRecommendedLinkRepository().findByCondition(condition);
        return ExportUtils.exportRecommendedLinks(recommendedLinks, getLanguages());
    }

    private List<String> getLanguages() throws MetamacException {
        return searchConfigurationService.retrieveLanguages();
    }

    @Override
    public void importByReplacingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        recommendedLinksServiceInvocationValidator.checkImportByReplacingRecommendedLinks(ctx, file, fileName);

        // Execute importation now
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword = new HashMap<String, RecommendedKeyword>();
        Map<String, String> categoryCodesToPersistByKeyword = new HashMap<String, String>();
        List<RecommendedLink> recommendedLinksToPersist = new ArrayList<RecommendedLink>();

        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = new HashMap<String, RecommendedKeyword>();

        ImportUtils.parseFile(ctx, file, exceptionItems, recommendedKeywordsAlreadyExisting, recommendedKeywordsToPersistByKeyword, categoryCodesToPersistByKeyword, recommendedLinksToPersist);

        if (!CollectionUtils.isEmpty(exceptionItems)) {
            // rollback and inform about errors
            throw MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR, fileName)).withExceptionItems(exceptionItems).build();
        }

        deleteAllRecommendedLinks();
        deleteAllRecommendedKeywords();

        saveRecommendedKeywordsEfficiently(ctx, recommendedKeywordsToPersistByKeyword, categoryCodesToPersistByKeyword);
        saveRecommendedLinksEfficiently(ctx, recommendedLinksToPersist, recommendedKeywordsToPersistByKeyword);
    }

    @Override
    public void importByAddingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkImportByAddingRecommendedLinks(ctx, file, fileName);

        // Execute importation now
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword = new HashMap<String, RecommendedKeyword>();
        Map<String, String> categoryCodesToPersistByKeyword = new HashMap<String, String>();
        List<RecommendedLink> recommendedLinksToPersist = new ArrayList<RecommendedLink>();

        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = getRecommendedKeywordsAlreadyExisting(ctx);

        ImportUtils.parseFile(ctx, file, exceptionItems, recommendedKeywordsAlreadyExisting, recommendedKeywordsToPersistByKeyword, categoryCodesToPersistByKeyword, recommendedLinksToPersist);

        if (!CollectionUtils.isEmpty(exceptionItems)) {
            // rollback and inform about errors
            throw MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR, fileName)).withExceptionItems(exceptionItems).build();
        }

        saveRecommendedKeywordsEfficiently(ctx, recommendedKeywordsToPersistByKeyword, categoryCodesToPersistByKeyword);
        saveRecommendedLinksEfficiently(ctx, recommendedLinksToPersist, recommendedKeywordsToPersistByKeyword);
    }

    private void deleteAllRecommendedKeywords() {
        recommendedKeywordRepository.deleteAll();
    }

    private void deleteAllRecommendedLinks() {
        getRecommendedLinkRepository().deleteAll();
    }

    private Map<String, RecommendedKeyword> getRecommendedKeywordsAlreadyExisting(ServiceContext ctx) throws MetamacException {
        List<RecommendedKeyword> recommendedKeywords = recommendedKeywordsService.findAllRecommendedKeywords(ctx);
        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = new HashMap<String, RecommendedKeyword>();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            recommendedKeywordsAlreadyExisting.put(recommendedKeyword.getKeyword(), recommendedKeyword);
        }
        return recommendedKeywordsAlreadyExisting;
    }

    private void saveRecommendedKeywordsEfficiently(ServiceContext ctx, Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword, Map<String, String> categoryCodesToPersistByKeyword)
            throws MetamacException {
        Collection<RecommendedKeyword> recommendedKeywords = recommendedKeywordsToPersistByKeyword.values();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            String code = categoryCodesToPersistByKeyword.get(recommendedKeyword.getKeyword());
            if (!StringUtils.isEmpty(code)) {
                Category category = srmRestInternalFacade.retrieveCategoryByCode(code);
                recommendedKeyword.setCategory(categoriesRest2DoMapperImpl.toExternalItem(category));
            }
            recommendedKeyword = recommendedKeywordsService.createRecommendedKeyword(ctx, recommendedKeyword);
            recommendedKeywordsToPersistByKeyword.put(recommendedKeyword.getKeyword(), recommendedKeyword);
        }
    }
    private void saveRecommendedLinksEfficiently(ServiceContext ctx, List<RecommendedLink> recommendedLinksToPersist, Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword)
            throws MetamacException {
        for (RecommendedLink recommendedLink : recommendedLinksToPersist) {
            if (recommendedKeywordsToPersistByKeyword.containsKey(recommendedLink.getRecommendedKeyword().getKeyword())) {
                recommendedLink.setRecommendedKeyword(recommendedKeywordsToPersistByKeyword.get(recommendedLink.getRecommendedKeyword().getKeyword()));
            }
            createRecommendedLink(ctx, recommendedLink);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ConditionalCriteria> initCriteriaConditions(List<ConditionalCriteria> conditions, Class entityClass) {
        List<ConditionalCriteria> conditionsEntity = ConditionalCriteriaBuilder.criteriaFor(entityClass).build();
        if (conditions != null) {
            conditionsEntity.addAll(conditions);
        }
        return conditionsEntity;
    }

    private void checkRecommendedLinkUnique(ServiceContext ctx, RecommendedLink recommendedLink) throws MetamacException {
        // Prepare criteria
        PagingParameter pagingParameter = PagingParameter.pageAccess(1, 1);
        ConditionRoot<RecommendedLink> conditionRoot = ConditionalCriteriaBuilder.criteriaFor(RecommendedLink.class);
        conditionRoot.withProperty(RecommendedLinkProperties.recommendedKeyword().keyword()).ignoreCaseEq(recommendedLink.getRecommendedKeyword().getKeyword());
        conditionRoot.withProperty(RecommendedLinkProperties.url()).ignoreCaseEq(recommendedLink.getUrl());
        List<ConditionalCriteria> conditions = conditionRoot.distinctRoot().build();

        // Find
        PagedResult<RecommendedLink> result = getRecommendedLinkRepository().findByCondition(conditions, pagingParameter);
        if (result.getValues().size() != 0) {
            throw new MetamacException(ServiceExceptionType.RECOMMENDED_LINK_ALREADY_EXIST_KEYWORD_AND_URL_DUPLICATED, recommendedLink.getRecommendedKeyword().getKeyword(), recommendedLink.getUrl());
        }

    }

}
