/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SpecializationCategoryIDAO;
import com.jilit.irp.persistence.dto.SpecializationCategory;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sunny.singhal
 */
public class SpecializationCategoryDAO extends HibernateDAO implements SpecializationCategoryIDAO {

    private static final Log log = LogFactory.getLog(SpecializationCategoryDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SpecializationCategory records via Hibernate from the database");
        return this.find("from SpecializationCategory as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SpecializationCategory.class, id);
    }
}
