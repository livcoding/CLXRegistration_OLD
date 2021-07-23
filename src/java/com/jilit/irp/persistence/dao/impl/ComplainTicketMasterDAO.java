/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ComplainTicketMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ComplainTicketMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author vipinkr.sharma
 */
public class ComplainTicketMasterDAO extends HibernateDAO implements ComplainTicketMasterIDAO {

     private static final Log log = LogFactory.getLog(ComplainTicketMasterDAO.class);


    public Collection<?> findAll() {
        log.info("Retrieving all ComplainTicketMaster records via Hibernate from the database");
        return this.find("from ComplainTicketMaster as tname");
    }


    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ComplainTicketMaster.class, id);
    }
}
