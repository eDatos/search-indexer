package es.gobcan.istac.search.core.exception;

import es.gobcan.istac.search.core.error.ServiceExceptionBaseParameters;

public class ServiceExceptionParameters extends ServiceExceptionBaseParameters {

    private static final String PARAMETER_SEARCH_PREFIX        = "parameter.search";

    private static final String IMPORTATION_PREFIX             = "importation";
    public static final String  IMPORTATION_MODE               = createCode(IMPORTATION_PREFIX, "mode");
    public static final String  IMPORTATION_TSV_COLUMN_KEYWORD = createCode(IMPORTATION_PREFIX, "keyword");
    public static final String  IMPORTATION_TSV_COLUMN_TITLE   = createCode(IMPORTATION_PREFIX, "title");
    public static final String  IMPORTATION_TSV_COLUMN_URL     = createCode(IMPORTATION_PREFIX, "url");

    public static String createCodeImportation(String fieldCode) {
        return createCode(IMPORTATION_PREFIX, fieldCode);
    }

    private static String createCode(String classCode, String fieldCode) {
        return PARAMETER_SEARCH_PREFIX + "." + classCode + "." + fieldCode;
    }
}
