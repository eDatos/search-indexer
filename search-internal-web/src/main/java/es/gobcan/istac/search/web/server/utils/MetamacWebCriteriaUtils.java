package es.gobcan.istac.search.web.server.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaPropertyEnum;
import es.gobcan.istac.search.web.shared.criteria.RecommendedLinkWebCriteria;

public class MetamacWebCriteriaUtils {

    public static MetamacCriteria build(RecommendedLinkWebCriteria recommendedLinkWebCriteria) throws MetamacException {
        MetamacCriteria criteria = new MetamacCriteria();
        // Criteria
        MetamacCriteriaConjunctionRestriction restriction = new MetamacCriteriaConjunctionRestriction();
        restriction.getRestrictions().add(buildRecommendedLinkCriteriaRestriction(recommendedLinkWebCriteria));
        criteria.setRestriction(restriction);

        // Order
        MetamacCriteriaOrder criteriaOrder = new MetamacCriteriaOrder();
        criteriaOrder.setPropertyName(RecommendedLinkCriteriaOrderEnum.CREATED_DATE.name());
        criteriaOrder.setType(OrderTypeEnum.DESC);
        criteria.getOrdersBy().add(criteriaOrder);

        // Pagination
        criteria.setPaginator(new MetamacCriteriaPaginator());
        criteria.getPaginator().setFirstResult(recommendedLinkWebCriteria.getFirstResult());
        criteria.getPaginator().setMaximumResultSize(recommendedLinkWebCriteria.getMaxResults());
        criteria.getPaginator().setCountTotalResults(true);

        return criteria;
    }

    public static MetamacCriteriaRestriction buildRecommendedLinkCriteriaRestriction(RecommendedLinkWebCriteria criteria) throws MetamacException {

        MetamacCriteriaConjunctionRestriction conjunctionRestriction = new MetamacCriteriaConjunctionRestriction();

        if (criteria != null) {

            // General criteria
            MetamacCriteriaDisjunctionRestriction searchCriteriaDisjunction = new MetamacCriteriaDisjunctionRestriction();
            if (StringUtils.isNotBlank(criteria.getCriteria())) {
                searchCriteriaDisjunction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(RecommendedLinkCriteriaPropertyEnum.KEYWORD.name(), criteria.getCriteria(), OperationType.ILIKE));
                // searchCriteriaDisjunction.getRestrictions()
                // .add(new MetamacCriteriaPropertyRestriction(RecommendedLinkCriteriaPropertyEnum.SUBJECT.name(), criteria.getCriteria(), OperationType.ILIKE));
            }
            conjunctionRestriction.getRestrictions().add(searchCriteriaDisjunction);

        }

        return conjunctionRestriction;
    }
}
