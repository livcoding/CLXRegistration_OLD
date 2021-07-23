/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.EmployeeAcademicDutyIDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.EmployeeAcademicDuty;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class EmployeeAcademicDutyDAO extends HibernateDAO implements EmployeeAcademicDutyIDAO {

    private static final Log log = LogFactory.getLog(EmployeeAcademicDutyDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all EmployeeAcademicDuty records via Hibernate from the database");
        return this.find("from EmployeeAcademicDuty as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(EmployeeAcademicDuty.class, id);
    }

    public List getEmployeeAcademicDutyList(final String employeeid){
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(EmployeeAcademicDuty.class, "master");
                criteria.add(Restrictions.eq("master.id.employeeid", employeeid));
                return criteria.list();
            }
        });
        return list;
    }



}
