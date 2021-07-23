/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.TT_MultiFacultyTeachingLoadIDAO;
import com.jilit.irp.persistence.dto.TT_MultiFacultyTeachingLoad;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.hibernate.Transaction;

/**
 *
 * @author ashok.singh
 */
public class TT_MultiFacultyTeachingLoadDAO extends HibernateDAO implements TT_MultiFacultyTeachingLoadIDAO{
     private static final Log log = LogFactory.getLog(TT_MultiFacultyTeachingLoad.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_MultiFacultyTeachingLoad records via Hibernate from the database");
        return this.find("from TT_MultiFacultyTeachingLoad as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_MultiFacultyTeachingLoad.class, id);
    }
      public void deleteSlotAllocationRecord(final String query) {
        Session session = null;
        Transaction tx = null;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }

            org.hibernate.Query quy = session.createQuery(query);
            quy.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

}
