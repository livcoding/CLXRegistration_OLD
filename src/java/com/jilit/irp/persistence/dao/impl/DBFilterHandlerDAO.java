/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.DBFilterHandlerIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO; 
import com.jilit.irp.persistence.dto.OtherStaffMaster; 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import javax.mail.search.AddressTerm;
import org.apache.commons.lang.StringUtils;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.*;

/**
 *
 * @author Shimona.Khandelwal
 */
public class DBFilterHandlerDAO extends HibernateDAO implements DBFilterHandlerIDAO {

    private String getPropertyName(String fieldname, List primarykeynames) {
        if (primarykeynames.contains(fieldname)) {
            return "id." + fieldname;
        }
        return fieldname;
    }

    private String getPropertyName(String fieldname, List primarykeynames, String type) {
        if(type.equals("String")){
            if (primarykeynames.contains(fieldname)) {
                return "id." + fieldname;
            }
        }
        return fieldname;
    }
 
//    public Collection<?> filterRecords123(final String employeetype) {
//
//        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                // Criteria criteria = session.createCriteria(EmployeeMaster.class, "master");
//
//                List l = null;
//                try {
//                    String fname = "";
//                    String type = "String";
//                    System.err.println("EMPLOYEETYPE" + employeetype);
//                    if (employeetype.equals("true")) {
//                        Criteria criteria = session.createCriteria(EmployeeMaster.class, "master");
//                        //criteria.add(Example.create(EmployeeMaster));
//                        criteria.setFetchMode("designationmasters", FetchMode.JOIN).setFetchMode("departmentmasters", FetchMode.JOIN);
//                        //.createAlias("depthod.departmentwisehoddetails", "departmentwisehoddetails").setFetchMode("departmentwisehoddetails", FetchMode.JOIN);
//                        //.createAlias("master.departmentmaster", "depthod").setFetchMode("departmentmaster", FetchMode.JOIN);
//                        criteria.setProjection(Projections.projectionList().add(Projections.property("master.employeeid").as("empid")).add(Projections.property("master.employeecode").as("empcode")).add(Projections.property("master.employeename").as("empname")));
//                        criteria.addOrder(Order.asc("master.employeename"));
//                        criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                        l = criteria.list();
//                    } /*else{
//                  
//                    }*/ else {
//                        Criteria criteria = session.createCriteria(OtherStaffMaster.class, "masters");
//                        criteria.createAlias("otherstaffmasterdetails", "osmd");
//                        //Criteria criteria = session.createCriteria(OtherStaffMasterDetail.class, "masters");
//                        //criteria.createAlias("designationmasters", "desigmaster");
//                        // criteria.createCriteria("designationmasters");
//                        //criteria/*.createAlias("designationmasters","designationmasters")*/
//                        // criteria.setFetchMode("designationmasters", FetchMode.JOIN)
//                        /*.createAlias("departmentmasters","departmentmasters")*/
//                        //.setFetchMode("departmentmasters", FetchMode.JOIN)
//                        //.setFetchMode("otherstaffmasters", FetchMode.JOIN);
//                        criteria.setProjection(Projections.projectionList().add(Projections.property("osmd.osid").as("empid")).add(Projections.property("osmd.oscode").as("empcode")).add(Projections.property("osmd.osname").as("empname")));
//                        criteria.addOrder(Order.asc("osmd.osname"));
//                        criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                        l = criteria.list();
//                    }
//
//                // l = criteria.list();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return l;
//            }
//        });
//        if (list != null) {
//            System.err.println("Check Size Of ::" + ":: " + list.size());
//        }
//        return list;
//    }

    


}
