package es.gobcan.istac.search.core.exception;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType RECOMMENDED_LINK_NOT_FOUND                           = create("exception.search.recommended_link.not_found");

    // Exportation
    public static final CommonServiceExceptionType EXPORTATION_TSV_ERROR                                = create("exception.search.exportation.tsv.error");
    public static final CommonServiceExceptionType RECOMMENDED_KEYWORD_ALREADY_EXIST_KEYWORD_DUPLICATED = create("exception.search.recommended_keyword.already_exists.keyword_duplicated");

    // From old es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo
    public static final CommonServiceExceptionType INDEXATION_SOLR_ERROR                                = create("exception.search.indexation.solr.error");
    public static final CommonServiceExceptionType INDEXATION_INPROGRESS                                = create("exception.search.indexation.inprogress");
    public static final CommonServiceExceptionType INDEXATION_RECOMMENDED_KEYWORDS                      = create("exception.search.indexation.recommended_keywords");
}
