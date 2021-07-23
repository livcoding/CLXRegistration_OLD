/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Xl_RegSubjectDataIDAO;
import com.jilit.irp.persistence.dto.Xl_RegSubjectData;
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

/**
 *
 *  @author vijeta.bopche
 */
public class Xl_RegSubjectDataDAO extends HibernateDAO implements Xl_RegSubjectDataIDAO {

    public static final Log log = LogFactory.getLog(Xl_RegSubjectDataDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Xl_RegSubjectData records via Hibernate from the database");
        return this.find("from Xl_RegSubjectData as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Xl_RegSubjectData.class, id);
    }

    public List getAllXlRegSubjectData(final String userid, final String xltaskid) {
        List list = getHibernateTemplate().executeFind(new org.springframework.orm.hibernate3.HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Xl_RegSubjectData.class, "master");
                criteria.add(Restrictions.eq("master.id.userid", userid));
                criteria.add(Restrictions.eq("master.id.xltaskid", xltaskid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.slno").as("slno")).add(Projections.property("registrationcode").as("registrationcode")).add(Projections.property("subjectcode").as("subjectcode")).add(Projections.property("enrollmentno").as("enrollmentno")).add(Projections.property("credits").as("credits")).add(Projections.property("stynumber").as("stynumber")).add(Projections.property("stytype").as("stytype")).add(Projections.property("recstatusmessage").as("recstatusmessage")).add(Projections.property("processingdate").as("processingdate")));

                return criteria.list();
            }
        });
        return list;
    }
}
