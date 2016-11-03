package es.gobcan.istac.search.web.shared.recommendedlink;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;

@GenDispatch(isSecure = false)
public class SaveRecommendedLink {

    @In(1)
    RecommendedLinkDto recommendedLink;
}
