package es.gobcan.istac.search.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.LeafProperty;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaOrderEnum;
import es.gobcan.istac.search.core.criteria.RecommendedLinkCriteriaPropertyEnum;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLinkProperties;

@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<RecommendedLink> criteriaMapper = null;

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        criteriaMapper = new MetamacCriteria2SculptorCriteria<RecommendedLink>(RecommendedLink.class, RecommendedLinkCriteriaOrderEnum.class, RecommendedLinkCriteriaPropertyEnum.class,
                new RecommendedLinkCriteriaCallback());
    }

    @Override
    public MetamacCriteria2SculptorCriteria<RecommendedLink> getRecommendedLinkCriteriaMapper() {
        return criteriaMapper;
    }

    private class RecommendedLinkCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            RecommendedLinkCriteriaPropertyEnum propertyEnum = RecommendedLinkCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case KEYWORD:
                    return new SculptorPropertyCriteria(RecommendedLinkProperties.recommendedKeyword().keyword(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                // case SUBJECT:
                // return new SculptorPropertyCriteria(RecommendedLinkProperties.recommendedKeyword().subject(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
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
                    return getDatetimeLeafPropertyEmbedded(RecommendedLinkProperties.createdDate(), RecommendedLink.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Property<RecommendedLink> retrievePropertyOrderDefault() throws MetamacException {
            return getDatetimeLeafPropertyEmbedded(RecommendedLinkProperties.createdDate(), RecommendedLink.class);
        }
    }

    @SuppressWarnings("rawtypes")
    private LeafProperty getDatetimeLeafPropertyEmbedded(Property property, Class entityClass) {
        return CriteriaUtils.getDatetimeLeafPropertyEmbedded(property, entityClass);
    }
}
