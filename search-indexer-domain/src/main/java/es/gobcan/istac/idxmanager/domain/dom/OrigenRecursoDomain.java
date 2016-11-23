package es.gobcan.istac.idxmanager.domain.dom;

public enum OrigenRecursoDomain {

    /*
     * Representa el origen del recurso que ha indexar en SOLR.
     */

    // Recurso patrocinado
    RECURSO_PATROCINADO("PATROCINADO", "dominio.origenrecurso.gpe"),

    // GPE: El recurso esta gestionado por el GPE (Alfresco) y por tanto se visualiza en JAXI
    RECURSO_GPE("GPE", "dominio.origenrecurso.gpe"),

    // WEB: Se trata de un recurso web gestionado por la web ISTAC
    RECURSO_WEB("WEB", "dominio.origenrecurso.web"),

    // WEB: Se trata de un recurso web gestionado por la web ISTAC
    METAMAC_STAT_RESOURCES("MTM_STAT_RESOURCES", "dominio.origenrecurso.metamac.stat_resources"),

    OTROS("OTROS", "dominio.origenrecurso.otros"); // Para otros casos

    private String siglas;
    private String descripcion;

    public String getSiglas() {
        return siglas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    OrigenRecursoDomain(String siglasIN, String descripcionIN) {
        siglas = siglasIN;
        descripcion = descripcionIN;
    }

}
