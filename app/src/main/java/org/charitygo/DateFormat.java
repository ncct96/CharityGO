package org.charitygo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    public int longToYearMonth(long timestamp) {

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;
    }

    public int longToYearMonthDay(long timestamp) {

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return (cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1)  * 100) + cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getLastDayofMonth(long timestamp) {

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return (cal.get(Calendar.YEAR) * 10000) + (cal.get(Calendar.MONTH) + 1 * 100) + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getFirstDayofMonth(long timestamp) {

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        return (cal.get(Calendar.YEAR) * 10000) + (cal.get(Calendar.MONTH) + 1 * 100) + cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    public String getMonthString(long timestamp){
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp);

        String monthName = new SimpleDateFormat("MMMM").format(cal.getTime());

        return monthName;
    }

    public boolean checkSameMonth(int dateWithMonth, int dateWithMonthDay){
        int tempDate = dateWithMonthDay / 100;

        if(dateWithMonth == tempDate){
            return true;
        }else{
            return false;
        }
    }
}
