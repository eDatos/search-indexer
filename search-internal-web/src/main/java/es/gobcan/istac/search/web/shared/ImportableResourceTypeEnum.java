package es.gobcan.istac.search.web.shared;

import java.io.Serializable;

public enum ImportableResourceTypeEnum implements Serializable {
    RECOMMENDED_LINKS;

    private ImportableResourceTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
