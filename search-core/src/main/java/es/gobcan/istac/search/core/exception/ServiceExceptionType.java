package es.gobcan.istac.search.core.exception;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType RECOMMENDED_LINK_NOT_FOUND                                = create("exception.search.recommended_link.not_found");

    // Exportation
    public static final CommonServiceExceptionType EXPORTATION_TSV_ERROR                                     = create("exception.search.exportation.tsv.error");
    public static final CommonServiceExceptionType RECOMMENDED_KEYWORD_ALREADY_EXIST_KEYWORD_DUPLICATED      = create("exception.search.recommended_keyword.already_exists.keyword_duplicated");
    public static final CommonServiceExceptionType RECOMMENDED_LINK_ALREADY_EXIST_KEYWORD_AND_URL_DUPLICATED = create("exception.search.recommended_link.already_exists.keyword_and_url_duplicated");

    // From old es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo
    public static final CommonServiceExceptionType INDEXATION_SOLR_ERROR                                     = create("exception.search.indexation.solr.error");
    public static final CommonServiceExceptionType INDEXATION_INPROGRESS                                     = create("exception.search.indexation.inprogress");
    public static final CommonServiceExceptionType INDEXATION_RECOMMENDED_KEYWORDS                           = create("exception.search.indexation.recommended_keywords");

    // Importation, org.siemac.metamac.srm.core.common.error.ServiceExceptionType used as guide
    public static final CommonServiceExceptionType IMPORTATION_TSV_ERROR                                     = create("exception.search.importation.tsv.error");
    public static final CommonServiceExceptionType IMPORTATION_TSV_ERROR_FILE_PARSING                        = create("exception.search.importation.tsv.error.file_parse");
    public static final CommonServiceExceptionType IMPORTATION_TSV_HEADER_INCORRECT                          = create("exception.search.importation.tsv.error.header.incorrect");
    public static final CommonServiceExceptionType IMPORTATION_TSV_LINE_INCORRECT                            = create("exception.search.importation.tsv.error.line.incorrect");
    public static final CommonServiceExceptionType IMPORTATION_TSV_METADATA_REQUIRED                         = create("exception.search.importation.tsv.error.metadata_required");
    public static final CommonServiceExceptionType IMPORTATION_TSV_WRONG_NUMBER_ELEMENTS                     = create("exception.search.importation.tsv.error.wrong_number_elements");
}
