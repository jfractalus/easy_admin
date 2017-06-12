package com.volia.eadmin.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private final static String dd_MM_yyyy_HH_mm_ss = "dd-MM-yyyy HH:mm:ss";
    private final static String dd_MM_yyyy = "dd.MM.yyyy";

    private DateTimeUtil(){}

    public static DateTime to_dd_MM_yyyy_hh_mm_ss(String source){
        return DateTime.parse(source, getTimeFormatter(dd_MM_yyyy_HH_mm_ss));
    }

    public static String to_dd_MM_yyyy_hh_mm_ss(DateTime source){
        return getTimeFormatter(dd_MM_yyyy_HH_mm_ss).print(source);
    }

    public static DateTime to_yyyy_MM_dd_HH_mm_ss(String source){
        return DateTime.parse(source, getTimeFormatter(yyyy_MM_dd_HH_mm_ss));
    }

    public static String to_yyyy_MM_dd_HH_mm_ss(DateTime source){
        return getTimeFormatter(yyyy_MM_dd_HH_mm_ss).print(source);
    }

    public static String printDateTimeByPattern(DateTime source, String pattern){
        return source!=null ? getTimeFormatter(pattern).print(source) : "";
    }

    public static String printLocalDateByPattern(LocalDate source, String pattern){
        return source!=null ? getTimeFormatter(pattern).print(source) : "";
    }

    public static LocalDate to_dd_MM_yyyy(String source){
        return LocalDate.parse(source, getTimeFormatter(dd_MM_yyyy));
    }

    private static DateTimeFormatter getTimeFormatter(String pattern){
        return DateTimeFormat.forPattern(pattern).withZoneUTC();
    }

    public static DateTime dateTimeNow(){
        return DateTime.now();
    }
}
