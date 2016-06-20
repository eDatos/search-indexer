package es.gobcan.istac.search.web.client.utils;

import java.util.LinkedHashMap;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.shared.utils.SearchSharedTokens;

public class CommonUtils {

    public static LinkedHashMap<String, String> getRecommendedKeywordsHashMap(List<RecommendedKeywordDto> recommendedKeywordList) {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(new String(), new String());
        for (RecommendedKeywordDto recommendedKeyword : recommendedKeywordList) {
            valueMap.put(recommendedKeyword.getId().toString(), getRecommendedKeywordName(recommendedKeyword));
        }
        return valueMap;
    }

    private static String getRecommendedKeywordName(RecommendedKeywordDto recommendedKeyword) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(recommendedKeyword.getKeyword());
        if (recommendedKeyword.getCategory() != null) {
            stringBuilder.append(" - ").append(ExternalItemUtils.getExternalItemName(recommendedKeyword.getCategory()));
        }
        return stringBuilder.toString();
    }

    public static String getRecommendedKeywordLinkedName(RecommendedKeywordDto recommendedKeyword) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(recommendedKeyword.getKeyword());
        if (recommendedKeyword.getCategory() != null) {
            stringBuilder.append(" - ").append(getCategoryNameLinked(recommendedKeyword.getCategory()));
        }
        return stringBuilder.toString();
    }

    public static String getCategoryNameLinked(ExternalItemDto externalItemDto) {
        StringBuilder stringBuilder = new StringBuilder();
        if (externalItemDto != null) {
            stringBuilder.append(ExternalItemUtils.getExternalItemName(externalItemDto));
            if (!externalItemDto.getManagementAppUrl().isEmpty()) {
                stringBuilder.insert(0, "<a href='" + externalItemDto.getManagementAppUrl() + "'>");
                stringBuilder.append("</a>");
            }
            return stringBuilder.toString();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static void downloadFile(String fileName) {
        StringBuffer url = new StringBuffer();
        url.append(URL.encode(SearchWeb.getRelativeURL(SearchSharedTokens.FILE_DOWNLOAD_DIR_PATH)));
        url.append("?").append(URL.encode(SearchSharedTokens.PARAM_FILE_NAME)).append("=").append(URL.encode(fileName));
        Window.open(url.toString(), "_blank", "");
    }

    public static LengthRangeValidator getShortTextLengthValidator() {
        return getMaxLengthValidator(SearchConstants.SHORT_STRING_MAXIMUM_LENGTH);
    }

    public static LengthRangeValidator getLongTextLengthValidator() {
        return getMaxLengthValidator(SearchConstants.LONG_STRING_MAXIMUM_LENGTH);
    }

    private static LengthRangeValidator getMaxLengthValidator(int maxLength) {
        LengthRangeValidator lengthRangeValidator = new LengthRangeValidator();
        lengthRangeValidator.setMin(0);
        lengthRangeValidator.setMax(maxLength);
        return lengthRangeValidator;
    }
}
