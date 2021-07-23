/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PRFacultySubjectChoicesIDAO;
import com.jilit.irp.persistence.dto.PRFacultySubjectChoices;
import com.jilit.irp.persistence.dto.PRFacultySubjectChoicesId;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author suman.saurabh
 */
public class PRFacultySubjectChoicesDAO extends HibernateDAO implements PRFacultySubjectChoicesIDAO {
 private static final Log log = LogFactory.getLog(PRFacultySubjectChoicesDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PRFacultySubjectChoices records via Hibernate from the database");
        return this.find("from PRFacultySubjectChoices as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRFacultySubjectChoices.class, id);
    }


    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((PRStudentSubjectChoiceCount) record);
    }
//    public String insertPRFacultySubjectChoice(final List<PRFacultySubjectChoices> prList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + prList.size());
//            for (int i = 0; i < prList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.save((PRFacultySubjectChoices) prList.get(i));
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
//public int checkIfChildExist(final String instituteid, final String registrationid, final Short eventno, final String stafftype, final String staffid, final String subjectid, final String subjectcomponentid) {
//        HibernateCallback callback = new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                PRFacultySubjectChoices prfsc = (PRFacultySubjectChoices)session.get(PRFacultySubjectChoices.class, new PRFacultySubjectChoicesId(instituteid, registrationid, eventno, stafftype, staffid, subjectid, subjectcomponentid));
//                int i1 = ((Integer) session.createFilter(prfsc.getPrfacultysubjecttimepreferences(), "select count(*)").list().get(0)).intValue();
//                return i1 ;
//            }
//        };
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    }
}
