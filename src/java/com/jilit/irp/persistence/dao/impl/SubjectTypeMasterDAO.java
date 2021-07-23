package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SubjectTypeMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.SubjectTypeMaster;
import com.jilit.irp.persistence.dto.SubjectTypeMasterId;
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
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Shimona.Khandelwal
 */
public class SubjectTypeMasterDAO extends HibernateDAO implements SubjectTypeMasterIDAO {

    private static final Log log = LogFactory.getLog(SubjectTypeMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectTypeMaster records via Hibernate from the database");
        return this.find("from SubjectTypeMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all SubjectTypeMaster records via Hibernate from the database");
        return this.find("from SubjectTypeMaster as tname where tname.id.instituteid = ? order by  seqid desc ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectTypeMaster.class, id);
    }

    public List<String> doValidate(final SubjectTypeMaster subjectType, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(SubjectTypeMaster.class);

                criteria.add(Restrictions.eq("id.instituteid", subjectType.getId().getInstituteid()));
                criteria.add(Restrictions.eq("subjecttype", subjectType.getSubjecttype()).ignoreCase());
                if (mode.equals("edit")) {
                    // criteria.add(Restrictions.ne("id.instituteid", subjectType.getId().getInstituteid()));
                    criteria.add(Restrictions.ne("id.subjecttypeid", subjectType.getId().getSubjecttypeid()));
                }

                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        System.out.println("to know trhe count value is " + count.intValue());

        if (count.intValue() > 0) {
            System.out.println("enter in the uerror code");
            errors.add("Same Values Of Subject Type exist");
        }
        return errors;
    }

    public int checkIfChildExist(final SubjectTypeMasterId studentid) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SubjectTypeMaster master = (SubjectTypeMaster) session.get(SubjectTypeMaster.class, studentid);
                int i1 = Integer.parseInt(session.createFilter(master.getBasketmasters(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(master.getBasketmasterdetails(), "select count(*)").list().get(0).toString());
                int i3 = 0;// Integer.parseInt(session.createFilter(master.getSubjectmaster(), "select count(*)").list().get(0).toString());
                int i4 = Integer.parseInt(session.createFilter(master.getOfferedodsubjecttaggings(), "select count(*)").list().get(0).toString());
                return i1 + i2 + i3 + i4;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getSubjectType(String ins_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select   st.id.subjecttypeid,st.subjecttype,st.subjecttypedesc,st.subjecttypedesc  from SubjectTypeMaster st"
                + " where  st.id.instituteid= :ins_id ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAllSubjectType(final String instituteid) {
        String qryString = "select a.id.subjecttypeid, a.subjecttype, a.subjecttypedesc"
                + " from SubjectTypeMaster a "
                + " where a.id.instituteid =:instituteid";
        return getHibernateSession().createQuery(qryString).setParameter("instituteid", instituteid).list();
    }

    @Override
    public List getSubjectTypeCode(String instituteid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select st.id.subjecttypeid, st.subjecttype, st.subjecttypedesc from SubjectTypeMaster st "
                + " where st.id.instituteid =:instituteid and coalesce(st.deactive,'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
