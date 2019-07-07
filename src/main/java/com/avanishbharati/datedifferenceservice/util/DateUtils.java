package com.avanishbharati.datedifferenceservice.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DateUtils {

    private static final Map<Integer,Integer> MONTH_DAYS = numberMonthDays();
    private static final Map<Integer,Integer> FEB_DAYS_LEAP_YEAR = numberFebDaysLeap();


    public static Map<Integer,Integer> getLeapFebDays(){
        return FEB_DAYS_LEAP_YEAR;
    }
    public static Map<Integer,Integer> getMonthDays(){
        return MONTH_DAYS;
    }

    /***
     * Find if the year is a leap year
     * @param valueOfYear
     * @return true if the a leap year
     */
    public static Boolean isLeapYear( int valueOfYear){
        return  ((valueOfYear % 4 == 0) && (valueOfYear % 100 != 0)) || (valueOfYear % 400 == 0);
    }

    private DateUtils(){}

    private static Map<Integer,Integer> numberMonthDays(){
        Map<Integer,Integer> map = new HashMap<>();

        map.put(1,31);
        map.put(2,28);
        map.put(3,31);
        map.put(4,30);
        map.put(5,31);
        map.put(6,30);
        map.put(7,31);
        map.put(8,31);
        map.put(9,30);
        map.put(10,31);
        map.put(11,30);
        map.put(12,31);
        return Collections.unmodifiableMap(map);

    }

    private static Map<Integer,Integer> numberFebDaysLeap(){
        Map<Integer,Integer> map = new HashMap<>();
        map.put(2,29);
        return Collections.unmodifiableMap(map);
    }
}
