package com.jilit.irp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JIRPStringUtil {

    public static boolean isNullorEmpty(Object obj) {
        return (obj == null || "".equals(obj)) ? true : false;
    }

    public static boolean isNullorEmpty(List obj) {
        if (obj == null) {
            return true;
        }
        if (obj.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullAndEqual(String str, String equalto) {
        if (str == null) {
            return false;
        }
        if (str.equals(equalto)) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullAndEqual(Character chr, char equalto) {
        if (chr == null) {
            return false;
        }
        if (chr.equals(equalto)) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEqual(String str, String equalto) {
        if (str == null) {
            return true;
        }
        if (str.equals(equalto)) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEqual(Character chr, char equalto) {
        if (chr == null) {
            return true;
        }
        if (chr.equals(equalto)) {
            return true;
        }
        return false;
    }

    public static String getFieldErrorString(Map<String, List<String>> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        sb.append("/* { ");

        sb.append("\"fieldErrors\": {");
        for (Map.Entry<String, List<String>> fieldError : fieldErrors.entrySet()) {
            sb.append("\"");

            sb.append(fieldError.getKey());
            sb.append("\":");
            sb.append(buildArray(fieldError.getValue()));
            sb.append(",");
        }
        //remove trailing comma, IE creates an empty object, duh
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        sb.append("} */");
        return sb.toString();
    }

    private static String buildArray(Collection<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String value : values) {
            sb.append("\"");
            sb.append(value);
            sb.append("\",");
        }
        if (values.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    private static String buildArray(String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        sb.append("\"");
        sb.append(value);
        sb.append("\"");


        sb.append("]");
        return sb.toString();
    }

    public static boolean containsRestrictedWord(String str) {
        ArrayList list = new ArrayList();
        String word;
        String upperStr = str.toUpperCase();
        //Add restricted words here
        list.add("create");
        list.add("alter");
        list.add("drop");
        list.add("select");
        list.add("update");
        list.add("insert");
        list.add("delete");
        list.add("truncate");
        //Checking if word exists in the list
        Iterator it = list.iterator();
        while (it.hasNext()) {
            word = (String) it.next();
            if (upperStr.indexOf(word.toUpperCase()) >= 0) {
                return true;
            }
        }

        return false;
    }

    public static String changeArraytoString(String[] strarr) {
        String retStr = "";
        if (strarr != null) {
            for (int i = 0; i < strarr.length; i++) {
                retStr = retStr + strarr[i];
            }
        }
        return retStr;
    }

    public static String[] changeStringtoArray(String str) {
        if (isNullorEmpty(str)) {
            String[] retStr = new String[str.length()];

            for (int i = 0; i < str.length(); i++) {
                retStr[i] = str.substring(i, i + 1);
            }

            return retStr;
        } else {
            return null;
        }
    }

    public static Long nvl(Long value) {
        if (value == null) {
            return new Long(0);
        }
        return value;
    }

    public static Short nvl(Short value) {
        if (value == null) {
            return new Short("0");
        }
        return value;
    }

    public static String getQuerySubstring(String[] strings) {

        String retString = "";
        String delim = "'";
        for (int i = 0; i < strings.length; i++) {
            retString = retString + delim + strings[i];
            delim = "','";
        }
        return (retString + "'");
    }

    public static String getQuerySubstring(List arrayList) {

        String retString = "";
        String delim = "'";
        for (int i = 0; i < arrayList.size(); i++) {
            retString = retString + delim + arrayList.get(i);
            delim = "','";
        }
        return (retString + "'");

    }

    public static String getQuerySubstringNew(List arrayList) {

        String retString = "";
        String delim = ",";
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 0) {
                retString = retString + arrayList.get(i);
            } else {
                retString = retString + delim + arrayList.get(i);
            }

        }
        return (retString);

    }

    public static Object[] toObjectArr(String str) {
        String[] arr = str.split(",");
        Object[] obj = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            obj[i] = (Object) arr[i];
        }
        return obj;
    }

    public static String truncDecimalNumber(String mStrNumber) {
        String mStr1 = mStrNumber.replace(".", ",");
        String[] arr = mStr1.split(",");
        String str = "";
        if (arr.length > 0) {
            str = arr[0];
        }
        return str;
    }

    public static double truncDecimalNumber(double mStrNumber) {

        String mStr1 = String.valueOf(mStrNumber).replace(".", ",");
        String[] arr = mStr1.split(",");
        String str = "";
        if (arr.length > 0) {
            str = arr[0];
        }
        return Double.parseDouble(str);
    }

    public static String listToString(List tempList) {
        String retString = "";
        if (tempList != null) {
            for (int i = 0; i < tempList.size(); i++) {
                if (retString.equals("")) {
                    retString = "'" + tempList.get(i) + "'";
                } else {
                    retString = retString + ", '" + tempList.get(i) + "'";
                }
            }
        }
        return retString;
    }

    public static String objectToString(Object[] tempList) {
        String retString = "";
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                if (retString.equals("")) {
                    retString = "'" + tempList[i] + "'";
                } else {
                    retString = retString + ", '" + tempList[i] + "'";
                }
            }
        }
        return retString;
    }

    public static String checkIfNull(Object val) {
        return val == null ? "" : val.toString();
    }
}
