package es.gobcan.istac.search.core.dto;

import java.io.Serializable;

public class IndexationStatusDto implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private String            statusKey;
    private String            lastExecutionDate;

    public IndexationStatusDto() {
    }

    public IndexationStatusDto(String statusKey, String lastDate) {
        this();
        setStatusKey(statusKey);
        setLastExecutionDate(lastDate);
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getLastExecutionDate() {
        return lastExecutionDate;
    }

    public void setLastExecutionDate(String lastDate) {
        this.lastExecutionDate = lastDate;
    }

}