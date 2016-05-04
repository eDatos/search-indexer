package es.gobcan.istac.search.web.client.widgets.form.fields.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItem;

import es.gobcan.istac.search.web.client.SearchWeb;

public abstract class SearchSrmCategoryLinkItem extends SearchSrmItemLinkItem {

    public SearchSrmCategoryLinkItem(String name, String title) {
        super(name, title, TypeExternalArtefactsEnum.CATEGORY_SCHEME, TypeExternalArtefactsEnum.CATEGORY, SearchWeb.getConstants().recommendedKeywordCategory(), "windowFilterListTitle",
                "windowFilterTextTitle", SearchWeb.getConstants().recommendedKeywordCategory());
    }

    @Override
    public void onSearch() {
        super.onSearch();
        getSearchWindow().getInitialFilterForm().hide();
        getSearchWindow().getFilterForm().hide();
    }
}
