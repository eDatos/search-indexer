package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class ExportRecommendedLinksList {

    @In(1)
    List<Long> recommendedLinkIds;

    @Out(1)
    String     fileName;
}
