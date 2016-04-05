package es.gobcan.istac.search.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum SearchToolStripButtonEnum implements ValueEnum {

    //@formatter:off
    REINDEX("reindex_button"),
    RECOMMENDED_LINKS("recommended_links_button");
    //@formatter:on

    private String value;

    SearchToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
