/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ClientMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ClientMaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author ankur.goyal
 */
public class ClientMasterDAO extends HibernateDAO implements ClientMasterIDAO{

    private static final Log log = LogFactory.getLog(ClientMasterDAO.class);
    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all ClientMaster records via Hibernate from the database");
        return this.find("from ClientMaster as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ClientMaster.class, id);
    }
}
