/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.RollNumberingControlIDAO;
import com.jilit.irp.persistence.dto.RollNumberingControl;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 *
 * @author singh.jaswinder
 */
public class RollNumberingControlDAO extends HibernateDAO implements RollNumberingControlIDAO {
private static final Log log = LogFactory.getLog(RollNumberingControlDAO.class);
    public Collection<?> findAll() {
        log.info("Retrieving all RollNumberingSetup records via Hibernate from the database");
        return this.find("from RollNumberingControl as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RollNumberingControl.class, id);
    }

    
    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((RollNumberingControl) record);
    }

    
}
