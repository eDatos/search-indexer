package es.gobcan.istac.search.web.client.utils;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.client.SearchWeb;
import es.gobcan.istac.search.web.shared.utils.SearchSharedTokens;

public class CommonUtils {

    public static LinkedHashMap<String, String> getRecommendedKeywordsHasMap(List<RecommendedKeywordDto> recommendedKeywordList) {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(new String(), new String());
        for (RecommendedKeywordDto recommendedKeyword : recommendedKeywordList) {
            valueMap.put(recommendedKeyword.getId().toString(), recommendedKeyword.getKeyword());
        }
        return valueMap;
    }

    public static void downloadFile(String fileName) {
        StringBuffer url = new StringBuffer();
        url.append(URL.encode(SearchWeb.getRelativeURL(SearchSharedTokens.FILE_DOWNLOAD_DIR_PATH)));
        url.append("?").append(URL.encode(SearchSharedTokens.PARAM_FILE_NAME)).append("=").append(URL.encode(fileName));
        Window.open(url.toString(), "_blank", "");
    }
}
