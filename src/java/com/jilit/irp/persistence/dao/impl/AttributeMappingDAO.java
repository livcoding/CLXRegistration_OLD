/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.AttributeMappingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  
 * @author jaswinder.singh
 */
public class AttributeMappingDAO extends HibernateDAO implements AttributeMappingIDAO {

    private static final Log log = LogFactory.getLog(AttributeMappingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all AttributeMapping records via Hibernate from the database");
        return this.find("from AttributeMapping as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
