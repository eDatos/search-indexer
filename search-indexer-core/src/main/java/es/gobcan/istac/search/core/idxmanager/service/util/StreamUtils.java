package es.gobcan.istac.search.core.idxmanager.service.util;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

public class StreamUtils {

    public static DateTime avro2Do(DatetimeAvro source) {
        DateTime target = null;
        if (source != null) {
            target = new DateTime(source.getInstant());
        }
        return target;
    }

}
