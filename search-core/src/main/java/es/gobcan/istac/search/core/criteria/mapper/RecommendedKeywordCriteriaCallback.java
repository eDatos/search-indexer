package es.gobcan.istac.search.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.criteria.RecommendedKeywordCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedKeywordCriteriaPropertyEnum;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeywordProperties;

class RecommendedKeywordCriteriaCallback implements CriteriaCallback {

    RecommendedKeywordCriteriaCallback() {
    }

    @Override
    public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
        RecommendedKeywordCriteriaPropertyEnum propertyEnum = RecommendedKeywordCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
        switch (propertyEnum) {
            case KEYWORD:
                return new SculptorPropertyCriteria(RecommendedKeywordProperties.keyword(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
            case CATEGORY:
                return new SculptorPropertyCriteria(RecommendedKeywordProperties.category().title().texts().label(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<RecommendedKeyword> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
        RecommendedKeywordCriteriaOrderEnum criteriaOrderEnum = RecommendedKeywordCriteriaOrderEnum.fromValue(order.getPropertyName());
        switch (criteriaOrderEnum) {
            case KEYWORD:
                return RecommendedKeywordProperties.keyword();
            case CREATED_DATE:
                return CriteriaUtils.getDatetimeLeafPropertyEmbedded(RecommendedKeywordProperties.createdDate(), RecommendedKeyword.class);
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<RecommendedKeyword> retrievePropertyOrderDefault() throws MetamacException {
        return CriteriaUtils.getDatetimeLeafPropertyEmbedded(RecommendedKeywordProperties.createdDate(), RecommendedKeyword.class);
    }
}