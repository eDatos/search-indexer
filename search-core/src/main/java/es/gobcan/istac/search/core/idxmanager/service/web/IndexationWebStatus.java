package es.gobcan.istac.search.core.idxmanager.service.web;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.util.ISO8601DateFormat;

@Component
@Scope("singleton")
public class IndexationWebStatus implements Serializable {

    public static final String     BEAN_NAME          = "indexationWebStatus";

    private static final long      serialVersionUID   = 1L;

    // Reindexacion Web
    private IndexacionStatusDomain indexationStatus   = IndexacionStatusDomain.PARADO;
    private Date                   indexationLastDate = null;
    private static final String    NINGUNA            = "NINGUNA";

    public String getIndexationWebLastDate() {
        if (indexationLastDate == null) {
            return NINGUNA;
        }
        return ISO8601DateFormat.format(indexationLastDate);
    }

    public IndexacionStatusDomain getIndexationWebStatus() {
        return indexationStatus;
    }

    public String getIndexationWebStatusKey() {
        return indexationStatus.getSiglas();
    }

    public void setIndexationWebStatus(IndexacionStatusDomain indexationStatus) {
        this.indexationStatus = indexationStatus;
        indexationLastDate = new Date();
    }

}