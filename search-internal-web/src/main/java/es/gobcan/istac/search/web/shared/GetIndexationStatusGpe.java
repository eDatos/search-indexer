package es.gobcan.istac.search.web.shared;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

import es.gobcan.istac.search.core.dto.IndexationStatusDto;

@GenDispatch(isSecure = false)
public class GetIndexationStatusGpe {

    @Out(1)
    IndexationStatusDto indexationStatus;

}
