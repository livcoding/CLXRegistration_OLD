/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CommitteeDesignationsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CommitteeDesignations;
import com.jilit.irp.persistence.dto.CommitteeDesignationsId;
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
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author campus.trainee
 */
public class CommitteeDesignationsDAO extends HibernateDAO implements CommitteeDesignationsIDAO {

    private static final Log log = LogFactory.getLog(CommitteeDesignationsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CommitteeDesignations records via Hibernate from the database");
        return this.find("from CommitteeDesignations as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeDesignations.class, id);
    }

    public List<String> doValidate(final CommitteeDesignations committeeDesignations, final String mode) {

        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(CommitteeDesignations.class);
                criteria.add(Restrictions.eq("designationcode", committeeDesignations.getDesignationcode()).ignoreCase());
                criteria.add(Restrictions.eq("id.instituteid", committeeDesignations.getId().getInstituteid()));
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.designationid", committeeDesignations.getId().getDesignationid()));   //Do not check for itself when updating record

                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Same Values Of Designation Code exist");
        }
        return errors;
    }

    public int checkIfChildExist(final CommitteeDesignationsId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                System.err.println("RAM  RAM RAM RAM RAM RAM RAM" + id);
                CommitteeDesignations committeeDesignations = (CommitteeDesignations) session.get(CommitteeDesignations.class, id);
                long b = 0;
                int i = ((Integer) session.createFilter(committeeDesignations.getCommitteeDesignationses(), "select count(*)").list().get(0)).intValue();
                if (i > 0) {
                    b = i;
                }
                System.err.println("CHIELD EXIST----------------------" + i + b);
                return b;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

//    public Collection<?> getdesignation(final String orderby, final String sortedby) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(CommitteeDesignations.class, "cd");
//
//                if (orderby.equals("C") && sortedby.equals("A")) {
//                    criteria.addOrder(Order.asc("cd.instituteid"));
//                } else if (orderby.equals("C") && sortedby.equals("D")) {
//                    criteria.addOrder(Order.desc("cd.instituteid"));
//                } else if (orderby.equals("D") && sortedby.equals("A")) {
//                    criteria.addOrder(Order.asc("cd.designationcode"));
//                } else if (orderby.equals("D") && sortedby.equals("D")) {
//                    criteria.addOrder(Order.desc("cd.designationcode"));
//                }
//                return criteria.list();
//            }
//        });
//        return list;
//    }
}
