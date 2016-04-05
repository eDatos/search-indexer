package es.gobcan.istac.search.core.recommendedlink.serviceimpl.validators;

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
}
