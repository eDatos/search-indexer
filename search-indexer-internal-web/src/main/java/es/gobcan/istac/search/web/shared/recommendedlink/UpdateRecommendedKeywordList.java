package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;

@GenDispatch(isSecure = false)
public class UpdateRecommendedKeywordList {

    @In(1)
    List<RecommendedKeywordDto> recommendedKeywordList;
}
