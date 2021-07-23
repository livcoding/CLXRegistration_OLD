/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramWiseSubsectionIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramWiseSubsection;
import com.jilit.irp.persistence.dto.ProgramWiseSubsectionId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
public class ProgramWiseSubsectionDAO extends HibernateDAO implements ProgramWiseSubsectionIDAO {

    private static final Log log = LogFactory.getLog(ProgramWiseSubsectionIDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramWiseSubsection records via Hibernate from the database");
        return this.find("from ProgramWiseSubsection as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramWiseSubsection.class, id);
    }

    @Override
    public List getSubSectionList(String instituteid, String sectionid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct ps.id.subsectionid, ps.subsectioncode, ps.subsectiondesc from ProgramWiseSubsection ps ");
        qry.append(" where ps.id.instituteid= :instituteid ");
        qry.append(" and ps.id.sectionid= :sectionid ");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("sectionid", sectionid).list();

        return list;
    }

    @Override
    public List getSectionList(String instituteid, String programid, String branchid, String academicyear, byte stynumber) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pwss.id.sectionid,scm.sectioncode "
                + " from ProgramWiseSubsection pwss,SectionMaster scm"
                + " where pwss.id.instituteid=:instituteid"
                + "  and pwss.id.programid=:programid "
                + " and pwss.branchid=:branchid"
                + "  and pwss.id.academicyear=:academicyear"
                + "  and pwss.id.stynumber=:stynumber"
                + "  and scm.id.instituteid=pwss.id.instituteid and scm.id.sectionid=pwss.id.sectionid and coalesce(scm.deactive,'N')='N'"
                + " order by scm.sectioncode");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("programid", programid)
                .setParameter("branchid", branchid)
                .setParameter("academicyear", academicyear)
                .setParameter("stynumber", stynumber).list();

        return list;
    }

    @Override
    public List getSectionListMinStudentWise(String instituteid, String programid, String branchid, String academicyear, byte stynumber) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pwss.id.sectionid,scm.sectioncode,(select count(stm.studentid) from StudentMaster stm where stm.instituteid=pwss.id.instituteid and stm.acadyear=pwss.id.academicyear and stm.programid=pwss.id.programid"
                + " and stm.branchid=pwss.branchid and stm.sectionid=pwss.id.sectionid and stm.subsectionid = pwss.id.subsectionid and stm.stynumber=pwss.id.stynumber and coalesce(stm.activestatus,'A')='A')as studentcount"
                + " from ProgramWiseSubsection pwss,SectionMaster scm"
                + " where pwss.id.instituteid=:instituteid"
                + "  and pwss.id.programid=:programid "
                + " and pwss.branchid=:branchid"
                + "  and pwss.id.academicyear=:academicyear"
                + "  and pwss.id.stynumber=:stynumber"
                + "  and scm.id.instituteid=pwss.id.instituteid and scm.id.sectionid=pwss.id.sectionid and coalesce(scm.deactive,'N')='N'"
                + " order by scm.sectioncode");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("programid", programid)
                .setParameter("branchid", branchid)
                .setParameter("academicyear", academicyear)
                .setParameter("stynumber", stynumber).list();

        return list;
    }

    @Override
    public List getSubSectionList(String instituteid, String programid, String branchid, String academicyear, String sectionid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pwss.id.stynumber "
                + " from ProgramWiseSubsection pwss,SectionMaster sm"
                + " where pwss.id.instituteid=:instituteid and sm.id.instituteid=pwss.id.instituteid and sm.id.sectionid=pwss.id.sectionid"
                + "  and pwss.id.programid=:programid "
                + " and pwss.branchid=:branchid"
                + "  and pwss.id.academicyear=:academicyear"
                + " and pwss.id.sectionid=:sectionid and coalesce(pwss.deactive,'N')='N'"
                + " order by pwss.id.stynumber");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("programid", programid)
                    .setParameter("branchid", branchid)
                    .setParameter("academicyear", academicyear)
                    .setParameter("sectionid", sectionid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubSectionListMinStudentWise(String instituteid, String programid, String branchid, String academicyear, byte stynumber, String sectionid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pwss.id.subsectionid,pwss.subsectioncode,pwss.maxstudent,sm.sectioncode,(select count(stm.studentid) from StudentMaster stm where stm.instituteid=pwss.id.instituteid and stm.acadyear=pwss.id.academicyear and stm.programid=pwss.id.programid"
                + " and stm.branchid=pwss.branchid and stm.sectionid=pwss.id.sectionid and stm.subsectionid = pwss.id.subsectionid and stm.stynumber=pwss.id.stynumber and coalesce(stm.activestatus,'A')='A')as studentcount"
                + " from ProgramWiseSubsection pwss,SectionMaster sm"
                + " where pwss.id.instituteid=:instituteid and sm.id.instituteid=pwss.id.instituteid and sm.id.sectionid=pwss.id.sectionid"
                + "  and pwss.id.programid=:programid "
                + " and pwss.branchid=:branchid"
                + "  and pwss.id.academicyear=:academicyear"
                + "  and pwss.id.stynumber=:stynumber"
                + " and pwss.id.sectionid=:sectionid and coalesce(pwss.deactive,'N')='N'");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("programid", programid)
                .setParameter("branchid", branchid)
                .setParameter("academicyear", academicyear)
                .setParameter("stynumber", stynumber)
                .setParameter("sectionid", sectionid).list();

        return list;
    }

    @Override
    public List getSubSectionCount(String instituteid, String programid, String branchid, String academicyear, byte stynumber, String Sectionid, String subsectionid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append("  select count(*) from StudentMaster sm where sm.instituteid=:instituteid and sm.programid=:programid and sm.branchid=:branchid and sm.sectionid=:sectionid "
                + " and sm.acadyear=:academicyear and sm.subsectionid=:subsectionid and sm.stynumber=:stynumber");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("programid", programid)
                .setParameter("branchid", branchid)
                .setParameter("academicyear", academicyear)
                .setParameter("stynumber", stynumber)
                .setParameter("sectionid", Sectionid)
                .setParameter("subsectionid", subsectionid).list();
        return list;
    }

    public int checkIfChildExist(final ProgramWiseSubsectionId programWiseSubsectionId) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramWiseSubsection subsection = (ProgramWiseSubsection) session.get(ProgramWiseSubsection.class, programWiseSubsectionId);
                int i1 = Integer.parseInt(session.createFilter(subsection.getTt_timetableallocationdetails(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(subsection.getTt_suspendedslotsdetails(), "select count(*)").list().get(0).toString());
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
//
////    public String doValidate(ProgramWiseSubsection programWiseSubsection, String mode) {
////        throw new UnsupportedOperationException("Not supported yet.");
////    }

    public String doValidate(final ProgramWiseSubsection pws, final String mode) {
        String errors = "";
        Integer count = new Integer(0);
        try {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(ProgramWiseSubsection.class);
                    criteria.add(Restrictions.eq("id.instituteid", pws.getId().getInstituteid()));
                    criteria.add(Restrictions.eq("id.sectionid", pws.getId().getSectionid()));
                    criteria.add(Restrictions.eq("id.programid", pws.getId().getProgramid()));
                    criteria.add(Restrictions.eq("id.academicyear", pws.getId().getAcademicyear()));
                    criteria.add(Restrictions.eq("id.stynumber", pws.getId().getStynumber()));
                    criteria.add(Restrictions.eq("branchid", pws.getBranchid()));
                    criteria.add(Restrictions.eq("subsectioncode", pws.getSubsectioncode()).ignoreCase());
                    if (mode.equals("edit")) {
                        criteria.add(Restrictions.ne("id.subsectionid", pws.getId().getSubsectionid()));//Do not check for itself when updating record
                    }
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors = "Duplicate";

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return errors;
    }
//

    public List getsubsection(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String subjectid, String registrationid) {
        String qry = " select pws.id.subsectionid, pws.subsectioncode, pws.subsectiondesc  from ProgramWiseSubsection pws "
                + " where pws.id.instituteid = '" + instituteid + "' ";
        if (!"all".equals(academicyear)) {
            qry = qry + " and pws.id.academicyear = '" + academicyear + "' ";
        }
        if (!"all".equals(programid)) {
            qry = qry + " and pws.id.programid = '" + programid + "' ";
        }
        if (!"all".equals(stynumber)) {
            qry = qry + " and pws.id.stynumber = '" + stynumber + "' ";
        }
        if (!"all".equals(sectionid)) {
            qry = qry + " and pws.id.sectionid = '" + sectionid + "' ";
        }
        qry = qry + " and exists( select sbd.subsectionid from StudentSubjectChoiceDetail sbd "
                + " where sbd.id.instituteid = '" + instituteid + "' and sbd.id.registrationid ='" + registrationid + "' ";
        if (!"all".equals(subjectid)) {
            qry = qry + " and sbd.id.subjectid = '" + subjectid + "'";
        }
        qry = qry + " and sbd.id.instituteid = pws.id.instituteid and pws.id.subsectionid = sbd.subsectionid and pws.id.stynumber = sbd.id.stynumber ) group by pws.id.subsectionid, pws.subsectioncode, pws.subsectiondesc order by pws.subsectioncode )";

        try {
            return getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public List getProgramWiseSubBatchList(String instituteid, String acad_year, String programid, String branchid) {
        String qryString = "Select pws.id.instituteid,pws.id.academicyear,pws.id.programid,pws.branchid,pws.id.stynumber,pws.id.sectionid,pws.id.subsectionid,sm.sectioncode,pws.subsectioncode,pws.subsectiondesc,pws.maxstudent,pws.deactive,ins.institutecode,pr.programcode"
                + " from  ProgramWiseSubsection pws,ProgramMaster pr, InstituteMaster ins,SectionMaster sm  "
                + " where pws.id.instituteid='" + instituteid + "'  and pws.id.academicyear= '" + acad_year + "' "
                + " and pws.branchid = '" + branchid + "'  and pws.id.programid = '" + programid + "'  "
                + " and pws.id.programid=pr.id.programid and pws.id.instituteid=ins.id.instituteid "
                + " and pws.id.instituteid=sm.id.instituteid and pws.id.sectionid=sm.id.sectionid order by pws.branchid ,pws.id.stynumber";
        return getHibernateTemplate().find(qryString);

    }

    @Override
    public List getDataCheckNewacademicyear(String instituteid, String new_acadyear) {
        String qryString = "select 1"
                + " from  ProgramWiseSubsection pws "
                + " where pws.id.instituteid='" + instituteid + "'  and pws.id.academicyear= '" + new_acadyear + "' ";
        // + " and pws.branchid = '" + branchid + "'  and pws.id.programid = '" + programid + "'  ";                
        return getHibernateTemplate().find(qryString);

    }

    @Override
    public List getProgramWiseSubBatchData(String instituteid, String acad_year) {
        String qryString = "select pws"
                + " from  ProgramWiseSubsection pws "
                + " where pws.id.instituteid='" + instituteid + "'  and pws.id.academicyear= '" + acad_year + "' ";
        // + " and pws.branchid = '" + branchid + "'  and pws.id.programid = '" + programid + "'  ";                
        return getHibernateTemplate().find(qryString);

    }

    public String saveObjList(final List<ProgramWiseSubsection> list) {
        String retStr = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                System.err.println("************* value" + i);
                session.saveOrUpdate((ProgramWiseSubsection) list.get(i));
            }
            retStr = "Data Save Successfully";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retStr = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retStr;
    }

}
