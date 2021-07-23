/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.RegCoordinatorTaggingIDAO;
import com.jilit.irp.persistence.dto.RegCoordinatorTagging;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vikash.payasi
 */
public class RegCoordinatorTaggingDAO extends HibernateDAO implements RegCoordinatorTaggingIDAO {

    private static final Log log = LogFactory.getLog(RegCoordinatorTaggingDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all RegCoordinatorTagging records via Hibernate from the database");
        return this.find("from RegCoordinatorTagging as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RegCoordinatorTagging.class, id);
    }

}
