/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.MentorMasterIDAO;
import com.jilit.irp.persistence.dto.MentorMaster;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import org.hibernate.Transaction;

/**
 *
 * @author akshya.gaur
 */
public class MentorMasterDAO extends HibernateDAO implements MentorMasterIDAO {

    private static final Log log = (Log) LogFactory.getLog(MentorMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all LocationMaster records via Hibernate from the database");
        return this.find("from LocationMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(MentorMaster.class, id);
    }
}
