
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ProgramSchemeAcadyearWiseIDAO;
import com.jilit.irp.persistence.dto.BasketMaster;
import com.jilit.irp.persistence.dto.ProgramScheme;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWise;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.*;
import org.hibernate.type.Type;

/**
 *
 * @author subrata.lohar
 */
public class ProgramSchemeAcadyearWiseDAO extends HibernateDAO implements ProgramSchemeAcadyearWiseIDAO {

    private static final Log log = LogFactory.getLog(ProgramSchemeAcadyearWiseDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramSchemeAcadyearWise records via Hibernate from the database");
        return this.find("from ProgramSchemeAcadyearWise as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramSchemeAcadyearWise.class, id);
    }

//    public void update(Object record) {
//        getHibernateTemplate().update((ProgramSchemeAcadyearWise) record);
//    }
//
//     public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((ProgramSchemeAcadyearWise) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((ProgramSchemeAcadyearWise) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((ProgramSchemeAcadyearWise) record);
//    }
//
//
    public List<String> doValidate(final ProgramSchemeAcadyearWise programSchemeAcadyearWise, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
//        System.err.println("DOVALIDATE:::::::::::::::::::::::::::::::::::::::::::::::");
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramSchemeAcadyearWise.class);
                criteria.add(Restrictions.eq("id.instituteid", programSchemeAcadyearWise.getId().getInstituteid()));
                criteria.add(Restrictions.eq("academicyear", programSchemeAcadyearWise.getAcademicyear()).ignoreCase());
                criteria.add(Restrictions.eq("programid", programSchemeAcadyearWise.getProgramid()).ignoreCase());
                criteria.add(Restrictions.eq("basketid", programSchemeAcadyearWise.getBasketid()).ignoreCase());
                criteria.add(Restrictions.eq("sectionid", programSchemeAcadyearWise.getSectionid()).ignoreCase());
                criteria.add(Restrictions.eq("stynumber", programSchemeAcadyearWise.getStynumber()));
                criteria.add(Restrictions.eq("subjectid", programSchemeAcadyearWise.getSubjectid()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.programschemeacadwiseid", programSchemeAcadyearWise.getId().getProgramschemeacadwiseid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("This Subject is already exists in this Basket! ");
        }
        return errors;
    }

// public List getBasketId(final String instituteid, final String academicyear, final String programid, final String sectionid, final String subjectid)
//    {
//       List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                List l = null;
//                Criteria criteria = session.createCriteria(ProgramSchemeAcadyearWise.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
//                criteria.add(Restrictions.eq("master.academicyear", academicyear));
//                criteria.add(Restrictions.eq("master.programid", programid));
//                criteria.add(Restrictions.eq("master.sectionid", sectionid));
//                criteria.add(Restrictions.eq("master.subjectid", subjectid));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.basketid")).add(Projections.groupProperty("master.basketid")));
//                l = criteria.list();
//                return l;
//            }
//        });
//
//        return list;
//    }
//
//    public List getTeachingSchemeSubjectWiseFeeReportData(final String instituteid, final String programid, final String branchid, final String academicyear, final String stynumber) {
//        List list = null;
//        String queryString = "select p.academicyear,b.branchcode,p.stynumber,sm.subjectcode,sm.subjectdesc,p.credits,p.regfeeamount,s.sectioncode" +
//                " from ProgramSchemeAcadyearWise  p ,BranchMaster b ,SubjectMaster sm ,SectionMaster s where" +
//                "  b.id.instituteid=p.id.instituteid" +
//                " and b.id.programid=p.programid" +
//                " and sm.id.subjectid=p.subjectid" +
//                " and sm.id.instituteid=p.id.instituteid" +
//                " and s.id.instituteid=p.id.instituteid" +
//                " and s.id.sectionid=p.sectionid" +
//                " and p.id.instituteid='" + instituteid + "'  " ;
//                 if (!academicyear.equals("All")) {
//                   queryString=queryString+ " and p.academicyear='" + academicyear + "' " ;
//                 }
//                 if (!programid.equals("All")) {
//                   queryString=queryString+ " and p.programid='" + programid + "'"  ;
//                 }
//                 if (!branchid.equals("All")) {
//                   queryString=queryString+ " and b.id.branchid='" + branchid + "' " ;
//                 }
//                         queryString=queryString+" and p.stynumber='" + stynumber + "' " +
//                                 " group by p.academicyear,b.branchcode,p.stynumber,sm.subjectcode,sm.subjectdesc,p.credits,p.regfeeamount,s.sectioncode";
//
//        try {
//            list = getHibernateTemplate().find(queryString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    @Override
    public List getTeachingSchemeEditData(String instituteId, String programSchemeAcadWiseId) {
        List l = null;
        try {
            String queryString = "select psa.id.programschemeacadwiseid,psa.programid,psa.sectionid,psa.stynumber,psa.basketid,psa.subjectid,psa.departmentid,"
                    + " psa.deactive,psa.credits,psa.totalmarks,psa.passingmarks,psa.electiveid,psa.regfeeamount,sm.subjectflag,psa.auditsubject"
                    + " from ProgramSchemeAcadyearWise  psa,SubjectMaster sm where psa.id.instituteid=:institute and psa.id.programschemeacadwiseid=:prgSchAcId and sm.id.instituteid=psa.id.instituteid and sm.id.subjectid=psa.subjectid ";
            l = getHibernateSession().createQuery(queryString)
                    .setParameter("institute", instituteId)
                    .setParameter("prgSchAcId", programSchemeAcadWiseId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public List getTeachingSubjectComponentEditData(String instituteId, String programSchemeAcadWiseId) {
        List l = null;
        try {
            String queryString = "select psd.id.subjectcomponentid,psd.totalccpmarks,psd.noofhours,psd.noofclassinaweek,psd.totalclasses,psd.deactive"
                    + " from ProgramSchemeAcadYearDetail psd where psd.id.instituteid=:institute and psd.id.programschemeacadwiseid=:prgSchAcId ";
            l = getHibernateSession().createQuery(queryString)
                    .setParameter("institute", instituteId)
                    .setParameter("prgSchAcId", programSchemeAcadWiseId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
}
