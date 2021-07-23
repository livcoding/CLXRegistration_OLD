package com.jilit.irp.persistence.dao.impl;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jilit.irp.persistence.dao.PRSubjectTypeWiseApprovalIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PRSubjectTypeWiseApproval;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author singh.jaswinder
 */
public class PRSubjectTypeWiseApprovalDAO extends HibernateDAO implements PRSubjectTypeWiseApprovalIDAO {

    private static final Log log = LogFactory.getLog(PRSubjectTypeWiseApproval.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PRSubjectTypeWiseApproval records via Hibernate from the database");
        return this.find("from PRSubjectTypeWiseApproval as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRSubjectTypeWiseApproval.class, id);
    }

}
