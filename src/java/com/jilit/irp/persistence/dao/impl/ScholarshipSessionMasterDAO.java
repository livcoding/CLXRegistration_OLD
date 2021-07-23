/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ScholarshipSessionMasterIDAO;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Session;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dto.ScholarshipSessionMaster;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
/**
 *
 *
 * @author Jaswinder.singh
 */
public class ScholarshipSessionMasterDAO extends HibernateDAO implements ScholarshipSessionMasterIDAO {

    private static final Log log = LogFactory.getLog(ScholarshipSessionMasterDAO.class);

    public Collection<?> findAll() {
       log.info("Retrieving all ScholarshipSessionMaster records via Hibernate from the database");
        return this.find("from ScholarshipSessionMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ScholarshipSessionMaster.class, id);
    }


}
