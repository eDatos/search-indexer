package es.gobcan.istac.search.web.shared.recommendedlink;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class ExportRecommendedLinks {

    @Out(1)
    String fileName;
}
