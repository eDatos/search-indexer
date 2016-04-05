package es.gobcan.istac.search.core.recommendedlink.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.exception.RecommendedLinkNotFoundException;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedLinksServiceInvocationValidator;
import es.gobcan.istac.search.core.utils.ExportUtils;

/**
 * Implementation of RecommendedLinksService.
 */
@Service("recommendedLinksService")
public class RecommendedLinksServiceImpl extends RecommendedLinksServiceImplBase {

    private final static Logger logger = LoggerFactory.getLogger(RecommendedLinksServiceImpl.class);

    public RecommendedLinksServiceImpl() {
    }

    @Override
    public RecommendedLink findRecommendedLinkById(ServiceContext ctx, Long id) throws MetamacException {

        getRecommendedLinksServiceInvocationValidator().checkFindRecommendedLinkById(ctx, id);

        try {
            return getRecommendedLinkRepository().findById(id);
        } catch (RecommendedLinkNotFoundException e) {
            logger.error(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND.getCode(), e);
            throw new MetamacException(ServiceExceptionType.RECOMMENDED_LINK_NOT_FOUND, id);
        }
    }

    @Override
    public RecommendedLink createRecommendedLink(ServiceContext ctx, RecommendedLink recommendedLink) throws MetamacException {

        getRecommendedLinksServiceInvocationValidator().checkCreateRecommendedLink(ctx, recommendedLink);

        recommendedLink = getRecommendedLinkRepository().save(recommendedLink);

        return recommendedLink;
    }

    @Override
    public RecommendedLink updateRecommendedLink(ServiceContext ctx, RecommendedLink recommendedLink) throws MetamacException {
        getRecommendedLinksServiceInvocationValidator().checkUpdateRecommendedLink(ctx, recommendedLink);

        return getRecommendedLinkRepository().save(recommendedLink);
    }

    @Override
    public void deleteRecommendedLink(ServiceContext ctx, Long id) throws MetamacException {

        getRecommendedLinksServiceInvocationValidator().checkDeleteRecommendedLink(ctx, id);

        RecommendedLink recommendedLink = findRecommendedLinkById(ctx, id);

        getRecommendedLinkRepository().delete(recommendedLink);
    }

    @Override
    public List<RecommendedLink> findAllRecommendedLinks(ServiceContext ctx) throws MetamacException {

        getRecommendedLinksServiceInvocationValidator().checkFindAllRecommendedLinks(ctx);

        return getRecommendedLinkRepository().findAll();
    }

    @Override
    public PagedResult<RecommendedLink> findRecommendedLinksByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {

        getRecommendedLinksServiceInvocationValidator().checkFindRecommendedLinksByCondition(ctx, condition, pagingParameter);

        initCriteriaConditions(condition, RecommendedLink.class);

        return getRecommendedLinkRepository().findByCondition(condition, pagingParameter);
    }

    @Override
    public String exportRecommendedLinks(ServiceContext ctx) throws MetamacException {
        logger.info("Exporting recommendedLinks");

        getRecommendedLinksServiceInvocationValidator().checkExportRecommendedLinks(ctx);

        List<RecommendedLink> recommendedLinks = findAllRecommendedLinks(ctx);

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

    private RecommendedLinksServiceInvocationValidator getRecommendedLinksServiceInvocationValidator() {
        return (RecommendedLinksServiceInvocationValidator) ApplicationContextProvider.getApplicationContext().getBean(RecommendedLinksServiceInvocationValidator.BEAN_ID);
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
