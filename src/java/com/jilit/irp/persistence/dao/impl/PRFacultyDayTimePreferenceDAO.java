/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PRFacultyDayTimePreferenceIDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dto.PRFacultyDayTimePreference;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author suman.saurabh
 */
public class PRFacultyDayTimePreferenceDAO extends HibernateDAO implements PRFacultyDayTimePreferenceIDAO{
    
    private static final Log log = LogFactory.getLog(PRFacultyDayTimePreferenceDAO.class);
    public Collection<?> findAll() {
       log.info("Retrieving all PRFacultyDayTimePreference records via Hibernate from the database");
        return this.find("from PRFacultyDayTimePreference as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRFacultyDayTimePreference.class, id);
    }

// public String insertPRFacultyDayTimePreference(final List<PRFacultyDayTimePreference> prList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + prList.size());
//            for (int i = 0; i < prList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.save((PRFacultyDayTimePreference) prList.get(i));
//            }
//
//            retList = "Success";
//            tx.commit();
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
