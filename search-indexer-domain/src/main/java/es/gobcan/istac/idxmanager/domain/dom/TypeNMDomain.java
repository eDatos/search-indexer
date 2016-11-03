package es.gobcan.istac.idxmanager.domain.dom;

public enum TypeNMDomain {

    // **********************
    // RECURSOS ESTADISTICOS
    // **********************
    DATASET_DSC("DSC", "dominio.nucleo.tipo.dsc", true), // DATASET > cubo Dataset DSC
    COLLECTION_DSP("DSP", "dominio.nucleo.tipo.dsp", true); // COLECCION > datos detallados Colecction DSP

    private String siglas;
    private String descripcion;
    private boolean isRecursoEstaditico;

    public String getDescripcion() {
        return descripcion;
    }

    public String getSiglas() {
        return siglas;
    }

    public boolean isRecursoEstaditico() {
        return isRecursoEstaditico;
    }

    TypeNMDomain(String siglasIN, String descripcionIN, boolean isRecursoEstadisticoIN) {
        siglas = siglasIN;
        descripcion = descripcionIN;
        isRecursoEstaditico = isRecursoEstadisticoIN;
    }

}
