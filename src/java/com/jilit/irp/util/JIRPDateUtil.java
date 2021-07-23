/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ashok.singh
 */
public class JIRPDateUtil {

    public static Date getDateFromASObject(Map obj, String datePropertyName) {
        Date date = null;

        try {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (obj.containsKey(datePropertyName)) {
                if (!JIRPStringUtil.isNullorEmpty(obj.get(datePropertyName))) {
                    date = format.parse(obj.get(datePropertyName).toString());
                }
                obj.remove(datePropertyName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getUtilDateFromASObject(Map obj, String datePropertyName) {
        Date date = null;

        try {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (obj.containsKey(datePropertyName)) {
                if (!JIRPStringUtil.isNullorEmpty(obj.get(datePropertyName))) {
                    date = format.parse(obj.get(datePropertyName).toString());
                }
            //obj.remove(datePropertyName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date convertDateFormat(String indate, String split, String formatstr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String[] arr = indate.split(split);
        indate = arr[1] + formatstr + arr[2] + formatstr + arr[0];
        return format.parse(indate);
    }

    /**
     * Converts string date from MM/dd/yyyy format to dd/MM/yyyy format
     * @param indate: Should be in MM/dd/yyyy format
     * @return
     */
    public static String convertDateFormat(Date indate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(indate);
    }

    /**
     *
     * @author jaswinder.singh
     * Converts Date to String in specified pattern.
     * If pattern is null then default pattern MM/dd/yyyy HH:mm:ss is used
     *
     */
    public static String convertDateFormat(Date date, String pattern) {
        //"dd/MM/yyyy HH:mm:ss"
        if (pattern.equals("") || pattern.equals(null)) {
            pattern = "MM/dd/yyyy HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }//

    /**
     *
     * @author jaswinder.singh
     * Converts String to Date in specified pattern.
     * If pattern is null then default pattern MM/dd/yyyy HH:mm:ss is used
     */
    public static Date convertToDateFormat(String date, String pattern) throws ParseException {
        //"dd/MM/yyyy HH:mm:ss"
        if (pattern.equals(null) || pattern.equals("")) {
            pattern = "MM/dd/yyyy HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);
    }

    public static Date convertToDateFormat(String[] indate, String[] intime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String[] arr = indate[0].split("/");
        String[] arr1 = intime[0].split(":");
        // StringUtils.replace(arr1[1], "pm", "");
        //StringUtils.replace(arr1[1], "am", "");
        return format.parse(arr[1] + "/" + arr[0] + "/" + arr[2] + " " + arr1[0] + ":" + arr1[1] + ":" + arr1[2]);
    }

    // This function will return last Date of any given Date
    public static Date getLastDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        date = c.getTime();
        return date;
    }

    // This function will return first Date of any given Date
    public static Date getFirstDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        date = c.getTime();
        return date;
    }
    // This function will Add or Deduct Days from a given Date
    // For Additing use + noofdays ie. 3
    // For Deduction use + noofdays ie. -3

    public static Date addDate(Date date, int noofdays) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, noofdays);
        date = c.getTime();
        return date;
    }
    // function to add month in in a day and return date

    public static Date addMonth(Date date, int noOfMonths) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, noOfMonths);
        date = c.getTime();
        return date;
    }

    // This function will return last Date of any given Date
    public static String getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int d = c.get(Calendar.DAY_OF_WEEK);
        String day = "";
        switch (d) {
            case 1:
                day = "SUNDAY";
                break;
            case 2:
                day = "MONDAY";
                break;
            case 3:
                day = "TUESDAY";
                break;
            case 4:
                day = "WEDNESSDAY";
                break;
            case 5:
                day = "THURSDAY";
                break;
            case 6:
                day = "FRIDAY";
                break;
            case 7:
                day = "SATURDAY";
                break;
        }
        return day;
    }

    public static long getDaysDifference(Date todate, Date fromdate) {
        long days = 0;
        days = (todate.getTime() - fromdate.getTime()) / (long) 86400000;
        days = days + 1;
        return days;
    }
    /*
     * This Function returns Greatest Date among two dates...
     *
     */

    public static Date getGreatestDate(Date date1, Date date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            if (date1 == null && date2 != null) {
                return date2;
            }
            if (date1 != null && date2 == null) {
                return date1;
            }
            if (date1 == null && date2 == null) {
                return date1;
            }
            if (date1 != null && date2 != null) {
                if (Long.parseLong(format.format(date1).toString()) >= Long.parseLong(format.format(date2).toString())) {
                    return date1;
                } else {
                    return date2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * This Function returns Least Date among two dates...
     *
     */

    public static Date getLeastDate(Date date1, Date date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            if (date1 == null && date2 != null) {
                return date2;
            }
            if (date1 != null && date2 == null) {
                return date1;
            }
            if (date1 == null && date2 == null) {
                return date1;
            }
            if (date1 != null && date2 != null) {
                if (Long.parseLong(format.format(date1).toString()) <= Long.parseLong(format.format(date2).toString())) {
                    return date1;
                } else {
                    return date2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addYear(Date date, int noOfYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, noOfYear);
        date = c.getTime();
        return date;
    }

     /**
     * Description: Mehtod to convert date into DD/MM/YYYY format.
     * @param indate
     * @return
     * @throws flex.messaging.services.messaging.selector.ParseException
     */
    public static String convertExistingDateFormat(Date indate) throws ParseException {
        if (indate == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(indate);
        }

    }
}
