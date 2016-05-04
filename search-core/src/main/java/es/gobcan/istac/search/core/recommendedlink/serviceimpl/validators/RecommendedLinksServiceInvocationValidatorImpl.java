package es.gobcan.istac.search.core.recommendedlink.serviceimpl.validators;

import java.io.File;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;

import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.utils.SearchValidationUtils;

public class RecommendedLinksServiceInvocationValidatorImpl {

    public static void checkFindRecommendedLinkById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        SearchValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
    }

    public static void checkCreateRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) throws MetamacException {
        SearchValidationUtils.checkNewRecommendedLink(recommendedLink, exceptions);
    }

    public static void checkUpdateRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) throws MetamacException {
        SearchValidationUtils.checkExistingRecommendedLink(recommendedLink, exceptions);
    }

    public static void checkDeleteRecommendedLink(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        SearchValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
    }

    public static void checkDeleteRecommendedLink(List<Long> ids, List<MetamacExceptionItem> exceptions) {
        for (Long id : ids) {
            SearchValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
        }
    }

    public static void checkFindAllRecommendedLinks(List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Nothing to check
    }

    public static void checkFindRecommendedLinksByCondition(List<ConditionalCriteria> condition, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Nothing to check
    }

    public static void checkExportRecommendedLinks(List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkExportRecommendedLinks(List<Long> ids, List<MetamacExceptionItem> exceptions) {
        for (Long id : ids) {
            SearchValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
        }
    }

    public static void checkImportByAddingRecommendedLinks(File file, String fileName, List<MetamacExceptionItem> exceptions) {
        SearchValidationUtils.checkParameterRequired(file, fileName, exceptions);
    }

    public static void checkImportByReplacingRecommendedLinks(File file, String fileName, List<MetamacExceptionItem> exceptions) {
        SearchValidationUtils.checkParameterRequired(file, fileName, exceptions);
    }
}
