package es.gobcan.istac.search.core.mapper;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;

@Component
public class CategoriesRest2DoMapperImpl implements CategoriesRest2DoMapper {

    @Autowired
    private SearchConfigurationService searchConfigurationService;

    protected InternationalString toInternationalString(org.siemac.metamac.rest.common.v1_0.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setLabel(source.getValue());
            target.setLocale(source.getLang());
            target.setInternationalString(targets);
            targets.getTexts().add(target);
        }
        return targets;
    }

    @Override
    public ExternalItem toExternalItem(Category category) throws MetamacException {
        if (category == null) {
            return null;
        }
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(category.getId());
        externalItem.setCodeNested(category.getNestedId());
        externalItem.setUri(category.getSelfLink().getHref());
        externalItem.setUrn(category.getUrn());
        externalItem.setUrnProvider(category.getUrnProvider());
        externalItem.setType(TypeExternalArtefactsEnum.fromValue(category.getKind()));
        externalItem.setTitle(toInternationalString(category.getName()));
        externalItem.setManagementAppUrl(externalSrmItemManagementUrlRest2UrlDo(category.getManagementAppLink()));
        return externalItem;
    }

    private String externalSrmItemManagementUrlRest2UrlDo(String managementAppLink) throws MetamacException {
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(searchConfigurationService.retrieveSrmInternalWebApplicationUrlBase(), managementAppLink);
    }
}
