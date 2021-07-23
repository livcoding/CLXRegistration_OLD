/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentAppointFixationIDAO;
import com.jilit.irp.persistence.dto.StudentAppointFixation;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author pankaj.kumar
 */
public class StudentAppointFixationDAO extends HibernateDAO implements StudentAppointFixationIDAO{

    private static final Log log = LogFactory.getLog(StudentAppointFixationDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAppointFixation records via Hibernate from the database");
        return this.find("from StudentAppointFixation as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAppointFixation.class, id);
    }

}
