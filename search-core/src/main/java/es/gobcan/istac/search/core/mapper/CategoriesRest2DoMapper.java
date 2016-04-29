package es.gobcan.istac.search.core.mapper;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;

public interface CategoriesRest2DoMapper {

    ExternalItem toExternalItem(Category category) throws MetamacException;

}
