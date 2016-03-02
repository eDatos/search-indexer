package es.gobcan.istac.idxmanager.domain.modelo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum IndexacionEnumDomain {

    // Auxiliares para el indice:
    ID("id", false),
    ORIGENRECURSO("origen_recurso", false),
    FORMATO("formato", false),

    // Del nucleo del ISTAC:
    NM_TYPE("nm_type", true),

    NM_TITLE("nm_title", true),
    NM_SUBTITLE("nm_subtitle", true),
    NM_TITLE_ALTERNATIVE("nm_title_alternative", true),

    NM_SURVEY_CODE("nm_survey_code", false),
    NM_SURVEY_TITLE("nm_survey_title", false),
    NM_SURVEY_ALTERNATIVE("nm_survey_alternative", false),
    NM_SUBJECT_AREAS("nm_subject_areas", true),
    NM_SUBJECT_CODES("nm_subject_codes", true),

    NM_DESCRIPTION("nm_description", true),
    NM_ABSTRACT("nm_abstract", true),
    NM_KEYWORDS("nm_keywords", true),
    NM_COVERAGE_SPATIAL("nm_coverage_spatial", true),
    NM_COVERAGE_SPATIAL_CODES("nm_coverage_spatial_codes", true),
    NM_COVERAGE_TEMPORAL("nm_coverage_temporal", true),
    NM_COVERAGE_TEMPORAL_CODES("nm_coverage_temporal_codes", true),
    NM_LAST_UPDATE("nm_last_update", false),

    SPONSORED("sponsored", true),

    // Campos para facets
    FACET_SUBJECT_KEYVALUE_FF("subject_keyvalue_ff", true),
    FACET_SURVEY_KEYVALUE_FF("survey_keyvalue_ff", true),
    FACET_COVERAGE_TEMPORAL_KEYVALUE_FF("coverage_temporal_keyvalue_ff", true),
    FACET_COVERAGE_SPATIAL_KEYVALUE_FF("coverage_spatial_keyvalue_ff", true),
    FACET_FORMATO_FF(FORMATO.campo, true),

    // Campos TIKA
    TK_SUBJECT("tk_subject", true),
    TK_COMMENTS("tk_comments", true),
    TK_AUTHOR("tk_author", true),
    TK_CATEGORY("tk_category", true),

    TK_CONTENT_TYPE("tk_content-type", true),
    TK_LINKS("tk_links", true),
    TK_CONTENIDO("tk_contenido", false),
    TK_LAST_MODIFIED("tk_last-modified", false),

    // Campos del excel relacionados con save-date
    TK_LAST_SAVE_DATE("tk_last-save-date", false),
    TK_DATE("tk_date", false),

    // Campos dinamicos usados
    CUS_TITLE_PREFIX("cus_tittle", false),
    CUS_DESC_PREFIX("cus_desc", false),
    CUS_URL_PREFIX("cus_url", false);

    private String campo;
    private boolean multievaluado;

    private static final Map<String, IndexacionEnumDomain> lookup = new HashMap<String, IndexacionEnumDomain>();

    static {
        for (IndexacionEnumDomain s : EnumSet.allOf(IndexacionEnumDomain.class)) {
            lookup.put(s.getCampo().toLowerCase(), s);
        }
    }

    public String getCampo() {
        return campo;
    }

    public boolean isMultievaluado() {
        return multievaluado;
    }

    public static IndexacionEnumDomain get(String campo) {
        return lookup.get(campo.toLowerCase());
    }

    private IndexacionEnumDomain(String campo, boolean multievaluado) {
        this.campo = campo;
        this.multievaluado = multievaluado;
    }
}
