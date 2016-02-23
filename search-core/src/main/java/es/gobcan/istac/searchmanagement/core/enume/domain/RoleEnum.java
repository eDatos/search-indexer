package es.gobcan.istac.searchmanagement.core.enume.domain;

import java.io.Serializable;

public enum RoleEnum implements Serializable {
    ADMINISTRADOR, ANY_ROLE_ALLOWED;

    private RoleEnum() {
    }

    public String getName() {
        return name();
    }
}
