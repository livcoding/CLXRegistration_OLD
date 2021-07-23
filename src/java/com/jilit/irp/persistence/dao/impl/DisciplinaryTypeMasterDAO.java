/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.DisciplinaryTypeMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.DisciplinaryTypeMaster;

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
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class DisciplinaryTypeMasterDAO extends HibernateDAO implements DisciplinaryTypeMasterIDAO
{

    private static final Log log = LogFactory.getLog(DisciplinaryTypeMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all DisciplinaryTypeMaster records via Hibernate from the database");
        return this.find("from DisciplinaryTypeMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(DisciplinaryTypeMaster.class, id);
    }

    public List doValidate(final DisciplinaryTypeMaster disciplinarytype) {

        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(DisciplinaryTypeMaster.class);
                criteria.add(Restrictions.eq("disciplinarytypecode", disciplinarytype.getDisciplinarytypecode()).ignoreCase());
                criteria.add(Restrictions.ne("disciplinarytypeid", disciplinarytype.getDisciplinarytypeid()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Disciplinary Type Code Already exist");
        }
        return errors;
    }
//
    public int checkIfChildExist(final DisciplinaryTypeMaster id) {
       HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DisciplinaryTypeMaster disciplinaryTypeMaster = (DisciplinaryTypeMaster) session.get(DisciplinaryTypeMaster.class, id.getDisciplinarytypeid());
                int i1 = Integer.parseInt(session.createFilter(disciplinaryTypeMaster.getStudentdisciplinaryactions(), "select count(*)").list().get(0).toString());
                return i1;
           }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
