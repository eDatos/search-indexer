package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;

@GenDispatch(isSecure = false)
public class GetRecommendedKeywordList {

    @Out(1)
    List<RecommendedKeywordDto> recommendedKeywordList;
}
