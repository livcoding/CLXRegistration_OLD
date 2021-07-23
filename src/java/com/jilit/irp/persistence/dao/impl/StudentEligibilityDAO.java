/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.StudentEligibilityIDAO;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
 * @author hariom
 */
public class StudentEligibilityDAO extends HibernateDAO implements StudentEligibilityIDAO {
   private static final Log log = LogFactory.getLog(StudentEligibilityDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentEligibility records via Hibernate from the database");
        return this.find("from StudentEligibility as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

