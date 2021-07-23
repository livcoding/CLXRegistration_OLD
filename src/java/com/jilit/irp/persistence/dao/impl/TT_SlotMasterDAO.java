/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_SlotMasterIDAO;
import com.jilit.irp.persistence.dto.TT_SlotMaster;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.jilit.irp.persistence.dao.HibernateDAO;

/**
 *
 * @author ashok.singh
 */
public class TT_SlotMasterDAO extends HibernateDAO implements TT_SlotMasterIDAO {

    private static final Log log = LogFactory.getLog(TT_SlotMaster.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_SlotMaster records via Hibernate from the database");
        return this.find("from TT_SlotMaster as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_SlotMaster.class, id);
    }
}
