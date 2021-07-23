/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


/**
 *
 * @author ashok.singh
 */

import com.jilit.irp.persistence.dao.StyTypeIDAO;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentwiseCoordinatorIDAO;
import com.jilit.irp.persistence.dto.StudentwiseCoordinator;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


public class StudentwiseCoordinatorDAO extends HibernateDAO implements StudentwiseCoordinatorIDAO {

    private static final Log log = LogFactory.getLog(StudentwiseCoordinatorDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentwiseCoordinator records via Hibernate from the database");
        return this.find("from StudentwiseCoordinator as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentwiseCoordinator.class, id);
    }
}
