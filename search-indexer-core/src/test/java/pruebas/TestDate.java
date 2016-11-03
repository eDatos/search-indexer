package pruebas;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.text.SimpleDateFormat;

public class TestDate {

    public static void main(String[] args) throws ParseException {

        // SimpleDateFormat that works exactly like Date.toString()
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date today = new Date();

        String dateAsString_format = simpleDateFormat.format(today);
        String dateAsString_native = today.toString();
        // Both strings are: "Thu Nov 12 18:49:36 GMT 2009"

        Date parsedDate = simpleDateFormat.parse(dateAsString_native);
        System.out.println(parsedDate.toString());
        // Result: "Thu Nov 12 18:49:36 GMT 2009"

        parsedDate = simpleDateFormat.parse("Fri Sep 10 07:57:35 GMT 2010");
        System.out.println(parsedDate.toString());

        String[] fechasPattern = {"E MMM dd HH:mm:ss zzz yyyy"};
        System.out.println(DateUtils.parseDate("Fri Sep 10 07:57:35 GMT 2010", fechasPattern).toString());

    }
}
