/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ElectiveSubjectApprovalIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ElectiveSubjectApproval;
import com.jilit.irp.persistence.dto.PRSubjectTypeWiseApproval;
import com.jilit.irp.util.JIRPDBUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class ElectiveSubjectApprovalDAO extends HibernateDAO implements ElectiveSubjectApprovalIDAO {

    private static final Log log = LogFactory.getLog(ElectiveSubjectApprovalDAO.class);
    
    @Override
 public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ElectiveSubjectApproval.class, id);
    }
    @Override
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
