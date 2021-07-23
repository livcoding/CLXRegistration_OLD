/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentCertificateSlnoIDAO;
import com.jilit.irp.persistence.dto.StudentCertificateSlno;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author ashok.singh
 */
public class StudentCertificateSlnoDAO extends HibernateDAO implements StudentCertificateSlnoIDAO {

    private static final Log log = LogFactory.getLog(StudentCertificateSlnoDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentCertificateSlno records via Hibernate from the database");
        return this.find("from StudentCertificateSlno as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentCertificateSlno.class, id);
    }

}
