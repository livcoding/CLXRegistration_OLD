/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.IdGenerationSetupIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.IdGenerationSetup;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Shimona.Khandelwal
 */
public class IdGenerationSetupDAO extends HibernateDAO implements IdGenerationSetupIDAO{

    private static final Log log = LogFactory.getLog(IdGenerationSetupDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all IdGenerationSetup records via Hibernate from the database");
        return this.find("from IdGenerationSetup as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(IdGenerationSetup.class, id);
    }
}

