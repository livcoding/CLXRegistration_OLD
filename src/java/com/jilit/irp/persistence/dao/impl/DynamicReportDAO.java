/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.DynamicReportIDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ajay2.kumar
 */
public class DynamicReportDAO extends HibernateDAO implements DynamicReportIDAO{

private static final Log log = LogFactory.getLog(DynamicReportDAO.class);

 public Collection<?> findAll() {
        log.info("Retrieving all StudentMaster records via Hibernate from the database");
        return this.find("from StudentMaster as tname");
    }
    public Object findByPrimaryKey(Serializable id) {
        return null;
    }
    public List getDynamicMasterReport(String query) {
        System.out.println(query);
        List list=new ArrayList();
        try{
         list = getHibernateTemplate().find(query);
        }
         catch(Exception e)
         {
          e.printStackTrace();
         }
        return list;

    }

//    public Map getDataOfQuery(final String query) {
//String errstr="";
//Map m=new HashMap();
//List l=new ArrayList();
//        try{
//         l = getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                return session.createSQLQuery(query).list();
//            }
//        }
//        );
//        errstr="Success";
//    }catch(Exception e)
//    {
//    errstr=e.getMessage();
//    }
//        m.put("errstr", errstr);
//        m.put("list", l);
//return m;
//    }
//
}
