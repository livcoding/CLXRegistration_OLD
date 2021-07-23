/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dto.ProgramEligibilityExams;
import com.jilit.irp.persistence.dao.ProgramEligibilityExamsIDAO;
import com.jilit.irp.persistence.dto.ProgramEligibilityExamsId;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author deepak.gupta
 */
public class ProgramEligibilityExamsDAO extends HibernateDAO implements ProgramEligibilityExamsIDAO {

    private static final Log log = LogFactory.getLog(ProgramEligibilityExamsIDAO.class);
    Session session1 = null;
    Transaction tx1 = null;

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramEligibilityExams.class, id);
    }

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramEligibilityExams records via Hibernate from the database");
        return this.find("from ProgramEligibilityExams as tname");
    }
}
