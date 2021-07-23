/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.BasketMasterDetailIDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.BasketMasterDetail;
import com.jilit.irp.persistence.dto.BasketMaster;

import com.jilit.irp.util.JIRPSession;
import java.io.Serializable;
import java.sql.SQLException;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class BasketMasterDetailDAO extends HibernateDAO implements BasketMasterDetailIDAO {

    private static final Log log = LogFactory.getLog(BasketMasterDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all BasketMasterDetail records via Hibernate from the database");
        return this.find("from BasketMasterDetail as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(BasketMasterDetail.class, id);
    }

    public List<String> doValidate(final BasketMasterDetail basketMasterDetail, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(BasketMasterDetail.class);
                criteria.add(Restrictions.eq("id.instituteid", basketMasterDetail.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.academicyear", basketMasterDetail.getId().getAcademicyear()));
                criteria.add(Restrictions.eq("id.programid", basketMasterDetail.getId().getProgramid()).ignoreCase());
                criteria.add(Restrictions.eq("id.sectionid", basketMasterDetail.getId().getSectionid()).ignoreCase());
                criteria.add(Restrictions.eq("id.stynumber", basketMasterDetail.getId().getStynumber()));
                criteria.add(Restrictions.eq("basketcode", basketMasterDetail.getBasketcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.basketid", basketMasterDetail.getId().getBasketid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                System.err.println("criteria.list() - " + criteria.list());
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Basket Code Found! ");
        }
        return errors;
    }

}