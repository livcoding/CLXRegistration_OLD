/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.LocationMasterIDAO;
import com.jilit.irp.persistence.dto.LocationMaster;
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
import java.io.Serializable;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class LocationMasterDAO extends HibernateDAO implements LocationMasterIDAO {

    private static final Log log = (Log) LogFactory.getLog(LocationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all LocationMaster records via Hibernate from the database");
        return this.find("from LocationMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(LocationMaster.class, id);
    }

}
