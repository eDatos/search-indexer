package es.gobcan.istac.search.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaPropertyEnum;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLinkProperties;

class RecommendedLinkCriteriaCallback implements CriteriaCallback {

    RecommendedLinkCriteriaCallback() {
    }

    @Override
    public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
        RecommendedLinkCriteriaPropertyEnum propertyEnum = RecommendedLinkCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
        switch (propertyEnum) {
            case KEYWORD:
                return new SculptorPropertyCriteria(RecommendedLinkProperties.recommendedKeyword().keyword(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
            case CATEGORY:
                return new SculptorPropertyCriteria(RecommendedLinkProperties.recommendedKeyword().category().title().texts().label(), propertyRestriction.getStringValue(),
                        propertyRestriction.getOperationType());
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<RecommendedLink> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
        RecommendedLinkCriteriaOrderEnum criteriaOrderEnum = RecommendedLinkCriteriaOrderEnum.fromValue(order.getPropertyName());
        switch (criteriaOrderEnum) {
            case CREATED_DATE:
                return CriteriaUtils.getDatetimeLeafPropertyEmbedded(RecommendedLinkProperties.createdDate(), RecommendedLink.class);
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<RecommendedLink> retrievePropertyOrderDefault() throws MetamacException {
        return CriteriaUtils.getDatetimeLeafPropertyEmbedded(RecommendedLinkProperties.createdDate(), RecommendedLink.class);
    }
}