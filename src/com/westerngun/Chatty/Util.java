package com.westerngun.Chatty;

import java.time.LocalDate;
import java.util.Date;

public class Util {
    static String dateDiff(Date date1, Date date2) {
        long seconds = date2.getTime() - date1.getTime();
        if (0 <= seconds && seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds <= 3600){
            int mins = (int)seconds / 60;
            return mins + " minutes ago";
        } else if (seconds <= 86400) {
            int hours = (int)seconds / 3600;
            return hours + " hours ago";
        } else {
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            String month = localDate.getMonth().toString();
            int day = localDate.getDayOfMonth();
            return year + "/" + month.substring(0, 3) + "/" + toTwoDigits(day);
        }
    }
    
    private static String toTwoDigits(int day) {
        if (1 <= day && day <= 9) {
            return "0" + day;
        } else {
            return Integer.toString(day);
        }
    }
}
