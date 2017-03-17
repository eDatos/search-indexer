package es.gobcan.istac.search.core.enume.domain;

import java.io.Serializable;

public enum RoleEnum implements Serializable {
    ADMINISTRADOR, LECTOR, ANY_ROLE_ALLOWED;

    private RoleEnum() {
    }

    public String getName() {
        return name();
    }
}
