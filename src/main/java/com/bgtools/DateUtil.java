package com.bgtools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static String year = "";
    private static String month = "";
    private static String day = "";
    private static String hours = "";

    public static String getLogDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.toString();
    }

    public static String getDate(String date) {
        String inPutDate = formateDate(date);
        LocalDate localDate = LocalDate.parse(inPutDate);
        localDate = localDate.minusDays(1L);
        return localDate.toString().replaceAll("-","");
    }

    public static Boolean validatehours(String date) {
        LocalTime localDate = LocalTime.parse(date + ":00:00");
        int hour = localDate.getHour();

        if (hour >= 12) {
            return true;
        } else {
            return false;
        }
    }

    public static String formateDate(String date) {
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6, 8);
        String dateStr = year + "-" + month + "-" + day;
        return dateStr;
    }

    public static Boolean validateDate(String date) {
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6, 8);
        return validateDate(year, month, day, 7);
    }

    public static Boolean validateDate(String year, String month, String day, int deadline) {
        String dateStr = year + "-" + month + "-" + day;
        LocalDate now = LocalDate.now();
        LocalDate localDate = LocalDate.parse(dateStr);
        long day1 = now.toEpochDay();
        long day2 = localDate.toEpochDay();

        if (Math.abs(day2 - day1) > deadline) {
            return false;
        } else {
            return true;
        }
    }

    public static Boolean isToday(String date) {
        String dateStr = formateDate(date);
        LocalDate now = LocalDate.now();
        LocalDate localDate = LocalDate.parse(dateStr);
        long day1 = now.toEpochDay();
        long day2 = localDate.toEpochDay();
        if (day1 == day2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkDate(String sourceDate) {
        if (sourceDate == null) {
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;

    }


    public static void main(String[] args) {

        String orderNo = "230109050246095463";
        String dataStr = 20 + orderNo.substring(0, 6);
        System.out.println("[ dataStr ] : " + dataStr);
        System.out.println("[ getDate() ] : " + getDate(dataStr));
    }
}