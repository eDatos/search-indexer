package es.gobcan.istac.idxmanager.domain.dom.client;

public enum IndexacionStatusDomain {

    PARADO("PARADO", "dominio.indexacion.status.parado"),
    INDEXANDO("INDEXANDO", "dominio.indexacion.status.indexando"),
    FALLO("FALLO", "dominio.indexacion.status.fallo");

    private String siglas;
    private String descripcion;

    public String getSiglas() {
        return siglas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    IndexacionStatusDomain(String siglasIN, String descripcionIN) {
        siglas = siglasIN;
        descripcion = descripcionIN;
    }

}
