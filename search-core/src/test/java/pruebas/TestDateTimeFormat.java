package pruebas;

import java.text.FieldPosition;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;

public class TestDateTimeFormat {

    public void run() {

        // Formatting Dates
        DateFormat dfUS = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        // DateFormat dfSpain = DateFormat.getDateInstance(DateFormat.FULL, Locale.);
        // DateFormat dfFrance = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);

        // StringBuffer sb = new StringBuffer();
        // Calendar c = Calendar.getInstance();
        // Date d = c.getTime();
        // sb = dfUS.format(d, sb, new FieldPosition(0));
        // System.out.println(sb.toString());
        //
        // StringBuffer sbf = new StringBuffer();
        // sbf = dfFrance.format(d, sbf, new FieldPosition(0));
        // System.out.println(sbf.toString());
        //
        // StringBuffer sbg = new StringBuffer();
        // DateFormat dfg = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT);
        // FieldPosition pos = new FieldPosition(DateFormat.MINUTE_FIELD);
        // sbg = dfg.format(d, sbg, pos);
        // System.out.println(sbg.toString());
        // System.out.println(sbg.toString().substring(pos.getBeginIndex(), pos.getEndIndex()));

        // Parsing Dates
        String dateString_US = "Thursday, February 7, 2008";
        String dateString_FRANCE = "jeudi 7 f�vrier 2008";
        String dateCustom = "Fri Sep 10 07:57:35 GMT 2010";

        // UnicodeString aPattern("GyyyyMMddHHmmssSSZ", "");
        SimpleDateFormat aa = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", new DateFormatSymbols(Locale.US));

        try {
            Date parsedDate_US = dfUS.parse(dateString_US);
            // Date parsedDate_FRANCE = dfFrance.parse(dateString_FRANCE);
            System.out.println(parsedDate_US.toString());
            // System.out.println(parsedDate_FRANCE.toString());
            // Date parsedDate_CUSTOM = dfUS.parse(dateCustom);
            // System.out.println(parsedDate_CUSTOM.toString());

            System.out.println(aa.parse(dateCustom).toString());

        } catch (ParseException pe) {
            System.out.println("Exception while parsing :" + pe);
        }
    }

    public static void main(String args[]) {
        new TestDateTimeFormat().run();
    }
}