/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

/**
 *
 * @author ashok.singh
 */
import com.jilit.irp.persistence.dao.CoordinatorTypeIDAO;
import com.jilit.irp.persistence.dto.CoordinatorTypeId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CoordinatorType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import javax.management.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CoordinatorTypeDAO extends HibernateDAO implements CoordinatorTypeIDAO {

    private static final Log log = LogFactory.getLog(CoordinatorTypeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CoordinatorType records via Hibernate from the database");
        return this.find("from CoordinatorType as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CoordinatorType.class, id);

    }

    public List getGridData(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("select ct from CoordinatorType ct "
                    + " where ct.id.instituteid = :instituteid order by ct.seqid desc");
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getLastCTypeId(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("select max(ct.id.coordinatortypeid),max(ct.seqid) from CoordinatorType ct where ct.id.instituteid= :instituteid ");
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> doValidate(final CoordinatorType coType, final String mode) {
        System.out.println("Inside do validate methods");
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (!mode.equals("update")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(CoordinatorType.class);
                    criteria.add(Restrictions.eq("coordinatortypecode", coType.getCoordinatortypecode()).ignoreCase());//unique column restriction in table
                    criteria.add(Restrictions.eq("id.instituteid", coType.getId().getInstituteid()).ignoreCase());//unique column restriction in table
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);

            if (count.intValue() > 0) {
                errors.add("Coordinator Type Code already exists");
            }
        }
        if (!mode.equals("update")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(CoordinatorType.class);
                    criteria.add(Expression.eq("coordinatortype", coType.getCoordinatortype()).ignoreCase());//unique column restriction in table
                    criteria.add(Restrictions.eq("id.instituteid", coType.getId().getInstituteid()).ignoreCase());//unique column restriction in table
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);

            if (count.intValue() > 0) {
                errors.add("Coordinator Type already exists");
            }
        }

        if (mode.equals("update")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(CoordinatorType.class);
                    criteria.add(Restrictions.ne("id.coordinatortypeid", coType.getId().getCoordinatortypeid()).ignoreCase());
                    criteria.add(Restrictions.eq("coordinatortypecode", coType.getCoordinatortypecode()).ignoreCase());//unique column restriction in table
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Coordinator Type Code already exists");
            }

        }
        return errors;
    }
//    public int checkIfChildExist(final CoordinatorTypeId coordinatorTypeId) {
//        HibernateCallback callback = new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                CoordinatorType coordinatorType = (CoordinatorType) session.get(CoordinatorType.class,  coordinatorTypeId);
//                int i2 = ((Integer) session.createFilter(coordinatorType.getFstwisecoordinators(), "select count(*)").list().get(0)).intValue();
//                int i4 = ((Integer) session.createFilter(coordinatorType.getCoordinatornamestudentwises(), "select count(*)").list().get(0)).intValue();
//                return  i2 + i4  ;
//               // return 0;
//            }
//        };
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    }
}
