package es.gobcan.istac.search.web.client.utils;

import es.gobcan.istac.search.web.client.SearchWeb;

public class CoreMessagesUtils {

    CoreMessagesUtils() {

    }

    public static String getCoreMessage(String dottedKey) {
        return SearchWeb.getCoreMessages().getString(dottedKey.replace(".", "_"));
    }

}
