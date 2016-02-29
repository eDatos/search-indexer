package es.gobcan.istac.search.web.client.model.ds;

import com.smartgwt.client.data.DataSource;

public class ReindexDS extends DataSource {

    public static String GENERAL_DESCRIPTION             = "reindex-gen-desc";

    public static String GPE_DESCRIPTION                 = "reindex-gpe-desc";
    public static String GPE_CRON_EXPRESSION             = "reindex-gpe-cron-exp";
    public static String GPE_STATUS                      = "reindex-gpe-status";
    public static String GPE_LAST_EXECUTION_SINCE_REBOOT = "reindex-gpe-last-exec";

    public static String WEB_DESCRIPTION                 = "reindex-web-desc";
    public static String WEB_CRON_EXPRESSION             = "reindex-web-cron-exp";
    public static String WEB_STATUS                      = "reindex-web-status";
    public static String WEB_LAST_EXECUTION_SINCE_REBOOT = "reindex-web-last-exec";

    public ReindexDS() {
        setClientOnly(true);
    }

}
