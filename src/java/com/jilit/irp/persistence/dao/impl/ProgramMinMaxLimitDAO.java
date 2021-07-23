/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramMinMaxLimitIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.ProgramMinMaxLimit;
import com.jilit.irp.persistence.dto.ProgramMinMaxLimitId;
import com.jilit.irp.persistence.dto.StudentGroupCrLimit;
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
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author sunny.singhal
 */
public class ProgramMinMaxLimitDAO extends HibernateDAO implements ProgramMinMaxLimitIDAO {

    private static final Log log = LogFactory.getLog(ProgramMinMaxLimitDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramMinMaxLimit records via Hibernate from the database");
        return this.find("from ProgramMinMaxLimit as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramMinMaxLimit.class, id);
    }

    public int checkIfChildExist(final ProgramMinMaxLimitId programMinMaxLimitId) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramMinMaxLimit minMaxLimit = (ProgramMinMaxLimit) session.get(ProgramMinMaxLimit.class, programMinMaxLimitId);
                return 0;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public String doValidate(final ProgramMinMaxLimitId id) {
        String errors = "";
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramMinMaxLimit.class);
                criteria.add(Restrictions.eq("id.instituteid", id.getInstituteid()));
                criteria.add(Restrictions.eq("id.branchid", id.getBranchid()));
                criteria.add(Restrictions.eq("id.programid", id.getProgramid()));
                criteria.add(Restrictions.eq("id.academicyear", id.getAcademicyear()));
                criteria.add(Restrictions.eq("id.stynumber", id.getStynumber()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors = "Duplicate Program Min Max Limit";
        }
        return errors;
    }

    public List getProgramMinMaxLimit(String instituteid, String acad_year) {
        String qryString = "Select  prglmt.id.instituteid,prglmt.id.academicyear,prglmt.id.programid,prglmt.id.branchid,prglmt.id.stynumber,ins.institutecode,pr.programcode,br.branchcode, "
                + " prglmt.sectiontype,prglmt.subsectionrequired,prglmt.minlimit,prglmt.maxlimit,prglmt.passinglimit,prglmt.programscheme, "
                + " prglmt.studentsubjectfacultychoice,prglmt.prerequisiteacademicwise,prglmt.deactive,prglmt.sectionmaxstudent,prglmt.cumulative,prglmt.infoupdateallowformdate,prglmt.infoupdateallowtodate "
                + " from  ProgramMinMaxLimit prglmt,ProgramMaster pr, BranchMaster br,InstituteMaster ins  "
                + " where prglmt.id.instituteid='" + instituteid + "'  and prglmt.id.academicyear= '" + acad_year + "' and prglmt.id.programid=pr.id.programid  and prglmt.id.branchid=br.id.branchid and prglmt.id.instituteid=ins.id.instituteid ";
        return getHibernateTemplate().find(qryString);

    }

    @Override
    public List getProgramMinMaxLimitEdit(String instituteid, String acad_year, String branchid, String programid, String stynumber) {
        String qryString = "Select  prglmt.id.instituteid,prglmt.id.academicyear,prglmt.id.programid,prglmt.id.branchid,prglmt.id.stynumber,ins.institutecode,pr.programcode,br.branchcode, "
                + " prglmt.sectiontype,prglmt.subsectionrequired,prglmt.minlimit,prglmt.maxlimit,prglmt.passinglimit,prglmt.programscheme, "
                + " prglmt.studentsubjectfacultychoice,prglmt.prerequisiteacademicwise,prglmt.deactive,prglmt.sectionmaxstudent,prglmt.cumulative,prglmt.infoupdateallowformdate,prglmt.infoupdateallowtodate,prglmt.subsectionmaxstudent "
                + " from  ProgramMinMaxLimit prglmt,ProgramMaster pr, BranchMaster br,InstituteMaster ins  "
                + " where prglmt.id.instituteid='" + instituteid + "'  and prglmt.id.academicyear= '" + acad_year + "' and prglmt.id.programid=pr.id.programid  and prglmt.id.branchid=br.id.branchid and prglmt.id.instituteid=ins.id.instituteid "
                + " and prglmt.id.branchid = '" + branchid + "'  and prglmt.id.programid = '" + programid + "'  and prglmt.id.stynumber = '" + stynumber + "' ";
        return getHibernateTemplate().find(qryString);

    }

    public List getSemester(String branchid, String acad_year, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" Select  prg.id.instituteid,prg.id.programid,prg.id.branchid,prg.id.academicyear,prg.startsty,prg.endsty  from ProgramMaxSty prg where prg.id.academicyear= :academicyear and  prg.id.branchid = :branchid and  prg.id.instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("branchid", branchid).
                    setParameter("academicyear", acad_year).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStynoForPST(String instituteid, String registrationid, String academicyear, String programid, String branchid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sr.stynumber from StudentRegistration_info sr "
                + " where sr.id.instituteid=:instituteid "
                + " and sr.id.registrationid=:registrationid "
                + " and sr.academicyear=:academicyear"
                + " and sr.programid=:programid"
                + " and sr.branchid=:branchid"
                + " order by sr.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStynoForPST_TeachingSchem(String instituteid, String academicyear, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sr.stynumber from ProgramSchemeAcadyearWise sr "
                + " where sr.id.instituteid=:instituteid "
                + " and sr.academicyear=:academicyear"
                + " and sr.programid=:programid"
                + " order by sr.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public String insertProgramMinMaxLimit(final List<ProgramMinMaxLimit> programMinMaxLimitList) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            System.err.println("*********** in transaction " + programMinMaxLimitList.size());
            for (int i = 0; i < programMinMaxLimitList.size(); i++) {
                System.err.println("************* value" + i);
                session.save((ProgramMinMaxLimit) programMinMaxLimitList.get(i));
            }
            retList = "Data Save Successfully";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public String updateProgramMinMaxLimit(final List<ProgramMinMaxLimit> programMinMaxLimitList) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            System.err.println("*********** in transaction " + programMinMaxLimitList.size());
            for (int i = 0; i < programMinMaxLimitList.size(); i++) {
                System.err.println("************* value" + i);
                session.update((ProgramMinMaxLimit) programMinMaxLimitList.get(i));
            }

            retList = "Data updated Successfully !";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public Collection<?> getStudentMinMaxCradits(final String clientid, final String groupid, final String branchid, final String programid, final String stynumber) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentGroupCrLimit.class, "master");
                criteria.add(Restrictions.eq("master.id.clientid", clientid));
                criteria.add(Restrictions.eq("master.id.groupid", groupid));
                criteria.add(Restrictions.eq("master.branchid", branchid));
                criteria.add(Restrictions.eq("master.programid", programid));
                criteria.add(Restrictions.eq("master.stynumber", stynumber));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.mincredit").as("maxcredit")).add(Projections.property("master.maxcredit").as("mincredit")));
                //criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            }
        });
        return list;

    }

    public Collection<?> getMinMaxLimt(final String instituteid, final String branchid, final String programid, final byte stynumber, final String academicyear) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ProgramMinMaxLimit.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.branchid", branchid));
                criteria.add(Restrictions.eq("master.id.branchid", branchid));
                criteria.add(Restrictions.eq("master.id.programid", programid));
                criteria.add(Restrictions.eq("master.id.stynumber", stynumber));
                criteria.add(Restrictions.eq("master.id.academicyear", academicyear));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.minlimit").as("minlimit")).add(Projections.property("master.maxlimit").as("maxlimit")));
                //criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            }
        });
        return list;

    }
}
