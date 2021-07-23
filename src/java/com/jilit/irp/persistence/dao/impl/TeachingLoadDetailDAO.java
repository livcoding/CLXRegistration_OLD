/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TeachingLoadDetailIDAO;
import com.jilit.irp.persistence.dto.TeachingLoadDetail;
import java.util.Collection;
import java.io.Serializable;

import java.sql.SQLException;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author chetan.kaushik
 */
public class TeachingLoadDetailDAO extends HibernateDAO implements TeachingLoadDetailIDAO {

    private static final Log log = LogFactory.getLog(TeachingLoadDetail.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Teaching Load Detail records via Hibernate from the database");
        return this.find("from TeachingLoadDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TeachingLoadDetail.class, id);

    }

}