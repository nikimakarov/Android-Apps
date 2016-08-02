package ru.surf.nikita_makarov.githubtrends.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class DateFormatter {

    private static final String zeroString = "0";
    private static final String emptyString = "";
    private static final String todayString = "today";
    private static final String weekString = "week";
    private static final String monthString = "month";
    private static final String yearString = "year";
    private static final String dashString = "-";
    private static final String allTimeDateString = "2000-01-01";
    private static final String allTimeString = "all time";
    private static final String createdQString = "created:>=";
    private static final String languageQString = "+language:";

    private DateFormatter(){

    }

    public static String makeParamsForQuery(String date, String language) {
        return createdQString + dateInterval(date) + languageQString + language;
    }

    public static String dateInterval(String dateFilter) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String zero1 = month < 10 ? zeroString : emptyString;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String zero2 = day < 10 ? zeroString : emptyString;
        switch (dateFilter) {
            case todayString:
                return (year + dashString + zero1 + month + dashString + zero2 + day);
            case weekString:
                return (year + dashString + zero1 + month + dashString + zero2 + (day - 7));
            case monthString:
                return (year + dashString + zero1 + (month - 1) + dashString + zero2 + day);
            case yearString:
                return ((year - 1) + dashString + zero1 + month + dashString + zero2 + day);
            case allTimeString:
                return allTimeDateString;
            default:
                return (year + dashString + zero1 + month + dashString + zero2 + day);
        }
    }

}
