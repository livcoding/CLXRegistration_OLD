/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.util;

 
 import java.text.SimpleDateFormat;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
 
public class JIRPDBUtil {
 
    public static Criterion getCriteriaForString(String fname, Object fvalue, String logic) {
        if (logic.equals("equal")) {
            return (Criterion) Restrictions.eq(fname, fvalue).ignoreCase();
        } else if (logic.equals("notequal")) {
            return (Criterion) Restrictions.ne(fname, fvalue).ignoreCase();
        } else if (logic.equals("less")) {
            return (Criterion) Restrictions.lt(fname, fvalue).ignoreCase();
        } else if (logic.equals("lessEqual")) {
            return (Criterion) Restrictions.le(fname, fvalue).ignoreCase();
        } else if (logic.equals("great")) {
            return (Criterion) Restrictions.gt(fname, fvalue).ignoreCase();
        } else if (logic.equals("greatEqual")) {
            return (Criterion) Restrictions.ge(fname, fvalue).ignoreCase();
        } else if (logic.equals("like")) {
            return (Criterion) Restrictions.like(fname, String.valueOf(fvalue), MatchMode.ANYWHERE).ignoreCase();
        } else if (logic.equals("startWith")) {
            return (Criterion) Restrictions.like(fname, String.valueOf(fvalue), MatchMode.START).ignoreCase();
        } else if (logic.equals("endWith")) {
            return (Criterion) Restrictions.like(fname, String.valueOf(fvalue), MatchMode.END).ignoreCase();
        } else if (logic.equals("in")) {
            return (Criterion) Expression.in(fname,  (Object[])fvalue);
           //return (Criterion) Expression.in(fname,  fvalue));
        } else if (logic.equals("nvl")) {
            return (Criterion) Restrictions.or(Restrictions.isNull(fname), Restrictions.eq(fname, fvalue));
        } else if (logic.equals("isnotnull")) {
            return (Criterion) Restrictions.isNotNull(fname);
        } else if (logic.equals("isnotnullorempty")) {
            return (Criterion) Restrictions.and(Restrictions.isNotNull(fname), Restrictions.isNotEmpty(fname));
        }
        return null;
    }
 
    public static String generateId(String dbStr, String prefix) {
        SimpleDateFormat format1 = new SimpleDateFormat("yy");

        String mth = (new SimpleDateFormat("MM")).format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(10);
        String year = format1.format(System.currentTimeMillis());
        sb.append(prefix + year);
        if (dbStr.equals("")) {
            return sb.append(mth + "01").toString();
        }
 
        if (!dbStr.substring(4, 6).equals(year)) {
            sb.append(mth + "01");
        } else {

            if (!dbStr.substring(6, 8).equals(mth)) {
                sb.append(mth + "01");
            } else {
                sb.append(mth);
                int a = Integer.parseInt(dbStr.substring(8, 10)) + 1;
                if (a <= 9) {
                    sb.append("0");
                }
                sb.append(String.valueOf(a));

            }
        }
        return sb.toString();
    }

 
}
