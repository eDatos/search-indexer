package es.gobcan.istac.search.core.constants;

import org.siemac.metamac.core.common.constants.CoreCommonConstants;

public final class SearchConstants {

    private SearchConstants() {
    }

    public static final String SECURITY_APPLICATION_ID                   = "GESTOR_BUSCADOR";

    public static final String TSV_EXPORTATION_ENCODING                  = "UTF-8";
    public static final String TSV_EXPORTATION_EXTENSION                 = ".tsv";
    public static final String TSV_SEPARATOR                             = "\t";
    public static final String TSV_LINE_SEPARATOR                        = "\n";
    public static final String TSV_HEADER_INTERNATIONAL_STRING_SEPARATOR = "#";

    public static final String TSV_HEADER_KEYWORD                        = "keyword";
    public static final String TSV_HEADER_KEYWORD_CATEGORY_CODE_NESTED   = "category-code-nested";
    public static final String TSV_HEADER_KEYWORD_CATEGORY_TITLE         = "category-title";
    public static final String TSV_HEADER_TITLE                          = "title";
    public static final String TSV_HEADER_URL                            = "url";
    public static final String TSV_HEADER_DESCRIPTION                    = "description";

    public static final String TSV_RECOMMENDED_LINKS_FILE_PREFIX         = "recommended-links";

    public static final int    LONG_STRING_MAXIMUM_LENGTH                = 4000;
    public static final int    SHORT_STRING_MAXIMUM_LENGTH               = 255;
}