/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SubjectComponentIDAO;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectTypeMaster;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.SubjectComponentDetail;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 *
 * @author akshya.gaur
 */
public class SubjectComponentDAO extends HibernateDAO implements SubjectComponentIDAO {

    private static final Log log = LogFactory.getLog(SubjectComponentDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectComponent records via Hibernate from the database");
        return this.find("from SubjectComponent as tname");
    }

    public Collection<?> findAll(final String instituteid) {
        log.info("Retrieving all SubjectComponent records via Hibernate from the database");
        return this.find("from SubjectComponent as tname where tname.id.instituteid = ? and coalesce(tname.deactive,'N')='N' ", new Object[]{instituteid});
    }

    public Collection<?> findAllWithDeactive(final String instituteid) {
        log.info("Retrieving all SubjectComponent records via Hibernate from the database");
        return this.find("from SubjectComponent as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Collection<?> findAll(String subjectid, String instituteid) {
        log.info("Retrieving all SubjectComponentDetail records via Hibernate from the database");
        return this.find("from SubjectComponentDetail as tname where tname.id.subjectid='" + subjectid + "' and tname.id.instituteid='" + instituteid + "' ");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectComponent.class, id);
    }

    public List<String> doValidate(final SubjectComponent subjectComponent, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(SubjectComponent.class);
                criteria.add(Restrictions.eq("subjectcomponentcode", subjectComponent.getSubjectcomponentcode()).ignoreCase());
                criteria.add(Expression.eq("id.instituteid", subjectComponent.getId().getInstituteid()));
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.subjectcomponentid", subjectComponent.getId().getSubjectcomponentid()));
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Subject component code already exists");
        }
        return errors;
    }

    public int checkIfChildExist(final SubjectComponentId studentid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SubjectComponent master = (SubjectComponent) session.get(SubjectComponent.class, studentid);
                int i1 = Integer.parseInt(session.createFilter(master.getProgramsubjectdetails(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(master.getProgramschemeacadyeardetails(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(master.getProgramschemedetails(), "select count(*)").list().get(0).toString());
                int i4 = Integer.parseInt(session.createFilter(master.getFacultysubjecttaggings(), "select count(*)").list().get(0).toString());
                int i5 = Integer.parseInt(session.createFilter(master.getStudentsubjectchoicedetails(), "select count(*)").list().get(0).toString());
                int i6 = 0;
                int i7 = Integer.parseInt(session.createFilter(master.getPrfacultysubjectchoiceses(), "select count(*)").list().get(0).toString());
                int i8 = Integer.parseInt(session.createFilter(master.getPrfacultydaytimepreferences(), "select count(*)").list().get(0).toString());
                int i9 = Integer.parseInt(session.createFilter(master.getPrfacultysubjecttimepreferences(), "select count(*)").list().get(0).toString());
                int i10 = Integer.parseInt(session.createFilter(master.getOfferedodsubjecttaggingdetails(), "select count(*)").list().get(0).toString());
                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getSubjectComponentCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(" select sc.id.subjectcomponentid, sc.subjectcomponentcode, sc.subjectcomponentdesc from SubjectComponent sc "
                    + " where sc.id.instituteid = :instituteid and coalesce(sc.deactive,'N')='N' order by sc.subjectcomponentdesc ");
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getComponentIdOfSubject(String instituteid, String subjectid) {
        List l = new ArrayList();
        String qryString = "select sc.id.subjectcomponentid, sc.subjectcomponentcode, sc.subjectcomponentdesc, scd.ltppassingmarks,scd.totalccpmarks,scd.noofhours,scd.noofclassinaweek,scd.totalclasses"
                + " from SubjectComponent sc, SubjectComponentDetail scd"
                + " where sc.id.instituteid = '" + instituteid + "'"
                + " and sc.id.instituteid = scd.id.instituteid "
                + " and sc.id.subjectcomponentid = scd.id.subjectcomponentid "
                + " and scd.id.subjectid = '" + subjectid + "'";
        try {
            l = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public List getComponentIdOfSubjectForAddDrop(String instituteid, String subjectid, String registrationid) {
        List l = new ArrayList();
        String qryString = "select distinct sc.id.subjectcomponentid, sc.subjectcomponentcode, sc.subjectcomponentdesc, scd.ltppassingmarks,scd.totalccpmarks,scd.noofhours,scd.noofclassinaweek,scd.totalclasses"
                + " from SubjectComponent sc, ProgramSubjectTagging pst,ProgramSubjectDetail scd"
                + " where sc.id.instituteid = '" + instituteid + "'"
                + " and sc.id.instituteid = scd.id.instituteid "
                + " and sc.id.subjectcomponentid = scd.id.subjectcomponentid "
                + " and pst.subjectid = '" + subjectid + "'"
                + " and pst.id.instituteid=scd.id.instituteid and pst.id.programsubjectid=scd.id.programsubjectid and pst.id.registrationid=scd.id.registrationid and pst.id.registrationid='" + registrationid + "'";
        try {
            l = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public String getComponentIdByCode(String instituteid, String subjectcomponentcode) {
        List l = new ArrayList();
        String value = "";
        String qryString = "select sc.id.subjectcomponentid from SubjectComponent sc"
                + " where sc.id.instituteid = '" + instituteid + "'"
                + " and sc.subjectcomponentcode = '" + subjectcomponentcode + "'";
        try {
            l = getHibernateTemplate().find(qryString);
            if (l.size() > 0) {
                value = l.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Object findByPrimaryKey1(Serializable id) {
        return getHibernateTemplate().get(SubjectComponentDetail.class, id);
    }

}
