/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PhdResearchRegistrationIDAO;
import com.jilit.irp.persistence.dto.PhdResearchRegistration;
import com.jilit.irp.persistence.dto.PhdResearchRegistrationId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author ashok.singh
 */
public class PhdResearchRegistrationDAO extends HibernateDAO implements PhdResearchRegistrationIDAO
{
     private static final Log log = LogFactory.getLog(StudentSubjectChoiceMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PhdResearchRegistration records via Hibernate from the database");
        return this.find("from PhdResearchRegistration as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PhdResearchRegistration.class, id);
    }
}
