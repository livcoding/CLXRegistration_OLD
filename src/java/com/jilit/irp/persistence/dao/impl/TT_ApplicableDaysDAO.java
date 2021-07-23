/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_ApplicableDaysIDAO;
import com.jilit.irp.persistence.dto.TT_ApplicableDays;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Session;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.TT_ApplicableDaysId;
import java.sql.SQLException;
import org.hibernate.HibernateException;

/**
 *
 * @author ashok.singh
 */
public class TT_ApplicableDaysDAO extends HibernateDAO implements TT_ApplicableDaysIDAO {

    private static final Log log = LogFactory.getLog(TT_ApplicableDays.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_ApplicableDays records via Hibernate from the database");
        return this.find("from TT_ApplicableDays as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_ApplicableDays.class, id);
    }

}
