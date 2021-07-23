/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TempRollNumberControlIDAO;
import com.jilit.irp.persistence.dto.TempRollNumberControl;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 *
 * @author malkeet.singh
 */
public class TempRollNumberControlDAO extends HibernateDAO implements TempRollNumberControlIDAO {
private static final Log log = LogFactory.getLog(TempRollNumberControlDAO.class);
    public Collection<?> findAll() {
        log.info("Retrieving all TempRollNumberControlDAO records via Hibernate from the database");
        return this.find("from TempRollNumberControlDAO as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TempRollNumberControl.class, id);
    }

    
    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((TempRollNumberControl) record);
    }

    
}
