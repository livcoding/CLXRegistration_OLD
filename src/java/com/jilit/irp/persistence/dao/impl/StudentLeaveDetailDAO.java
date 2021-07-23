/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentLeaveDetailIDAO;
import com.jilit.irp.persistence.dto.StudentLeaveDetail;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public class StudentLeaveDetailDAO extends HibernateDAO implements StudentLeaveDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentLeaveDetailDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentLeaveDetail records via Hibernate from the database");
        return this.find("from StudentLeaveDetail as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentLeaveDetail.class, id);

    }
}
