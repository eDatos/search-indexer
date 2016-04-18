package es.gobcan.istac.search.core.recommendedlink.serviceimpl.validators;

import java.io.File;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;

import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public class RecommendedLinksServiceInvocationValidatorImpl {

    public static void checkFindRecommendedLinkById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkCreateRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkUpdateRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkDeleteRecommendedLink(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkFindAllRecommendedLinks(List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkFindRecommendedLinksByCondition(List<ConditionalCriteria> condition, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkExportRecommendedLinks(List<MetamacExceptionItem> exceptions) {
        // TODO
    }

    public static void checkExportRecommendedLinks(List<Long> ids, List<MetamacExceptionItem> exceptions) {
        // TODO Auto-generated method stub
    }

    public static void checkImportByAddingRecommendedLinks(File file, String fileName, List<MetamacExceptionItem> exceptions) {
        // TODO Auto-generated method stub
    }

    public static void checkImportByReplacingRecommendedLinks(File file, String fileName, List<MetamacExceptionItem> exceptions) {
        // TODO Auto-generated method stub
    }

}
