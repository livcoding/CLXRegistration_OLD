/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.RollNumberingSetupDetailIDAO;
import com.jilit.irp.persistence.dto.RollNumberingSetupDetail;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class RollNumberingSetupDetailDAO extends HibernateDAO implements RollNumberingSetupDetailIDAO {

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RollNumberingSetupDetail.class, id);
    }

    public Collection<?> getEnrollmentFormatReport(final String[] programid, final String[] branchid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(RollNumberingSetupDetail.class, "master").createAlias("master.rollnumberingsetup", "roll").setFetchMode("rollnumberingsetup", FetchMode.JOIN).createAlias("master.programmaster", "pm").setFetchMode("programmaster", FetchMode.JOIN);
                criteria.createAlias("master.branchmaster", "bm").setFetchMode("branchmaster", FetchMode.JOIN);
                criteria.createAlias("bm.departmentbranchtaggings", "de").setFetchMode("departmentbranchtaggings", FetchMode.JOIN);
                criteria.createAlias("de.departmentmaster", "dept").setFetchMode("departmentmaster", FetchMode.JOIN);
                criteria.add(Expression.in("pm.id.programid", programid)).add(Expression.in("bm.id.branchid", branchid));
                criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("pm.programcode").as("programcode")).add(Projections.property("dept.departmentcode").as("departmentcode")).add(Projections.property("pm.programdesc").as("programdesc"))));
                criteria.addOrder(Order.asc("pm.programcode"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            } 
        });
        return list;
    }

    public List getGroupidForEnrollNumber(String inid, String prid, String bmid, String acyr, String pmtyid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select temproll.id.groupid from TempRollNumberSetupDetail  temproll "
                + " where temproll.id.instituteid =:inid and temproll.id.programid =:prid and temproll.id.branchid =:bmid "
                + " and temproll.id.programtypeid =:pmtyid and temproll.id.academicyear =:acyr ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("inid", inid).setParameter("prid", prid).setParameter("bmid", bmid).
                    setParameter("acyr", acyr).setParameter("pmtyid", pmtyid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

}
