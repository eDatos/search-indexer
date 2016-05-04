package es.gobcan.istac.search.core.utils;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;

import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public class SearchValidationUtils extends ValidationUtils {

    public static void checkNewRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) {
        checkParameterRequired(recommendedLink, ServiceExceptionParameters.RECOMMENDED_LINK, exceptions);

        if (recommendedLink == null) {
            return;
        }
        // Metadata that must be empty for new entities
        checkMetadataEmpty(recommendedLink.getId(), ServiceExceptionParameters.RECOMMENDED_LINK__ID, exceptions);
        checkMetadataEmpty(recommendedLink.getVersion(), ServiceExceptionParameters.RECOMMENDED_LINK__VERSION, exceptions);
    }

    public static void checkExistingRecommendedLink(RecommendedLink recommendedLink, List<MetamacExceptionItem> exceptions) {
        checkParameterRequired(recommendedLink, ServiceExceptionParameters.RECOMMENDED_LINK, exceptions);

        if (recommendedLink == null) {
            return;
        }
        checkMetadataRequired(recommendedLink.getRecommendedKeyword(), ServiceExceptionParameters.RECOMMENDED_LINK__RECOMMENDED_KEYWORD, exceptions);
        checkMetadataRequired(recommendedLink.getUrl(), ServiceExceptionParameters.RECOMMENDED_LINK__URL, exceptions);
        checkMetadataRequired(recommendedLink.getTitle(), ServiceExceptionParameters.RECOMMENDED_LINK__TITLE, exceptions);
    }

    public static void checkNewRecommendedKeyword(RecommendedKeyword recommendedKeyword, List<MetamacExceptionItem> exceptions) {
        checkParameterRequired(recommendedKeyword, ServiceExceptionParameters.RECOMMENDED_KEYWORD, exceptions);

        if (recommendedKeyword == null) {
            return;
        }
        // Metadata that must be empty for new entities
        checkMetadataEmpty(recommendedKeyword.getId(), ServiceExceptionParameters.RECOMMENDED_KEYWORD__ID, exceptions);
        checkMetadataEmpty(recommendedKeyword.getVersion(), ServiceExceptionParameters.RECOMMENDED_KEYWORD__VERSION, exceptions);
    }

    public static void checkExistingRecommendedKeyword(RecommendedKeyword recommendedKeyword, List<MetamacExceptionItem> exceptions) {
        checkParameterRequired(recommendedKeyword, ServiceExceptionParameters.RECOMMENDED_KEYWORD, exceptions);

        if (recommendedKeyword == null) {
            return;
        }
        checkMetadataRequired(recommendedKeyword.getKeyword(), ServiceExceptionParameters.RECOMMENDED_KEYWORD__KEYWORD, exceptions);
    }
}
