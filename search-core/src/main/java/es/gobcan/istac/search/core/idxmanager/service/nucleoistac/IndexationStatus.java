package es.gobcan.istac.idxmanager.service.nucleoistac;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gobcan.istac.idxmanager.domain.dom.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.util.ISO8601DateFormat;

@Component
@Scope("singleton")
public class IndexationStatus implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = -2486128655153455672L;

    // Reindexacion GPE
    private IndexacionStatusDomain idxGPEStatus = IndexacionStatusDomain.PARADO;
    private Date idxGPELastDate = null;
    private static final String NINGUNA = "NINGUNA";

    /**************************************************************************
     * GETTER/SETTER
     *************************************************************************/
    public String getIdxGPEStatusSiglas() {
        return idxGPEStatus.getSiglas();
    }
    public void setIdxGPEStatus(IndexacionStatusDomain idxGPEStatus) {
        this.idxGPEStatus = idxGPEStatus;
        idxGPELastDate = new Date();
    }
    public String getIdxGPELastDate() {
        if (idxGPELastDate == null) {
            return NINGUNA;
        }
        return ISO8601DateFormat.format(idxGPELastDate);
    }
    public IndexacionStatusDomain getIdxGPEStatus() {
        return idxGPEStatus;
    }

}
