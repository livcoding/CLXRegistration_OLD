/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentAttendancePeriodIDAO;
import com.jilit.irp.persistence.dto.StudentAttendanceEntryDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author girish.chander
 */
public class StudentAttendancePeriodDAO extends HibernateDAO implements StudentAttendancePeriodIDAO {

    public Collection<?> findAll() {
        return this.find("from StudentAttendanceEntryDate as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAttendanceEntryDate.class, id);
    }

}
