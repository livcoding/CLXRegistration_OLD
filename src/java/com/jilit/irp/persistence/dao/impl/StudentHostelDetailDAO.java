/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentHostelDetailIDAO;
import com.jilit.irp.persistence.dto.StudentHostelDetail;
import org.hibernate.criterion.Restrictions;

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
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;


import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author v.kumar
 */
public class StudentHostelDetailDAO extends HibernateDAO implements StudentHostelDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentHostelDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentHostelDetail records via Hibernate from the database");
        return this.find("from StudentHostelDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentHostelDetail.class, id);
    }


}