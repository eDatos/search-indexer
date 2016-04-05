package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteRecommendedLinkList {

    @In(1)
    List<Long> recommendedLinkIds;
}
