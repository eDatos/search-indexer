package es.gobcan.istac.idxmanager.domain.mvc.enumerated;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FiltroSeccionEnum {

    // @formatter:off
    AREA_TEMATICA("SUBJECT_AREA", "Área temática"),
    OPERACION_ESTADISTICA("SURVEY", "Operación estadística");
    // @formatter:on

    private String id;
    private String field;

    private static final Map<String, FiltroSeccionEnum> LOOKUP_MAP_REVERSE = new HashMap<String, FiltroSeccionEnum>();
    private static final Map<String, FiltroSeccionEnum> LOOKUP_MAP = new HashMap<String, FiltroSeccionEnum>();

    static {
        for (FiltroSeccionEnum s : EnumSet.allOf(FiltroSeccionEnum.class)) {
            String key = s.field.toLowerCase();
            LOOKUP_MAP_REVERSE.put(key, s);
            LOOKUP_MAP.put(s.id, s);
        }
    }

    public static FiltroSeccionEnum fromId(String id) {
        return LOOKUP_MAP.get(id);
    }

    public static FiltroSeccionEnum fromField(String field) {
        return LOOKUP_MAP_REVERSE.get(field.toLowerCase());
    }

    private FiltroSeccionEnum(String id, String field) {
        this.id = id;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public String getId() {
        return id;
    }
}