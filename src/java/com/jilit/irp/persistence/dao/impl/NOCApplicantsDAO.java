/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


import com.jilit.irp.persistence.dto.NOCApplicants;
import com.jilit.irp.persistence.dto.NOCApplicantsId;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.NOCApplicantsIDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;



/**
 *
 * @author pankaj.kumar
 */
public class NOCApplicantsDAO extends HibernateDAO implements NOCApplicantsIDAO{
    private static final Log log = LogFactory.getLog(NOCApplicantsDAO.class);

    public int checkIfChildExist(NOCApplicantsId Id) {

        return 0;
    }

    public List doValidate(final NOCApplicants nocApp, String mode) {
        System.out.println("Inside do validate methods");
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(NOCApplicants.class);
                criteria.add(Restrictions.eq("id.registrationid", nocApp.getId().getRegistrationid()));
                criteria.add(Restrictions.eq("id.studentid", nocApp.getId().getStudentid()));
                criteria.add(Restrictions.eq("id.instituteid", nocApp.getId().getInstituteid()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (mode.equals("edit")) {
            if (count.intValue() == 0) {
                errors.add("Applicant not found for the selected Registration code");
            }
        } else {
            if (count.intValue() > 0) {
                errors.add("Applicant already exist for the selected Registration code");
            }
        }
        return errors;

    }
     public Collection<?> findAll() {
        log.info("Retrieving all NOCApplicants records via Hibernate from the database");
        return this.find("from NOCApplicants as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(NOCApplicants.class, id);
    }
}
