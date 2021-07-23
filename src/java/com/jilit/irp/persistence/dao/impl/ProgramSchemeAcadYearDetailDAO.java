/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ProgramSchemeAcadYearDetailIDAO;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadYearDetail;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author subrata.lohar
 */
public class ProgramSchemeAcadYearDetailDAO extends HibernateDAO implements ProgramSchemeAcadYearDetailIDAO {

    private static final Log log = LogFactory.getLog(ProgramSchemeAcadYearDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramSchemeAcadyearDetail records via Hibernate from the database");
        return this.find("from ProgramSchemeAcadyearDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramSchemeAcadYearDetail.class, id);
    }

//    public void update(Object record) {
//        getHibernateTemplate().update((ProgramSchemeAcadYearDetail) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((ProgramSchemeAcadYearDetail) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((ProgramSchemeAcadYearDetail) record);
//    }
//
    public boolean deleteChildRecord(List<ProgramSchemeAcadYearDetail> objectList) {
        boolean flag = false;
        Session session = null;
        Transaction tx = null;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }
            for (int i = 0; i < objectList.size(); i++) {
                session.delete(objectList.get(i));
            }
            tx.commit();
            if (tx.wasCommitted()) {
                flag = true;
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            flag = false;
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return flag;
    }
}
