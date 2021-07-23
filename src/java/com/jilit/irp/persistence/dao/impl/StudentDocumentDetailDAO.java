/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentDocumentDetailIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.StudentDocumentDetail;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Shimona.Khandelwal
 */
public class StudentDocumentDetailDAO extends HibernateDAO implements StudentDocumentDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentDocumentDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentDocumentDetail records via Hibernate from the database");
        return this.find("from StudentDocumentDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentDocumentDetail.class, id);
    }

//    public String insert(List<StudentDocumentDetail> studentDocumentDetailList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + studentDocumentDetailList.size());
//            for (int i = 0; i < studentDocumentDetailList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.saveOrUpdate((StudentDocumentDetail) studentDocumentDetailList.get(i));
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
//            session.disconnect();
//        }
//        return retList;
//    }
}

