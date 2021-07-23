/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.IdGenerationControlIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.IdGenerationControl;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Shimona.Khandelwal
 */
public class IdGenerationControlDAO extends HibernateDAO implements IdGenerationControlIDAO{

    private static final Log log = LogFactory.getLog(IdGenerationControlDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all IdGenerationControl records via Hibernate from the database");
        return this.find("from IdGenerationControl as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(IdGenerationControl.class, id);
    }
}

