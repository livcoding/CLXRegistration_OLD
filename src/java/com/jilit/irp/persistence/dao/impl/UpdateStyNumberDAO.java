/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.UpdateStyNumberIDAO;
import com.jilit.irp.persistence.dto.ProgramMaxSty;
import com.jilit.irp.persistence.dto.UpdateStyNumber;
import org.hibernate.Transaction;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.hibernate.criterion.Order;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author akshya.gaur
 */
public class UpdateStyNumberDAO extends HibernateDAO implements UpdateStyNumberIDAO {

    private static final Log log = LogFactory.getLog(UpdateStyNumberDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramMaxSty records via Hibernate from the database");
        return this.find("from ProgramMaxSty as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(UpdateStyNumberDAO.class, id);
    }

    public String saveData(final List updateStyNumberList, final List updateStudentMasterList) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        List lst = new ArrayList();

        try {
            session = getHibernateSession();
            tx = session.beginTransaction();
            System.err.println("*********** in transaction " + updateStyNumberList.size());
            for (int i = 0; i < updateStyNumberList.size(); i++) {
                System.err.println("************* value" + i);

                session.update(updateStudentMasterList.get(i));
                session.saveOrUpdate((UpdateStyNumber) updateStyNumberList.get(i));
            }

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
//

    public List getStudentMaster(final String studentid) {
        ArrayList l = (ArrayList) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qry = "select sm  from StudentMaster sm where sm.studentid='" + studentid + "'";

                return session.createQuery(qry).list();
            }
        });
        return l;
    }
//
//    public List getacedmicyear(final String instituteid) {
//        final List retList = (List) getHibernateTemplate().execute(new HibernateCallback() {
//
//            @Override
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(ProgramMaxSty.class, "master");
//
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
//                criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("master.id.academicyear").as("academicyear"))));
//                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", "N")));
//                criteria.addOrder(Order.desc("master.id.academicyear"));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return retList;
//    }
}
