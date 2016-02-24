package es.gobcan.istac.search.web.client.widgets;

import static es.gobcan.istac.search.web.client.SearchWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.MasterHead;

public class SearchMasterHead extends MasterHead {

    public SearchMasterHead() {
        super();
        setTitleLabel(getConstants().appTitle());
    }
}
