package ssl.ois.timelog.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlDateTimeConverter {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private SqlDateTimeConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static String toString(Date date) {
        return sdf.format(date);
    }

    public static Date toDate(String dateString) throws ParseException {
        return sdf.parse(dateString);
    }
}
