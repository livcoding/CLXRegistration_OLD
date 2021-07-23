/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OtherStaffMasterDetailIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.OtherStaffMaster;
import com.jilit.irp.persistence.dto.OtherStaffMasterDetail;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author sunny.singhal
 */
public class OtherStaffMasterDetailDAO extends HibernateDAO implements OtherStaffMasterDetailIDAO{

    private static final Log log = LogFactory.getLog(OtherStaffMasterDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OtherStaffMasterDetail records via Hibernate from the database");
        return this.find("from OtherStaffMasterDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OtherStaffMasterDetail.class, id);
    }
	
	public void saveOrUpdate(Object record){        
        getHibernateTemplate().saveOrUpdate((OtherStaffMaster) record);
    }
    
}
