/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Notify_NotificationToIDAO;
import com.jilit.irp.persistence.dto.Notify_NotificationTo;
import java.io.Serializable;
import java.sql.SQLException;
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
 * @author soa university
 */
public class Notify_NotificationToDAO extends HibernateDAO implements Notify_NotificationToIDAO {

    private static final Log log = LogFactory.getLog(Notify_NotificationToDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Notify_NotificationTo records via Hibernate from the database");
        return this.find("from Notify_NotificationTo as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Notify_NotificationTo.class, id);
    }

//    public int getMaxSLNo(final String notificationid) {
//        List slno = (List) getHibernateTemplate().execute(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(Notify_NotificationTo.class);
//                criteria.add(Restrictions.eq("id.notificationid", notificationid));
//                criteria.setProjection(Projections.max("id.slno"));
//                return criteria.list();
//            }
//        });
//        return 0;
//    }
}
