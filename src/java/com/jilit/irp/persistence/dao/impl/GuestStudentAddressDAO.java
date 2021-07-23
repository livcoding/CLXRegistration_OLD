/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.GuestStudentAddressIDAO;
import com.jilit.irp.persistence.dao.GuestStudentMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.GuestStudentAddress;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author priya.sharma
 */
public class GuestStudentAddressDAO extends HibernateDAO implements GuestStudentAddressIDAO{
     private static final Log log = LogFactory.getLog(GuestStudentAddressDAO.class);
     
     
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(GuestStudentAddress.class, id);
    }

    public Collection<?> findAll() {
        log.info("Retrieving all GuestStudentAddress records via Hibernate from the database");
        return this.find("from GuestStudentAddress as tname");
    }
    
}
