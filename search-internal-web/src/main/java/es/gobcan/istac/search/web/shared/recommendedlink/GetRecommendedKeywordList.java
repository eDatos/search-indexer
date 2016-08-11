package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.web.shared.criteria.RecommendedKeywordWebCriteria;

@GenDispatch(isSecure = false)
public class GetRecommendedKeywordList {

    @In(1)
    RecommendedKeywordWebCriteria criteria;

    @Out(1)
    List<RecommendedKeywordDto>   recommendedKeywordList;

    @Out(2)
    Integer                       firstResult;

    @Out(3)
    Integer                       totalResults;
}
