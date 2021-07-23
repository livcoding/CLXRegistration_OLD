/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SubjectAreaMasterIDAO;
import com.jilit.irp.persistence.dto.SubjectAreaMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class SubjectAreaMasterDAO extends HibernateDAO implements SubjectAreaMasterIDAO {

    private static final Log log = LogFactory.getLog(SubjectAreaMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectAreaMaster records via Hibernate from the database");
        return this.find("from SubjectAreaMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectAreaMaster.class, id);
    }
    public int checkIfChildExist(final String qualificationid) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SubjectAreaMaster subjectAreaMaster = (SubjectAreaMaster) session.get(SubjectAreaMaster.class, qualificationid);
                int i1 = ((Integer) session.createFilter(subjectAreaMaster.getDepartmentsubjecttaggings(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

}