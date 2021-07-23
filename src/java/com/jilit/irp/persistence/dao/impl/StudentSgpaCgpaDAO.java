/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentSgpaCgpaIDAO;
import com.jilit.irp.persistence.dto.StudentSgpaCgpa;
import com.jilit.irp.persistence.dto.StudentSgpaCgpaId;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author ashok.singh
 */
public class StudentSgpaCgpaDAO extends HibernateDAO implements StudentSgpaCgpaIDAO {

    private static final Log log = (Log) LogFactory.getLog(StudentSgpaCgpa.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentSgpaCgpa records via Hibernate from the database");
        return this.find("from StudentSgpaCgpa as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentSgpaCgpa.class, id);
    }

}
