/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_SuspendedSlotsDetailIDAO;
import com.jilit.irp.persistence.dto.TT_SuspendedSlotsDetail;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public class TT_SuspendedSlotsDetailDAO extends HibernateDAO implements TT_SuspendedSlotsDetailIDAO {

    private static final Log log = LogFactory.getLog(TT_SuspendedSlotsDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_SuspendedSlotsDetail records via Hibernate from the database");
        return this.find("from TT_SuspendedSlotsDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_SuspendedSlotsDetail.class, id);
    }

    
}
    