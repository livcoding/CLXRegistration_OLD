/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.BranchChangeRequestIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.BranchChangeRequest;
import com.jilit.irp.persistence.dto.StudentMaster;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author rinkal.gupta
 */
public class BranchChangeRequestDAO extends HibernateDAO implements BranchChangeRequestIDAO{

    private static final Log log = LogFactory.getLog(BranchChangeRequestDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all BranchChangeRequest records via Hibernate from the database");
        return this.find("from BranchChangeRequest as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(BranchChangeRequest.class, id);
    }
}
