/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.RoomUsedForTaggingIDAO;
import com.jilit.irp.persistence.dto.RoomUsedForTagging;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;


/**
 *
 * @author shimona.khandelwal
 */

public class RoomUsedForTaggingDAO extends HibernateDAO implements RoomUsedForTaggingIDAO {
    private static final Log log = LogFactory.getLog(RoomUsedForTagging.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RoomUsedForTagging records via Hibernate from the database");
        return this.find("from RoomUsedForTagging as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RoomUsedForTagging.class, id);
    }

     
}

