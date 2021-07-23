package com.jilit.irp.persistence.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDAO extends HibernateDaoSupport {


    public Collection<?> findAllActive(String tname, boolean addOrderBySeqId) {
        String s = "from " + tname + " as tname where coalesce(tname.deactive,'N')='N' " + (addOrderBySeqId ? " order by seqid asc " : "");
        return this.find(s);
    }

    public Collection<?> findAllActive(String tname, boolean addOrderBySeqId, String instituteid) {
        String s = "from " + tname + " as tname where tname.id.instituteid='" + instituteid + "' and coalesce(tname.deactive,'N')='N' " + (addOrderBySeqId ? " order by seqid asc " : "");
        return this.find(s);
    }

    public Collection<?> find(String queryString) {
        final List<?> list = getHibernateTemplate().find(queryString);
        return list;
    }

    public Collection<?> find(String queryString, Object[] params) {
        final List<?> list = getHibernateTemplate().find(queryString, params);
        System.err.println("*****" + list.size());
        return list;
    }
    
    public Short getMaxSequenceId(final Object exampleObject) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = session.createCriteria(exampleObject.getClass());
                criteria.setProjection(Projections.projectionList().add(Projections.max("seqid")));
                return criteria.list();
            }
        });
        final Short seqid = list.get(0) == null ? new Short("0") : Short.decode(String.valueOf(list.get(0)));
        return seqid;
    }
    public Short getMaxSequenceIdInstWise(final Object exampleObject,String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = session.createCriteria(exampleObject.getClass());
                criteria.setProjection(Projections.projectionList().add(Projections.max("seqid")));
                  criteria.add(Restrictions.eq("instituteid", instituteid));
                return criteria.list();
            }
        });
        final Short seqid = list.get(0) == null ? new Short("0") : Short.decode(String.valueOf(list.get(0)));
        return seqid;
    }
     public Short getMaxSequenceIdInstPK(final Object exampleObject,String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = session.createCriteria(exampleObject.getClass());
                criteria.setProjection(Projections.projectionList().add(Projections.max("seqid")));
                  criteria.add(Restrictions.eq("id.instituteid", instituteid));
                return criteria.list();
            }
        });
        final Short seqid = list.get(0) == null ? new Short("0") : Short.decode(String.valueOf(list.get(0)));
        return seqid;
    }

    public List getRowCount(final Object exampleObject) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(exampleObject.getClass());
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        });
        return list;
    }

    /**
     * Description: Function has been defined to get max seqid basis of
     * Institute id.
     *
     * @param exampleObject
     * @param instituteid
     * @return
     */

    public void update(Object record) {
            getHibernateTemplate().update(record);
        }

    public void add(Object record) {
            getHibernateTemplate().save(record);
        }
    
    public void delete(Object record) {
            getHibernateTemplate().delete(record);
        }
    
    public void saveOrUpdate(Object record) {
            getHibernateTemplate().saveOrUpdate(record);
        }

    public Session getHibernateSession() {
        return getSession();
    }

}
