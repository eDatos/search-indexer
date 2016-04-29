package es.gobcan.istac.search.core.recommendedlink.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.RecommendedKeywordsService;

public class RecommendedKeywordsServiceInvocationValidatorImpl {

    @Autowired
    RecommendedKeywordsService recommendedKeywordsService;

    public static void checkFindRecommendedKeywordById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkCreateRecommendedKeyword(RecommendedKeyword recommendedKeyword, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkUpdateRecommendedKeyword(RecommendedKeyword recommendedKeyword, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkUpdateRecommendedKeyword(List<RecommendedKeyword> recommendedKeyword, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkDeleteRecommendedKeyword(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

    public static void checkDeleteRecommendedKeyword(List<Long> ids, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkFindAllRecommendedKeywords(List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Nothing to check
    }

    public static void checkFindRecommendedKeywordsByCondition(List<ConditionalCriteria> condition, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }

}
