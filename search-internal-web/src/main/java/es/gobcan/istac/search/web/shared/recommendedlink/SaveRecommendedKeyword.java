package es.gobcan.istac.search.web.shared.recommendedlink;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;

@GenDispatch(isSecure = false)
public class SaveRecommendedKeyword {

    @In(1)
    RecommendedKeywordDto recommendedKeyword;
}
