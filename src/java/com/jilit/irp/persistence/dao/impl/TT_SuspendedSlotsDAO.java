/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_SuspendedSlotsIDAO;
import com.jilit.irp.persistence.dto.TT_SuspendedSlots;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public class TT_SuspendedSlotsDAO extends HibernateDAO implements TT_SuspendedSlotsIDAO {

    private static final Log log = LogFactory.getLog(TT_SuspendedSlotsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_SuspendedSlots records via Hibernate from the database");
        return this.find("from TT_SuspendedSlots as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_SuspendedSlots.class, id);
    }

}

