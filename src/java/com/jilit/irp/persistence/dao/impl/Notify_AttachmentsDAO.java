/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Notify_AttachmentsIDAO;
import com.jilit.irp.persistence.dto.Notify_Attachments;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
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
public class Notify_AttachmentsDAO extends HibernateDAO implements Notify_AttachmentsIDAO {

    private static final Log log = LogFactory.getLog(Notify_AttachmentsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Notify_Attachments records via Hibernate from the database");
        return this.find("from Notify_Attachments as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Notify_Attachments.class, id);
    }

    public int getMaxAttachmentSLNo(final String notificationid, final int slno){
        long attachmentslno = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Notify_Attachments.class);
                criteria.add(Restrictions.eq("id.notificationid", notificationid));
                criteria.add(Restrictions.eq("id.slno", slno));
                criteria.setProjection(Projections.max("id.attachmentslno"));
                return criteria.list() == null || criteria.list().isEmpty() ? 0 : Integer.valueOf(criteria.list().get(0).toString());
            }
        });
        return (int)attachmentslno;

    }

}
