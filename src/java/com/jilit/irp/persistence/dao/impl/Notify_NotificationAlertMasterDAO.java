/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Notify_NotificationAlertMasterIDAO;
import com.jilit.irp.persistence.dto.Notify_NotificationAlertMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author soa university
 */
public class Notify_NotificationAlertMasterDAO extends HibernateDAO implements Notify_NotificationAlertMasterIDAO {

    private static final Log log = LogFactory.getLog(Notify_NotificationAlertMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Notify_NotificationAlertMaster records via Hibernate from the database");
        return this.find("from Notify_NotificationAlertMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Notify_NotificationAlertMaster.class, id);
    }


    /**
     * Description: Function has been used to Sms/ Email/ Portal Data of particular user.
     * @param memberid
     * @param membertype
     * @return
     */
    public List getSMSEmailPortalData(final String memberid, final String membertype) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Notify_NotificationAlertMaster.class, "master");
                criteria.createAlias("master.notify_notificationtos", "nt");
                if("A".equals(membertype) || "E".equals(membertype) || "C".equals(membertype) ||"V".equals(membertype))
                {
                    criteria.add(Restrictions.eq("nt.tostaffid", memberid));
                }
                else if("S".equals(membertype))
                {
                   criteria.add(Restrictions.eq("nt.tostudentid", memberid));
                }
                criteria.add(Restrictions.eq("nt.deleteflag", "N"));
                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", "N")));
                criteria.addOrder(Order.desc("master.notificationdatetime"));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.notificationvia").as("notificationvia"))
                                                                   .add(Projections.property("master.notificationdatetime").as("notificationdatetime"))
                                                                   .add(Projections.property("master.notoficationsubject").as("notoficationsubject"))
                                                                   .add(Projections.property("master.notificationtext").as("notificationtext"))
                                                                   .add(Projections.property("nt.readflag").as("readflag"))
                                                                   .add(Projections.property("nt.deleteflag").as("deleteflag"))
                                                                   .add(Projections.property("nt.id.notificationid").as("notificationid"))
                                                                   .add(Projections.property("nt.id.slno").as("slno"))
                                                                   .add(Projections.property("master.notificationfrom").as("notificationfrom")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }
}
