package org.charitygo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    public int longToYearMonth(long timestamp){

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH)+1;
    }

    public int longToYearMonthDay(long timestamp){

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return (cal.get(Calendar.YEAR) * 10000) + (cal.get(Calendar.MONTH)+1 * 100) + cal.get(Calendar.DAY_OF_MONTH);
    }
}
