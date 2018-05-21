package com.westerngun.Chatty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class Util {
    static String dateDiff(LocalDateTime date1, LocalDateTime date2) {
        Duration secondsDuration = Duration.between(date1, date2);
        long seconds = secondsDuration.getSeconds();
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
    
    /**
     * Extract the tweet content from the user input line.
     * @param input the line user has entered.
     * @return the real content of tweet to publish.
     */
    protected static String getContentFromInput(String input) {
        return input.substring(input.indexOf("->") + 3, input.length());
    }
}
