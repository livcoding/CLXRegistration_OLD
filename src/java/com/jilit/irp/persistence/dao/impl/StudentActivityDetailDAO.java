/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentActivityDetailIDAO;
import com.jilit.irp.persistence.dto.StudentActivityDetail;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chetna.pargai
 */
public class StudentActivityDetailDAO extends HibernateDAO implements StudentActivityDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentActivityDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentActivityDetail records via Hibernate from the database");
        return this.find("from StudentActivityDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentActivityDetail.class, id);
    }

    @Override
    public List getAllStyNumberWithAllOptionLOV(String instituteid) {
        String qryString = " select sd.id.stynumber, sd.stydesc "
                + " from StyDesc sd "
                + " where sd.id.instituteid = '" + instituteid + "' ";
        return getHibernateTemplate().find(qryString);
    }
}
