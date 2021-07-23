/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.QualificationCategoryIDAO;
import com.jilit.irp.persistence.dto.QualificationCategory;
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
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class QualificationCategoryDAO extends HibernateDAO implements QualificationCategoryIDAO {

    private static final Log log = LogFactory.getLog(QualificationCategoryDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all QualificationCategory records via Hibernate from the database");
        return this.find("from QualificationCategory as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(QualificationCategory.class, id);
    }

    public int checkIfChildExist(final String qualificationcategoryid) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                QualificationCategory qualificationCategory = (QualificationCategory) session.get(QualificationCategory.class, qualificationcategoryid);
                int i1 = 0;//((Integer) session.createFilter(qualificationCategory.getEmployeedetails(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

 
}
