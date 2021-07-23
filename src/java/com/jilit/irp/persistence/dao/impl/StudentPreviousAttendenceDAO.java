/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentPreviousAttendenceIDAO;
import com.jilit.irp.persistence.dto.StudentPreviousAttendence;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author v.kumar
 */
public class StudentPreviousAttendenceDAO extends HibernateDAO implements StudentPreviousAttendenceIDAO{

    private static final Log log = LogFactory.getLog(StudentPreviousAttendenceDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentPreviousAttendence records via Hibernate from the database");
        return this.find("from StudentPreviousAttendence as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentPreviousAttendence.class, id);
    }

    public List<String> doValidate(StudentPreviousAttendence studentPreviousAttendence, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

