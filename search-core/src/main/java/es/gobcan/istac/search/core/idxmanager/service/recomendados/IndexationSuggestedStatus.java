package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gobcan.istac.idxmanager.domain.dom.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.util.ISO8601DateFormat;

@Component
@Scope("singleton")
public class IndexationSuggestedStatus implements Serializable {

    /**
     * 
     */
    private static final long      serialVersionUID     = -2486128655153455672L;

    // Reindexacion GPE
    private IndexacionStatusDomain idxSuggestedStatus   = IndexacionStatusDomain.PARADO;
    private Date                   idxSuggestedLastDate = null;
    private static final String    NINGUNA              = "NINGUNA";

    /**************************************************************************
     * GETTER/SETTER
     *************************************************************************/
    public String getIdxSuggestedStatusSiglas() {
        return idxSuggestedStatus.getSiglas();
    }
    public void setIdxSuggestedStatus(IndexacionStatusDomain idxStatus) {
        idxSuggestedStatus = idxStatus;
        idxSuggestedLastDate = new Date();
    }
    public String getIdxSuggestedLastDate() {
        if (idxSuggestedLastDate == null) {
            return NINGUNA;
        }
        return ISO8601DateFormat.format(idxSuggestedLastDate);
    }
    public IndexacionStatusDomain getIdxSuggestedStatus() {
        return idxSuggestedStatus;
    }

}