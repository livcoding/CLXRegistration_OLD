/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HostelRoomBedMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.HostelRoomBedMaster;
import com.jilit.irp.persistence.dto.HostelRoomBedMasterId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author chetna.pargai
 */
public class HostelRoomBedMasterDAO extends HibernateDAO implements HostelRoomBedMasterIDAO {

    private static final Log log = LogFactory.getLog(HostelRoomBedMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all HostelRoomBedMaster records via Hibernate from the database");
        return this.find("from HostelRoomBedMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(HostelRoomBedMaster.class, id);
    }
}
















