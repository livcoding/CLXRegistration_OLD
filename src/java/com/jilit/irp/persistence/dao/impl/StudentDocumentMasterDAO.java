/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentDocumentMasterIDAO;
import com.jilit.irp.persistence.dto.CategoryProgramDocumentTagging;
import com.jilit.irp.persistence.dto.QualificationCategory;
import com.jilit.irp.persistence.dto.StudentDocumentMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ankit.kumar
 */
public class StudentDocumentMasterDAO extends HibernateDAO implements StudentDocumentMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentDocumentMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentDocumentMaster records via Hibernate from the database");
        return this.find("from StudentDocumentMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentDocumentMaster.class, id);
    }
    public int checkIfChildExist(final String documentid) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentDocumentMaster studentDocumentMaster = (StudentDocumentMaster) session.get(StudentDocumentMaster.class, documentid);
                int i1 = Integer.parseInt(session.createFilter(studentDocumentMaster.getCategoryprogramdocumenttaggings(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getUnTaggedDocuments(final String instituteid, final String categoryid, final String programid) {

                String qryString = "select cm.id.documentid, cm.documentcode, cm.documentdesc   " +
                        " from StudentDocumentMaster cm " +
                        " where  coalesce(cm.deactive,'N')='N'" +
                        " and (cm.id.documentid) " +
                        " not in (select ict.id.documentid from CategoryProgramDocumentTagging  ict where " +
                        " ict.id.instituteid='" + instituteid + "' " +
                        " and ict.id.categoryid='" + categoryid + "' " +
                        " and ict.id.programid='" + programid + "') ";
        return getHibernateTemplate().find(qryString);

    }
//
     public String getSaveInsertForCategoryProgramDocumentTagging(List<CategoryProgramDocumentTagging> finalDeleteList,List<CategoryProgramDocumentTagging> finalInsertList)
   {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
              tx = session.beginTransaction();
            //System.err.println("*********** in transaction " + LoadDistributionGrantList.size());
            for (int i = 0; i < finalInsertList.size(); i++)
            {
                System.err.println("************* value" + i);
                session.save(finalInsertList.get(i));
            }
//            for (int i = 0; i < finalDeleteList.size(); i++)
//            {
//                System.err.println("************* value" + i);
//                session.delete(finalDeleteList.get(i));
//            }
            retList = "Data Save Successfully";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }
}
