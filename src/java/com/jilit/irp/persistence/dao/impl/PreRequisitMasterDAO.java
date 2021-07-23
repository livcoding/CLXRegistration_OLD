/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.PreRequisitMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PreRequisitMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;


import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;


import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class PreRequisitMasterDAO extends HibernateDAO implements PreRequisitMasterIDAO {

    private static final Log log = LogFactory.getLog(PreRequisitMaster.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitMaster records via Hibernate from the database");
        return this.find("from PreRequisitMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitMaster.class, id);
    }
}
