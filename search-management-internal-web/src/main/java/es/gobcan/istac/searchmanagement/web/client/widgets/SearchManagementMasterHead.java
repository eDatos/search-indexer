package es.gobcan.istac.searchmanagement.web.client.widgets;

import static es.gobcan.istac.searchmanagement.web.client.SearchManagementWeb.getConstants;

import org.siemac.metamac.web.common.client.widgets.MasterHead;

public class SearchManagementMasterHead extends MasterHead {

    public SearchManagementMasterHead() {
        super();
        setTitleLabel(getConstants().appTitle());
    }
}
