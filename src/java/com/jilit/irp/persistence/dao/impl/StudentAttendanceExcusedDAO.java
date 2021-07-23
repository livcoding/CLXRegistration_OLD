/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentAttendanceExcusedIDAO;
import com.jilit.irp.persistence.dto.StudentAttendanceExcused;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import org.hibernate.Transaction;

/**
 *
 * @author v.kumar
 */
public class StudentAttendanceExcusedDAO extends HibernateDAO implements StudentAttendanceExcusedIDAO {

    private static final Log log = LogFactory.getLog(StudentAttendanceExcusedDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAttendanceExcused records via Hibernate from the database");
        return this.find("from StudentAttendanceExcused as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAttendanceExcused.class, id);
    }

//    public String insertStudentAttendanceExcused(final List<StudentAttendanceExcused> recordsToInsert) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + recordsToInsert.size());
//            for (int i = 0; i < recordsToInsert.size(); i++) {
//                System.err.println("************* value" + i);
//                StudentAttendanceExcused a = (StudentAttendanceExcused) recordsToInsert.get(i);
//                session.save((StudentAttendanceExcused) recordsToInsert.get(i));
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
//
//    public List<String> doValidate(final StudentAttendanceExcused studentAttendance, final String mode) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        //Unique Key Constraint
//        //System.err.println("in Validate method");
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(StudentAttendanceExcused.class);
//                criteria.add(Expression.eq("id.instituteid", studentAttendance.getId().getInstituteid()));
//                criteria.add(Expression.eq("id.fstid", studentAttendance.getId().getFstid()));
//                criteria.add(Expression.eq("id.attendancedate", studentAttendance.getId().getAttendancedate()));
//                criteria.add(Expression.eq("id.classtimefrom", studentAttendance.getId().getClasstimefrom()));
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue() > 0) {
//            errors.add("Attendance already marked as Excuse for the selected time slot!");
//        }
//        return errors;
//    }
}
