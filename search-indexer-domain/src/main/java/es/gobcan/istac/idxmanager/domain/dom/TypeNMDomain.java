package es.gobcan.istac.idxmanager.domain.dom;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TypeNMDomain {

    // **********************
    // RECURSOS ESTADISTICOS
    // **********************
    DATASET_DSC("DSC", "dominio.nucleo.tipo.dsc", true), // DATASET > cubo Dataset DSC
    COLLECTION_DSP("DSP", "dominio.nucleo.tipo.dsp", true); // COLECCION > datos detallados Colecction DSP

    private String siglas;
    private String descripcion;
    private boolean isRecursoEstaditico;

    private static final Map<String, TypeNMDomain> LOOKUP_MAP = new HashMap<String, TypeNMDomain>();

    static {
        for (TypeNMDomain s : EnumSet.allOf(TypeNMDomain.class)) {
            String key = s.siglas.toLowerCase();
            LOOKUP_MAP.put(key, s);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSiglas() {
        return siglas;
    }

    public boolean isRecursoEstaditico() {
        return isRecursoEstaditico;
    }

    public static TypeNMDomain fromSiglas(String siglas) {
        if (siglas == null) {
            return null;
        }
        return LOOKUP_MAP.get(siglas.toLowerCase());
    }

    TypeNMDomain(String siglasIN, String descripcionIN, boolean isRecursoEstadisticoIN) {
        siglas = siglasIN;
        descripcion = descripcionIN;
        isRecursoEstaditico = isRecursoEstadisticoIN;
    }

}
