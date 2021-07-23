/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.EmpDetailIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.EmpDetail;
import com.jilit.irp.persistence.dto.EmpDetailId;
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
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author saroj.khuntia
 */
public class EmpDetailDAO extends HibernateDAO implements EmpDetailIDAO {

     private static final Log log = LogFactory.getLog(EmpDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all EmpDetail records via Hibernate from the database");
        return this.find("from EmpDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(EmpDetail.class, id);
    }

//    public List doValidate(final EmpDetail master, final String mode) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(EmpDetail.class);
//                criteria.add(Restrictions.eq("id.instituteid", master.getId().getInstituteid()));
//                criteria.add(Restrictions.ne("id.empid", master.getId().getEmpid()).ignoreCase());
//                criteria.add(Restrictions.eq("empcode", master.getEmpcode()).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
//
//        if (count.intValue() > 0) {
//            errors.add("Same Values Of Room Code Exist");
//        }
//        return errors;
//
//    }
//
//  /*  public int checkIfChildExist(final EmpDetailId id) {
//
//        HibernateCallback callback = new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                EmpDetail empdet = (EmpDetail) session.get(EmpDetail.class, id);
//                int i1 = ((Integer) session.createFilter(empdet.getProgramschemes(), "select count(*)").list().get(0)).intValue();
//                return i1;
//            }
//        };
//
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    } */
//
//    public List getEmpcode(final String instituteid) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(EmpDetail.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
//                criteria.add(Restrictions.eq("master.deactive", "N"));
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//    public List checkIfElectiveCodeExists(final String instituteid, final String empcode) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(EmpDetail.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
//                criteria.add(Restrictions.eq("master.deactive", "N"));
//                criteria.add(Restrictions.eq("master.empcode", empcode).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.empid").as("empid")).add(Projections.property("master.empcode").as("empcode")).add(Projections.property("master.empname").as("empname")));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//    }
}
