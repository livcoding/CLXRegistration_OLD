/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentGroupCrLimitIDAO;
import com.jilit.irp.persistence.dto.StudentGroupCrLimit;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author ankit.kumar
 */
public class StudentGroupCrLimitDAO extends HibernateDAO implements StudentGroupCrLimitIDAO {

    private static final Log log = LogFactory.getLog(StudentGroupCrLimitDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentGroupCrLimit records via Hibernate from the database");
        return this.find("from StudentGroupCrLimit as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentGroupCrLimit.class, id);
    }

   

}
