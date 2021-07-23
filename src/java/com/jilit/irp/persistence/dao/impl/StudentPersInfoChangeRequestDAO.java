/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentPersInfoChangeRequestIDAO;
import com.jilit.irp.persistence.dto.StudentPersInfoChangeRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author akshya.gaur
 */
public class StudentPersInfoChangeRequestDAO extends HibernateDAO implements StudentPersInfoChangeRequestIDAO {

    private static final Log log = LogFactory.getLog(StudentPersInfoChangeRequestDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentPersInfoChangeRequest records via Hibernate from the database");
        return this.find("from StudentPersInfoChangeRequest as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id)
    {
        return getHibernateTemplate().get(StudentPersInfoChangeRequest.class, id);
    }

//    public Collection<?> getStudentPersInfoChangeRequest(String instituteId, String studentId)
//    {
//        Collection collection = null;
//        String qry = "from StudentPersInfoChangeRequest as si where si.id.instituteid = ? and si.id.studentid = ?";
//        collection =  this.find(qry, new String [] {instituteId, studentId});
//        return collection;
//    }
//
//    public String updateStudentPersInfoChangeRequest(final List updateList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            if(updateList.size()==0){
//            retList = "No Data To Save";
//            }else{
//            tx = session.beginTransaction();
//            for (int i = 0; i < updateList.size(); i++) {
//                session.update((StudentPersInfoChangeRequest) updateList.get(i));
//            }
//
//            retList = "Data Save Successfully";
//            tx.commit();
//            }
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
