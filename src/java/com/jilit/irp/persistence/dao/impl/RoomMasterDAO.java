/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RoomMasterIDAO;
import java.util.Collection;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.RoomMaster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author suman.saurabh
 */
/*public class RoomMasterDAO  {

}*/
public class RoomMasterDAO extends HibernateDAO implements RoomMasterIDAO {

    private static final Log log = LogFactory.getLog(RegistrationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RoomMaster records via Hibernate from the database");
        return this.find("from RoomMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RoomMaster.class, id);
    }
}
