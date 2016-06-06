package es.gobcan.istac.search.web.shared.recommendedlink;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

import es.gobcan.istac.search.core.dto.RecommendedLinkGroupedKeywordsDto;

@GenDispatch(isSecure = false)
public class CreateRecommendedLinks {

    @In(1)
    RecommendedLinkGroupedKeywordsDto recommendedLinkGroupedKeywordsDto;
}
