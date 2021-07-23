/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentCertificateMasterIDAO;
import com.jilit.irp.persistence.dto.StudentCertificateMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author ashok.singh
 */
public class StudentCertificateMasterDAO extends HibernateDAO implements StudentCertificateMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentCertificateMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentCertificateMaster records via Hibernate from the database");
        return this.find("from StudentCertificateMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentCertificateMaster.class, id);
    }

}
