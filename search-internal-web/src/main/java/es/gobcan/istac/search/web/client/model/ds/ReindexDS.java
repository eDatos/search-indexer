package es.gobcan.istac.search.web.client.model.ds;

import com.smartgwt.client.data.DataSource;

public class ReindexDS extends DataSource {

    public static String CRON_EXPRESSION             = "reindex-cron-exp";
    public static String STATUS                      = "reindex-status";
    public static String LAST_EXECUTION_SINCE_REBOOT = "reindex-last-exec";

    public ReindexDS() {
        setClientOnly(true);
    }

}
