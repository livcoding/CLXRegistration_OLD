/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentAttendanceCategoryIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import com.jilit.irp.persistence.dto.StudentAttendanceCategory;
import com.jilit.irp.persistence.dto.Studentattendancefollowup;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author chetna.pargai
 */
public class StudentAttendanceCategoryDAO extends HibernateDAO implements StudentAttendanceCategoryIDAO {

    private static final Log log = (Log) LogFactory.getLog(StudentAttendanceCategoryDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAttendanceCategory records via Hibernate from the database");
        return this.find("from StudentAttendanceCategory as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAttendanceCategory.class, id);
    }
}
