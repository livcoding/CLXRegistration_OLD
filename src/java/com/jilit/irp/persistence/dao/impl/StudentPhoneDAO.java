/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentPhoneIDAO;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentPhone;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;


import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author campus.trainee
 */
public class StudentPhoneDAO extends HibernateDAO implements StudentPhoneIDAO {

    private static final Log log = LogFactory.getLog(StudentPhoneDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentPhone records via Hibernate from the database");
        return this.find("from StudentPhone as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentPhone.class, id);
    }


}
