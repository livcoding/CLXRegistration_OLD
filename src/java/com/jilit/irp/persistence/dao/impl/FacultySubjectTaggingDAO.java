/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.FacultySubjectTaggingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ExamGradeMaster;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.util.JIRPSession;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author sunny.singhal
 */
public class FacultySubjectTaggingDAO extends HibernateDAO implements FacultySubjectTaggingIDAO {

    CallableStatement cstmt = null;
    static Connection con = null;
    Session session = null;
    private static final Log log = LogFactory.getLog(FacultySubjectTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all FacultySubjectTagging records via Hibernate from the database");
        return this.find("from FacultySubjectTagging as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(FacultySubjectTagging.class, id);
    }
//    /**
//     * Method Used To Check & Get FSTID For Perticular Criteria
//     * @param instituteid
//     * @param registrationid
//     * @param subjectid
//     * @param academicyear
//     * @param programid
//     * @param sectionid
//     * @param subsectionid
//     * @param stynumber
//     * @param componentid
//     * @param stytypeid
//     * @return
//     */

    @Override
    public List checkIfFSTIDExists(final String instituteid, final String registrationid, final String subjectid, final String academicyear, final String programid, final String sectionid, final String subsectionid, final byte stynumber, final String componentid, final String stytypeid) {
        List l = null;
        String qryString = " select fst.id.fstid "
                + " from FacultySubjectTagging fst "
                + " where fst.id.instituteid='" + instituteid + "' "
                + " and fst.registrationid='" + registrationid + "' "
                + " and fst.subjectid='" + subjectid + "' "
                + " and fst.academicyear='" + academicyear + "' "
                + " and fst.programid='" + programid + "' "
                + " and fst.sectionid='" + sectionid + "' "
                + " and fst.subsectionid='" + subsectionid + "' "
                + " and fst.stynumber='" + stynumber + "' "
                + " and fst.stytypeid='" + stytypeid + "' "
                + " and fst.subjectcomponentid='" + componentid + "'";
        try {
            l = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public List getAddDropList(final String instituteid, final String registrationid, final byte stynumber, final String studentid, final String subjectid, final String academicyear, final String programid, final String sectionid, final String subsectionid) {
        List retList = null;
        String qry = "select coalesce(sft.deactive,'N')"
                + " from PRFacultyStudentTagging sft, FacultySubjectTagging fst"
                + " where fst.id.instituteid = sft.id.instituteid"
                + " and fst.id.fstid = sft.id.fstid"
                + " and fst.id.instituteid = '" + instituteid + "'"
                + " and sft.id.studentid = '" + studentid + "'"
                + " and fst.registrationid = '" + registrationid + "'"
//                + " and fst.academicyear = '" + academicyear + "'"
//                + " and fst.programid = '" + programid + "' "
                + //              " and fst.sectionid = '"+sectionid+"'" +
                " and fst.subjectid = '" + subjectid + "' ";
//                + " and fst.stynumber = '" + stynumber + "' ";
        try {
            retList = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }
//

    @Override
    public List getSubjectDetail(final String instituteid, final String registrationid, final String subjectid, final String basketid, final String programid) {
        List retList = null;
        final StringBuilder qry = new StringBuilder();
        qry.append("select"
                + " distinct a.subjectcomponentid,"
                + " c.subjectcomponentcode,"
                + " 'emp',a.id.fstid,a.academicyear, a.programid, a.sectionid,a.stynumber,a.subsectionid,a.ttreferenceid,"
                + " b.employeecode,pm.programcode,sm.sectioncode,pws.subsectioncode,a.stynumber,a.academicyear,b.employeename,dm.departmentcode,a.subjectid  ,a.mergewithfstid"
                + " from"
                + " FacultySubjectTagging a,V_Staff b,"
                + " SubjectComponent c,TT_TimeTableAllocation t,"
                + " ProgramMaster pm,SectionMaster sm,"
                + " ProgramWiseSubsection pws,DepartmentMaster dm "
                + " where dm.departmentid = b.departmentid and"
                + " t.ttreferenceid=a.ttreferenceid and t.id.registrationid=a.registrationid"
                + " and t.id.instituteid=a.id.instituteid and a.id.instituteid=c.id.instituteid "
                + " and a.subjectcomponentid=c.id.subjectcomponentid  "
                + " and t.staffid=b.employeeid  and a.id.instituteid=:instituteid "
                + " and a.registrationid=:registrationid and a.subjectid=:subjectid  "
                //                + " and a.basketid=:basketid and a.programid=:programid "
                + " and :basketid=:basketid and :programid=:programid "
                + " and pm.id.programid = a.programid and pm.id.instituteid = a.id.instituteid "
                + " and sm.id.sectionid = a.sectionid and sm.id.instituteid = a.id.instituteid "
                + " and pws.id.stynumber = a.stynumber  and pws.id.programid = a.programid "
                + " and pws.id.subsectionid = a.subsectionid and pws.id.sectionid = a.sectionid "
                + " and exists(select psaw.id.instituteid from ProgramSchemeAcadyearWise psaw,ProgramSchemeAcadYearDetail psad where psaw.id.instituteid = psad.id.instituteid"
                + " and psaw.id.programschemeacadwiseid = psad.id.programschemeacadwiseid "
                + " and psad.id.subjectcomponentid = c.id.subjectcomponentid and psad.id.instituteid=a.id.instituteid"
                + " and psaw.programid = :programid and psaw.subjectid=a.subjectid and psaw.basketid=:basketid) and pws.id.instituteid = a.id.instituteid order by c.subjectcomponentcode,a.academicyear,pm.programcode,sm.sectioncode,pws.subsectioncode ");
        try {
            retList = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("basketid", basketid).
                    setParameter("programid", programid).list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List getDropSubjectDetail(final String instituteid, final String registrationid, final String subjectid, final String basketid, final String programid, final String studentid) {
        List retList = null;
        final StringBuilder qry = new StringBuilder();
        qry.append(" select distinct a.subjectcomponentid, c.subjectcomponentcode,  ");
        qry.append(" 'XXX',a.fstid,a.academicyear,a.programid, a.sectionid,a.stynumber,a.subsectionid,a.ttreferenceid, "); // 2 - 9
        qry.append(" pm.programcode ,sm.sectioncode,pws.subsectioncode,a.stynumber,a.academicyear, ");//10 - 14 
        qry.append(" a.fstid from FacultySubjectTagging a, PRFacultyStudentTagging sft, V_Staff b, SubjectComponent c,TT_TimeTableAllocation t, ProgramMaster pm, SectionMaster sm, ProgramWiseSubsection pws");
        qry.append(" where t.ttreferenceid=a.ttreferenceid and t.registrationid=a.registrationid and t.instituteid=a.instituteid");
        qry.append("  and a.instituteid=c.instituteid");
        qry.append(" and a.subjectcomponentid=c.subjectcomponentid");
        qry.append(" and t.staffid=b.employeeid");
        qry.append(" and a.instituteid='" + instituteid + "'");
        qry.append(" and a.registrationid='" + registrationid + "'");
//        qry.append(" and a.basketid='" + basketid + "'");
        qry.append(" and a.subjectid='" + subjectid + "'");
//        qry.append(" and a.programid='" + programid + "'");
        qry.append(" and sft.studentid ='" + studentid + "'");
        qry.append(" and sft.fstid = a.fstid");
        qry.append(" and sft.instituteid = a.instituteid");
        qry.append(" and pm.programid = a.programid and pm.instituteid = a.instituteid and a.ttreferenceid=t.ttreferenceid");
        qry.append(" and sm.sectionid = a.sectionid and sm.instituteid = a.instituteid");
        qry.append(" and pws.stynumber = a.stynumber and pws.programid = a.programid and pws.subsectionid = a.subsectionid and pws.sectionid = a.sectionid and pws.instituteid = a.instituteid");
        /*   qry.append(" group by a.subjectcomponentid, c.subjectcomponentcode, a.academicyear,t.staffid, b.employeecode, b.employeename, a.stynumber,  a.fstid, a.subsectionid,a.programid,a.instituteid,a.sectionid, ");
        qry.append(" pm.programcode,sm.sectioncode,pws.subsectioncode, a.academicyear,a.ttreferenceid");*/
        qry.append(" union ");
        qry.append(" select distinct a.subjectcomponentid, c.subjectcomponentcode,  ");
        qry.append(" 'XXX',a.fstid,a.academicyear, a.programid, a.sectionid, a.stynumber ,a.subsectionid,a.ttreferenceid, "); // 2
        qry.append(" pm.programcode ,sm.sectioncode,pws.subsectioncode,a.stynumber,a.academicyear,a.fstid  ");//3
        qry.append(" from FacultySubjectTagging a, PRFacultyStudentTagging sft, SubjectComponent c, ProgramMaster pm, SectionMaster sm, ProgramWiseSubsection pws");
        qry.append(" where a.instituteid=c.instituteid");
        qry.append(" and a.subjectcomponentid=c.subjectcomponentid");
        qry.append(" and a.instituteid='" + instituteid + "'");
        qry.append(" and a.registrationid='" + registrationid + "'");
//        qry.append(" and a.basketid='" + basketid + "'");
        qry.append(" and a.subjectid='" + subjectid + "'");
//        qry.append(" and a.programid='" + programid + "'");
        qry.append(" and sft.studentid ='" + studentid + "'");
        qry.append(" and sft.fstid = a.fstid");
        qry.append(" and sft.instituteid = a.instituteid");
        qry.append(" and pm.programid = a.programid and pm.instituteid = a.instituteid");
        qry.append(" and sm.sectionid = a.sectionid and sm.instituteid = a.instituteid");
        qry.append(" and pws.stynumber = a.stynumber and pws.programid = a.programid and pws.subsectionid = a.subsectionid and pws.sectionid = a.sectionid and pws.instituteid = a.instituteid");
        /* qry.append(" group by a.subjectcomponentid, c.subjectcomponentcode, a.academicyear, a.stynumber,  a.fstid, a.subsectionid,a.programid,a.instituteid,a.sectionid, ");
        qry.append(" pm.programcode,sm.sectioncode,pws.subsectioncode, a.academicyear,a.ttreferenceid");
        //  qry.append(" order by c.subjectcomponentcode ");*/
        try {
            retList = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    return session.createSQLQuery(qry.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }
//

    @Override
    public List checkFstIdExistOrNot(String instiuteid, String registrationid, String subjectid, String acadyear, String programid, byte styNo, String sectionid, String stytypeid, String subjectcomponentid,String subsectionid) {
        List retList = null;
        String qry = "select fst.id.fstid"
                + " from FacultySubjectTagging fst"
                + " where fst.id.instituteid = '" + instiuteid + "'"
                + " and fst.registrationid = '" + registrationid + "'"
                + " and fst.academicyear = '" + acadyear + "'"
                + " and fst.programid = '" + programid + "' "
                + " and fst.subjectid = '" + subjectid + "'"
                + " and fst.subjectcomponentid = '" + subjectcomponentid + "'"
                + " and fst.stynumber = '" + styNo + "' "
                + " and fst.stytypeid = '" + stytypeid + "' "
                + " and fst.subsectionid= '" + subsectionid + "' "
                + " and fst.sectionid = '" + sectionid + "' ";
        try {
            retList = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List getGipSubject(String instituteid, String registrationid, String studentid, String programid, String gradeid, String grade) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" Select sm.id.subjectid,sm.subjectcode,sm.subjectdesc,pst.credits,sr.id.stynumber,");
        sb.append(" (select distinct pr.id.groupid  from Pr_ElectiveSets pr where pr.id.instituteid = pst.id.instituteid and pr.id.subjectid = pst.subjectid and pr.id.programid = pst.programid  and rownum = 1 ) as groupid");
        sb.append(" from ProgramSubjectTagging pst, SubjectMaster sm,StudentResult sr ");
        sb.append(" where pst.id.instituteid=sm.id.instituteid and pst.subjectid=sm.id.subjectid and pst.id.instituteid= '" + instituteid + "'  and pst.id.registrationid= '" + registrationid + "' and pst.programid= '" + programid + "' ");
        sb.append(" and exists(select 1 from StudentSgpaCgpa  sg where  sg.id.instituteid= '" + instituteid + "'  and sg.id.studentid= '" + studentid + "' ");
        sb.append(" and sg.id.stynumber=(select max(sg1.id.stynumber) from StudentSgpaCgpa  sg1 where sg.id.instituteid=sg1.id.instituteid and sg.id.studentid=sg1.id.studentid )  and sg.cgpa " + grade + " )");
        sb.append(" and sr.id.instituteid= '" + instituteid + "' and sr.id.studentid= '" + studentid + "'  and coalesce(sr.fail, 'N')='N' and coalesce(sr.deactive, 'N')='N' and sr.id.subjectid =pst.subjectid ");
        sb.append(" and sr.gradeid in (" + gradeid + ") ");
        sb.append(" group by  sm.id.subjectid , sm.subjectcode , sm.subjectdesc ,sr.id.stynumber , pst.id.instituteid , pst.id.registrationid ,pst.credits,pst.subjectid,pst.programid  order by sm.subjectdesc");
        try {
            return getHibernateTemplate().find(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGipCriteria(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select cgparangefrom,cgparangeupto,appicablegrades from Setup_GIPCriteria sg");
        sb.append(" where sg.id.instituteid=:instituteid and sg.id.programid=:programid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Collection<?> getGradeCriteriaBased(final String instituteid, final String[] grade) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ExamGradeMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.deactive", "N"));
                criteria.add(Restrictions.in("master.grade", grade));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.gradeid")));
                //criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public String getBasketForAudit(String instituteid, String programid, String sectionid, String stynumber) {
        List list = null;
        String basketid = "";
        StringBuilder sb = new StringBuilder();
        sb.append("select bm.id.basketid from BasketMaster bm "
                + "where bm.subjecttypeid=(select st.id.subjecttypeid from SubjectTypeMaster st where st.id.instituteid=bm.id.instituteid and st.subjecttype='E') "
                + " and bm.id.instituteid=:instituteid and bm.programid=:programid "
                + " and bm.sectionid=:sectionid and bm.stynumber=:stynumber ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("stynumber", new Byte(stynumber)).list();
            if (list.size() > 0) {
                basketid = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return basketid;
    }

    public List getBasketForGip(String instituteid, String registrationid, String studentid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct fst.basketid, ds.id.departmentid from FacultySubjectTagging fst, FacultyStudentTagging sft,DepartmentSubjectTagging ds");
        sb.append(" where fst.id.instituteid=sft.id.instituteid  and fst.id.fstid = sft.fstid and fst.id.instituteid = ds.id.instituteid and fst.subjectid = ds.id.subjectid and fst.id.instituteid=:instituteid and fst.subjectid=:subjectid  and sft.id.studentid=:studentid and fst.registrationid=:registrationid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List checkInPST(String instituteid, String registrationid, String subjectid, String academicyear, String programid, Short stynumber, String sectionid, String basketid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select 1 from ProgramSubjectTagging pst");
        sb.append(" where pst.id.instituteid=:instituteid and pst.id.registrationid=:registrationid and pst.subjectid=:subjectid  and pst.academicyear=:academicyear and pst.programid=:programid and pst.stynumber=:stynumber and pst.sectionid=:sectionid and pst.basketid=:basketid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("stynumber", stynumber).
                    setParameter("sectionid", sectionid).
                    setParameter("basketid", basketid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getComponentIds(String instituteid, String subjectid, String registrationid, String programid, String academicyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct c.id.subjectcomponentid, a.ttreferenceid  from  FacultySubjectTagging a, SubjectComponentDetail c, TT_TimeTableAllocation t  ");
        sb.append(" where t.ttreferenceid=a.ttreferenceid  and t.id.registrationid=a.registrationid and t.id.instituteid=a.id.instituteid and a.id.instituteid=c.id.instituteid ");
        sb.append(" and a.subjectid = c.id.subjectid and a.subjectcomponentid=c.id.subjectcomponentid ");
        sb.append(" and c.id.instituteid=:instituteid and c.id.subjectid=:subjectid and a.registrationid=:registrationid and a.programid=:programid and a.academicyear=:academicyear");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("registrationid", registrationid).
                    setParameter("programid", programid).
                    setParameter("academicyear", academicyear).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStudentDetailData(String studentid, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pm.id.programid,pm.enrollmentcode , bm.id.branchid, bm.enrollmentcode,ac.id.academicyear,ac.enrollmentcode,im.id.instituteid,im.enrollmentcode ,pmt.id.programtypeid,pmt.enrollmentcode "
                + " from   StudentMaster sm ,ProgramMaster pm, BranchMaster bm,Academicyear ac,InstituteMaster im,ProgramType pmt,ProgramTypeProgramTagging pmtpm "
                + " where sm.instituteid = im.id.instituteid and sm.instituteid = pm.id.instituteid and sm.programid = pm.id.programid and  sm.instituteid = bm.id.instituteid "
                + " and sm.branchid = bm.id.branchid and sm.acadyear = ac.id.academicyear and sm.instituteid =ac.id.instituteid and  sm.instituteid = pmt.id.instituteid and pmtpm.id.instituteid =sm.instituteid "
                + " and pmtpm.id.instituteid =pmt.id.instituteid   and pmtpm.id.instituteid =pm.id.instituteid  and pmtpm.id.programid =pm.id.programid and pmtpm.id.programtypeid =pmt.id.programtypeid "
                + " and  sm.studentid =:studentid "
                + " and sm.instituteid =:instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("studentid", studentid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List checkFstIdExistOrNotForAudit(String instiuteid, String registrationid, String subjectid, String academicyear, String programid, byte styNo, String sectionid, String stytypeid, String subjectcomponentid, String basketid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fst.id.fstid from FacultySubjectTagging fst ");
        sb.append(" where  fst.id.instituteid=:instituteid and fst.registrationid=:registrationid and fst.academicyear=:academicyear and fst.programid=:programid");
        sb.append(" and fst.subjectid=:subjectid and fst.subjectcomponentid =:subjectcomponentid and fst.stynumber=:stynumber and fst.stytypeid =:stytypeid and fst.sectionid =:sectionid and fst.basketid=:basketid");

        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instiuteid).
                    setParameter("subjectid", subjectid).
                    setParameter("registrationid", registrationid).
                    setParameter("programid", programid).
                    setParameter("academicyear", academicyear).
                    setParameter("stynumber", styNo).
                    setParameter("sectionid", sectionid).
                    setParameter("stytypeid", stytypeid).
                    setParameter("subjectcomponentid", subjectcomponentid).
                    setParameter("basketid", basketid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List checkFSTExistOrNot(String instiuteid, String registrationid, String subjectid, String academicyear, String programid, byte styNo, String sectionid, String subsectionid, String stytypeid, String subjectcomponentid, String basketid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fst.id.fstid from FacultySubjectTagging fst ");
        sb.append(" where  fst.id.instituteid=:instituteid and fst.registrationid=:registrationid and fst.academicyear=:academicyear and fst.programid=:programid");
        sb.append(" and fst.subjectid=:subjectid and fst.subjectcomponentid =:subjectcomponentid and fst.stynumber=:stynumber and fst.stytypeid =:stytypeid and fst.sectionid =:sectionid and fst.subsectionid=:subsectionid and fst.basketid=:basketid");

        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instiuteid).
                    setParameter("subjectid", subjectid).
                    setParameter("registrationid", registrationid).
                    setParameter("programid", programid).
                    setParameter("academicyear", academicyear).
                    setParameter("stynumber", styNo).
                    setParameter("sectionid", sectionid).
                    setParameter("subsectionid", subsectionid).
                    setParameter("stytypeid", stytypeid).
                    setParameter("subjectcomponentid", subjectcomponentid).
                    setParameter("basketid", basketid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
}
