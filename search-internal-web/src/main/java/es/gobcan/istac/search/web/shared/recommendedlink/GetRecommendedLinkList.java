package es.gobcan.istac.search.web.shared.recommendedlink;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

@GenDispatch(isSecure = false)
public class GetRecommendedLinkList {

    @In(1)
    RecommendedLinkWebCriteria criteria;

    @Out(1)
    List<RecommendedLinkDto>   recommendedLinkList;

    @Out(2)
    Integer                    firstResult;

    @Out(3)
    Integer                    totalResults;
}
