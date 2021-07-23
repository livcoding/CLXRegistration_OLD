/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Sct_IrpUserTypeIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Sct_IrpUserType;
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
 * @author sunny.singhal
 */
public class Sct_IrpUserTypeDAO extends HibernateDAO implements Sct_IrpUserTypeIDAO {

    private static final Log log = LogFactory.getLog(Sct_IrpUserTypeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_IrpUserType records via Hibernate from the database");
        return this.find("from Sct_IrpUserType as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_IrpUserType.class, id);
    }

    public List getIrpUserType() {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpUserType.class);
                criteria.add(Restrictions.not(Restrictions.in("usertype", new String[]{"P", "A", "S"})));
                criteria.add(Restrictions.or(Restrictions.isNull("deactive"), Restrictions.eq("deactive", "N")));
                criteria.setProjection(Projections.projectionList().add(Projections.property("usertype").as("data")).add(Projections.property("userdescription").as("label")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }
    
    public List getIrpUserTypeForAssignRoll() {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpUserType.class);
                //criteria.add(Restrictions.not(Restrictions.in("usertype", new String[]{"P", "A", "S"})));
                criteria.add(Restrictions.or(Restrictions.isNull("deactive"), Restrictions.eq("deactive", "N")));
                criteria.setProjection(Projections.projectionList().add(Projections.property("usertype").as("data")).add(Projections.property("userdescription").as("label")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }
}
