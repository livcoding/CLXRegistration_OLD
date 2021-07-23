/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ExcelExprotIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author sunny.singhal
 */
public class ExcelExprotDAO extends HibernateDAO implements ExcelExprotIDAO {

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public List getTableNamesFromHibernate() {
//        ASObject aso = new ASObject();
//        List l = new ArrayList();
//        getHibernateTemplate().getSessionFactory().getAllClassMetadata();
//        Map xx = (Map) getHibernateTemplate().getSessionFactory().getAllClassMetadata();
//        for (Iterator i = xx.values().iterator(); i.hasNext();) {
//            SingleTableEntityPersister y = (SingleTableEntityPersister) i.next();
//            System.out.println(y.getName() + " -> " + y.getTableName());
//            aso = new ASObject();
//            aso.put("classname", y.getName());
//            aso.put("tablename", y.getTableName());
//            l.add(aso);
//        }
//        return l;
//    }
//
//    public List getColumnNamesFromHibernate(List tablesname) {
//        List l = new ArrayList();
//        ASObject aso = new ASObject();
//        SingleTableEntityPersister y = null;
//        for (int i = 0; i < tablesname.size(); i++) {
//            String tablename = (String) tablesname.get(i);
//            y = (SingleTableEntityPersister) getHibernateTemplate().getSessionFactory().getClassMetadata(tablename);
//            for (int j = 0; j < y.getPropertyNames().length; j++) {
//                aso.put("propertynames", y.getPropertyNames()[j]);
//                // aso.put("nullable", y.)
//                l.add(aso);
//            }
//        }
//        return l;
//    }
//
//    public List getTableNames() {
//        List l = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                String qryString = "select tname from tab where tabtype='TABLE' AND Tname NOT LIKE 'BIN$%' ORDER BY TNAME ";
//                return session.createSQLQuery(qryString).list();
//            }
//        });
//        return (ArrayList) l;
//    }
//
//    public List getColumnTable(final String tablename) {
//        List l = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                String qryString = "SELECT distinct column_name,data_type,data_length,nullable " +
//                        " FROM user_tab_columns " +
//                        " where table_name  in (" + tablename + ") " +
//                        " order by column_name";
//                //"";
//                return session.createSQLQuery(qryString).list();
//            }
//        });
//        return (ArrayList) l;
//    }
//    public Session session = null;
//    public Transaction tx = null;
//
//    public String insertXlsData(final String qry) {
//        String retList = null;
//
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
////            System.err.println("*********** in transaction " + programMinMaxLimitList.size());
////            for (int i = 0; i < programMinMaxLimitList.size(); i++) {
////                System.err.println("************* value" + i);
////                session.save((ProgramMinMaxLimit) programMinMaxLimitList.get(i));
////            }
//            session.createSQLQuery(qry).list();
//            retList = "Data Save Successfully";
//        //tx.commit();
//        } catch (Exception e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            retList = "Error in tx update";
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return retList;
//    }
}
