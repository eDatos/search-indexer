package es.gobcan.istac.search.core.recommendedlink.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.domain.RecommendedLinksTsvHeader;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeywordRepository;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLinkProperties;
import es.gobcan.istac.search.core.recommendedlink.exception.RecommendedLinkNotFoundException;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedLinksServiceInvocationValidator;
import es.gobcan.istac.search.core.utils.ExportUtils;
import es.gobcan.istac.search.core.utils.ImportUtils;

/**
 * Implementation of RecommendedLinksService.
 */
@Service("recommendedLinksService")
public class RecommendedLinksServiceImpl extends RecommendedLinksServiceImplBase {

    private final static Logger                        logger = LoggerFactory.getLogger(RecommendedLinksServiceImpl.class);

    @Autowired
    private RecommendedLinksServiceInvocationValidator recommendedLinksServiceInvocationValidator;

    @Autowired
    private RecommendedKeywordsService                 recommendedKeywordsService;

    @Autowired
    private RecommendedKeywordRepository               recommendedKeywordRepository;

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

        return exportRecommendedLinks(recommendedLinks);
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx, List<Long> ids) throws MetamacException {
        logger.info("Exporting selected recommendedLinks " + ids.toString());

        recommendedLinksServiceInvocationValidator.checkExportRecommendedLinks(ctx, ids);

        List<ConditionalCriteria> condition = ConditionalCriteriaBuilder.criteriaFor(RecommendedLink.class).withProperty(RecommendedLinkProperties.id()).in(ids).build();
        List<RecommendedLink> recommendedLinks = getRecommendedLinkRepository().findByCondition(condition);
        return exportRecommendedLinks(recommendedLinks);
    }

    private String exportRecommendedLinks(List<RecommendedLink> recommendedLinks) throws MetamacException {
        // Export
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;

        try {
            File file = File.createTempFile("recommended-links", SearchConstants.TSV_EXPORTATION_EXTENSION);
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, SearchConstants.TSV_EXPORTATION_ENCODING);

            ExportUtils.writeRecommendedLinksHeader(writer);

            for (RecommendedLink recommendedLink : recommendedLinks) {
                ExportUtils.writeRecommendedLink(writer, recommendedLink);
            }

            writer.flush();
            return file.getName();
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.EXPORTATION_TSV_ERROR).withMessageParameters(e).build();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    public void importByReplacingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {
        recommendedLinksServiceInvocationValidator.checkImportByReplacingRecommendedLinks(ctx, file, fileName);

        // Execute importation now
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword = new HashMap<String, RecommendedKeyword>();
        List<RecommendedLink> recommendedLinksToPersist = new ArrayList<RecommendedLink>();

        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = new HashMap<String, RecommendedKeyword>();

        parseFile(ctx, file, exceptionItems, recommendedKeywordsAlreadyExisting, recommendedKeywordsToPersistByKeyword, recommendedLinksToPersist);

        if (!CollectionUtils.isEmpty(exceptionItems)) {
            // rollback and inform about errors
            throw MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR, fileName)).withExceptionItems(exceptionItems).build();
        }

        deleteAllRecommendedLinks();
        deleteAllRecommendedKeywords();

        saveRecommendedKeywordsEfficiently(ctx, recommendedKeywordsToPersistByKeyword);
        saveRecommendedLinksEfficiently(ctx, recommendedLinksToPersist, recommendedKeywordsToPersistByKeyword);
    }

    @Override
    public void importByAddingRecommendedLinks(ServiceContext ctx, File file, String fileName) throws MetamacException {

        recommendedLinksServiceInvocationValidator.checkImportByAddingRecommendedLinks(ctx, file, fileName);

        // Execute importation now
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword = new HashMap<String, RecommendedKeyword>();
        List<RecommendedLink> recommendedLinksToPersist = new ArrayList<RecommendedLink>();

        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = getRecommendedKeywordsAlreadyExisting(ctx);

        parseFile(ctx, file, exceptionItems, recommendedKeywordsAlreadyExisting, recommendedKeywordsToPersistByKeyword, recommendedLinksToPersist);

        if (!CollectionUtils.isEmpty(exceptionItems)) {
            // rollback and inform about errors
            throw MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR, fileName)).withExceptionItems(exceptionItems).build();
        }

        saveRecommendedKeywordsEfficiently(ctx, recommendedKeywordsToPersistByKeyword);
        saveRecommendedLinksEfficiently(ctx, recommendedLinksToPersist, recommendedKeywordsToPersistByKeyword);
    }

    private void deleteAllRecommendedKeywords() {
        recommendedKeywordRepository.deleteAll();
    }

    private void deleteAllRecommendedLinks() {
        getRecommendedLinkRepository().deleteAll();
    }

    private void parseFile(ServiceContext ctx, File file, List<MetamacExceptionItem> exceptionItems, Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting,
            Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword, List<RecommendedLink> recommendedLinksToPersist) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            InputStream stream = new FileInputStream(file);
            String charset = FileUtils.guessCharset(file);
            inputStreamReader = new InputStreamReader(stream, charset);
            bufferedReader = new BufferedReader(inputStreamReader);

            // Header
            String line = bufferedReader.readLine();
            RecommendedLinksTsvHeader header = ImportUtils.parseTsvHeaderToImportRecommendedLinks(line, exceptionItems);

            if (CollectionUtils.isEmpty(exceptionItems)) {

                int lineNumber = 2;
                while ((line = bufferedReader.readLine()) != null) {
                    if (StringUtils.isBlank(line)) {
                        continue;
                    }
                    String[] columns = StringUtils.splitPreserveAllTokens(line, SearchConstants.TSV_SEPARATOR);
                    if (columns.length != header.getColumnsSize()) {
                        exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_WRONG_NUMBER_ELEMENTS, lineNumber));
                        continue;
                    }

                    RecommendedKeyword recommendedKeyword = ImportUtils.tsvLineToRecommendedKeyword(ctx, header, columns, lineNumber, exceptionItems, recommendedKeywordsToPersistByKeyword,
                            recommendedKeywordsAlreadyExisting);
                    RecommendedLink recommendedLink = ImportUtils.tsvLineToRecommendedLink(ctx, header, columns, lineNumber, exceptionItems, recommendedKeyword);

                    if (recommendedKeyword != null && recommendedLink != null) {
                        if (recommendedKeyword.getId() == null && !recommendedKeywordsToPersistByKeyword.containsKey(recommendedKeyword.getKeyword())) {
                            recommendedKeywordsToPersistByKeyword.put(recommendedKeyword.getKeyword(), recommendedKeyword);
                        }
                        recommendedLinksToPersist.add(recommendedLink);
                    }
                    lineNumber++;
                }
            }
        } catch (Exception e) {
            logger.error("Error importing tsv file", e);
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR_FILE_PARSING, e.getMessage()));
        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                // do not relaunch error
                logger.error("Error closing streams", e);
            }
        }
    }

    private Map<String, RecommendedKeyword> getRecommendedKeywordsAlreadyExisting(ServiceContext ctx) throws MetamacException {
        List<RecommendedKeyword> recommendedKeywords = recommendedKeywordsService.findAllRecommendedKeywords(ctx);
        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = new HashMap<String, RecommendedKeyword>();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
            recommendedKeywordsAlreadyExisting.put(recommendedKeyword.getKeyword(), recommendedKeyword);
        }
        return recommendedKeywordsAlreadyExisting;
    }

    private void saveRecommendedKeywordsEfficiently(ServiceContext ctx, Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword) throws MetamacException {
        Collection<RecommendedKeyword> recommendedKeywords = recommendedKeywordsToPersistByKeyword.values();
        for (RecommendedKeyword recommendedKeyword : recommendedKeywords) {
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

}
