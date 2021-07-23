/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.StudentLgIDAO;
import com.jilit.irp.persistence.dto.StudentLg;
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
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class StudentLgDAO extends HibernateDAO implements StudentLgIDAO{

    private static final Log log = LogFactory.getLog(StudentLgDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentLg records via Hibernate from the database");
        return this.find("from StudentLg as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentLg.class, id);
    }

    public Short getMaxSLNO(final Object exampleObject) {
                        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(exampleObject.getClass());
				criteria.setProjection(Projections.projectionList().add(Projections.max("id.slno")));
				return criteria.list();
			}
		});
		Short slno = list.get(0)==null?new Short("0"):(Short) list.get(0);
        return slno;
	}

     public Short getMaxSLNO(final Object exampleObject,final String studentid) {
                        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(exampleObject.getClass(), "master");
                criteria.add(Expression.eq("master.id.studentid", studentid));
				criteria.setProjection(Projections.projectionList().add(Projections.max("id.slno")));
                System.err.println("STUDENTID-------------"+studentid);
				return criteria.list();
			}
		});
		Short slno = list.get(0)==null?new Short("0"):(Short) list.get(0);
        return slno;
	}

}

