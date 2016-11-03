package es.gobcan.istac.idxmanager.domain.mvc.enumerated;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FiltroTextoEnum {

    // @formatter:off
    ALGUNA_DE_LAS_PALABRAS("SOME_WORDS", "Con alguna de las palabras"),
    FRASE_EXACTA("EXACT_PHRASE", "Con la frase exacta"),
    TODAS_LAS_PALABRAS("ALL_WORDS", "Con todas las palabras");
    // @formatter:on

    private String id;
    private String field;

    private static final Map<String, FiltroTextoEnum> LOOKUP_MAP_REVERSE = new HashMap<String, FiltroTextoEnum>();
    private static final Map<String, FiltroTextoEnum> LOOKUP_MAP = new HashMap<String, FiltroTextoEnum>();

    static {
        for (FiltroTextoEnum s : EnumSet.allOf(FiltroTextoEnum.class)) {
            String key = s.field.toLowerCase();
            LOOKUP_MAP_REVERSE.put(key, s);
            LOOKUP_MAP.put(s.id, s);
        }
    }

    public static FiltroTextoEnum fromId(String id) {
        return LOOKUP_MAP.get(id);
    }

    public static FiltroTextoEnum fromField(String field) {
        return LOOKUP_MAP_REVERSE.get(field.toLowerCase());
    }

    private FiltroTextoEnum(String id, String field) {
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