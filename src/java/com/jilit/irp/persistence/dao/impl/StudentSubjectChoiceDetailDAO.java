/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentSubjectChoiceDetailIDAO;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author akshya.gaur
 */
public class StudentSubjectChoiceDetailDAO extends HibernateDAO implements StudentSubjectChoiceDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentSubjectChoiceDetail.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentSubjectChoiceDetail records via Hibernate from the database");
        return this.find("from StudentSubjectChoiceDetail as tname order by seqid asc");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentSubjectChoiceDetail.class, id);
    }

    public List StyNumberList(String instituteId, String academicYear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct s.stynumber from StudentMaster  s where s.instituteid= :instituteid "
                + " and s.acadyear =:acadyear  and stynumber is not null order by s.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("acadyear", academicYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List branchList(String instituteId, String academicYear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct bm.id.branchid, bm.id.programid, bm.branchcode, bm.branchdesc "
                + " from StudentMaster s, BranchMaster bm  where s.instituteid= :instituteid "
                + " and s.acadyear = :acadyear and bm.id.instituteid = s.instituteid "
                + " and bm.id.programid = s.programid and bm.id.branchid = s.branchid order by bm.branchcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("acadyear", academicYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSectionList(String instituteId, String acadYear, String styNum, String branchId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sm.id.sectionid, sm.sectioncode, sm.sectiondesc "
                + " from StudentMaster s, SectionMaster sm where s.instituteid = :instituteid "
                + " and s.acadyear = :acadyear and s.stynumber = :stynumber "
                + "and s.branchid = :branchid and s.instituteid = sm.id.instituteid "
                + "and s.sectionid = sm.id.sectionid order by sm.sectioncode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("acadyear", acadYear).
                    setParameter("stynumber", Byte.parseByte(styNum)).
                    setParameter("branchid", branchId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubSectionListForStuMas(String instituteId, String acadYear, String styNum, String branchId, String sectionId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pws.id.subsectionid, pws.subsectioncode from ProgramWiseSubsection pws "
                + " where pws.id.instituteid = :instituteid and pws.id.sectionid = :sectionid "
                + " and pws.subsectiontype = 'A' and pws.id.academicyear = :academicyear and pws.branchid = :branchid "
                + " and pws.id.stynumber = :stynumber and coalesce(pws.deactive,'N') ='N'");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("academicyear", acadYear).
                    setParameter("stynumber", Byte.parseByte(styNum)).
                    setParameter("branchid", branchId).
                    setParameter("sectionid", sectionId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getRegistraionCodeForStuReg(String instituteId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct r.id.registrationid, r.registrationcode, "
                + " r.registrationdesc from StudentRegistration s, RegistrationMaster r "
                + " where s.id.registrationid = r.id.registrationid and coalesce(r.deactive,'N') ='N' "
                + " and s.stytypeid = (select st.id.stytypeid from StyType st where st.stytype = 'REG' "
                + " and s.id.instituteid = st.id.instituteid) and r.id.instituteid= :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getAcademicYear(String instituteId, String regId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct s.academicyear from StudentRegistration_info  s where "
                + " s.id.instituteid= :instituteid and s.id.registrationid= :registrationid "
                + " order by s.academicyear desc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("registrationid", regId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStyNumber(String instituteId, String academicYear, String regId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct s.stynumber from StudentRegistration_info  "
                + " s where s.id.instituteid= :instituteid and s.id.registrationid= :registrationid "
                + " and s.academicyear = :academicyear order by s.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("academicyear", academicYear).
                    setParameter("registrationid", regId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getBranchForStuReg(String instituteId, String academicYear, String regId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct bm.id.branchid, bm.id.programid, bm.branchcode, bm.branchdesc "
                + " from StudentRegistration_info s, BranchMaster bm  where s.id.instituteid= :instituteid "
                + " and s.id.registrationid= :registrationid and s.academicyear = :academicyear "
                + " and bm.id.instituteid = s.id.instituteid and bm.id.programid = s.programid "
                + " and bm.id.branchid = s.branchid order by bm.branchcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("academicyear", academicYear).
                    setParameter("registrationid", regId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSectionForStuReg(String instituteId, String academicYear, String regId, String stynumber, String branchid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sm.id.sectionid, sm.sectioncode, sm.sectiondesc "
                + " from StudentRegistration_info s, SectionMaster sm where s.id.instituteid = :instituteid "
                + " and s.id.registrationid= :registrationid and s.academicyear = :academicyear "
                + " and s.stynumber = :stynumber and s.branchid = :branchid "
                + "and s.id.instituteid = sm.id.instituteid and s.sectionid = sm.id.sectionid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("academicyear", academicYear).
                    setParameter("registrationid", regId).
                    setParameter("stynumber", Byte.parseByte(stynumber)).
                    setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubjectCode(String instituteId, String regId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct  sm.id.subjectid, sm.id.instituteid ,sm.subjectcode,"
                + " sm.subjectdesc from StudentSubjectChoiceMaster s,SubjectMaster sm where "
                + " sm.id.subjectid = s.id.subjectid  and(sm.deactive='N' or sm.deactive is null)  "
                + " and s.id.instituteid= :instituteid and s.id.registrationid= :registrationid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("registrationid", regId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getDepartmentCode(String instituteId, String regId, String subjectId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select dm.departmentid, dm.departmentcode, dm.department, coalesce(dm.shortname,' ') "
                + " from DepartmentMaster dm where coalesce(dm.deactive,'N') = 'N'"
                + " and exists ( select 1 from ProgramSubjectTagging pst where "
                + " pst.id.instituteid= :instituteid and pst.subjectid= :subjectid  "
                + " and pst.id.registrationid= :registrationid and dm.id.departmentid=pst.departmentid ) "
                + " group by dm.departmentid, dm.departmentcode, dm.department, dm.shortname order by dm.departmentcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("registrationid", regId).
                    setParameter("subjectid", subjectId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubjectCompCode(String instituteId, String subjectId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct  sm.id.subjectcomponentid, sm.id.instituteid ,sm.subjectcomponentcode, "
                + " sm.subjectcomponentdesc from StudentSubjectChoiceDetail s,SubjectComponent sm where "
                + " sm.id.subjectcomponentid = s.id.subjectcomponentid and (sm.deactive='N' or sm.deactive is null)  "
                + " and sm.id.instituteid= :instituteid and s.id.subjectid= :subjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteId).
                    setParameter("subjectid", subjectId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getPSTSection(String instituteid, String registrationid, String subjectid, String departmentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct st.id.studentid,  sm.id.sectionid, st.enrollmentno, st.name, pm.programcode, pst.academicyear, "
                + " pst.stynumber, sm.sectioncode, pws.subsectioncode, pst.subjectrunning, "
                + " bm.basketcode, sp.stytype from ProgramSubjectTagging pst, SectionMaster sm , ProgramWiseSubsection pws, "
                + " StudentMaster st, ProgramMaster pm, BasketMaster bm, StudentRegistration_info sr, StyType sp "
                + " where pst.id.instituteid = sm.id.instituteid and pst.id.instituteid = pws.id.instituteid "
                + " and pst.id.instituteid = st.instituteid and pws.id.instituteid = pm.id.instituteid and pst.id.instituteid = sp.id.instituteid "
                + " and sr.id.instituteid = sp.id.instituteid "
                + " and pws.id.instituteid = bm.id.instituteid and pst.id.instituteid = sr.id.instituteid and pst.id.registrationid = sr.id.registrationid "
                + " and pst.sectionid = sm.id.sectionid and pst.sectionid = pws.id.sectionid and pst.sectionid = sr.sectionid "
                + " and pst.sectionid = bm.sectionid and sr.subsectionid = pws.id.subsectionid and pst.academicyear = pws.id.academicyear "
                + " and pst.academicyear = sr.academicyear and st.id.studentid = sr.id.studentid "
                + " and pst.programid = pm.id.programid and pst.programid = pws.id.programid "
                + " and pst.programid = st.programid and pst.programid = bm.programid "
                + " and pst.stynumber = pws.id.stynumber and pst.stynumber = sr.stynumber "
                + " and pst.stynumber = bm.stynumber and pst.basketid = bm.id.basketid and sr.stytypeid = sp.id.stytypeid "
                + " and pst.id.instituteid = :instituteid and pst.id.registrationid = :registrationid "
                + " and pst.subjectid = :subjectid and pst.departmentid= :departmentid "
                + " order by 1");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("departmentid", departmentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubsectionForCombo(String instid, String styNum, String academicyear, String branchid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sm.id.sectionid, sm.sectioncode from ProgramWiseSubsection pws, SectionMaster sm "
                + " where pws.id.instituteid= :instituteid and pws.id.academicyear = :academicyear "
                + " and pws.branchid = :branchid "
                + " and pws.id.stynumber = :stynumber and pws.subsectiontype = 'A' "
                + " and coalesce(pws.deactive,'N') ='N' and sm.id.instituteid = pws.id.instituteid "
                + " and sm.id.sectionid = pws.id.sectionid order by sm.sectioncode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instid).
                    setParameter("academicyear", academicyear).
                    setParameter("branchid", branchid).
                    setParameter("stynumber", Byte.parseByte(styNum)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubsectionForCombo2(String instid, String styNum, String academicyear, String branchid, String nextSemValue) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        byte stynumber = (byte) ("Y".equals(nextSemValue) ? (Byte.parseByte(styNum) + 1) : Byte.parseByte(styNum));
        sb.append(" select distinct sm.id.sectionid, sm.sectioncode from ProgramWiseSubsection pws, SectionMaster sm "
                + " where pws.id.instituteid= :instituteid and pws.id.academicyear = :academicyear "
                + " and pws.branchid = :branchid "
                + " and pws.id.stynumber = :stynumber and pws.subsectiontype = 'A' "
                + " and coalesce(pws.deactive,'N') ='N' and sm.id.instituteid = pws.id.instituteid "
                + " and sm.id.sectionid = pws.id.sectionid order by sm.sectioncode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instid).
                    setParameter("academicyear", academicyear).
                    setParameter("branchid", branchid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getAcademicYearForChoice(String instituteid, String registrationid, String subjectid, String subCompId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.acadyear,sm.acadyear "
                + " from StudentMaster sm, StudentSubjectChoiceDetail scd "
                + " where sm.instituteid= :instituteid "
                + " and sm.instituteid=scd.id.instituteid "
                + " and scd.id.subjectid= :subjectid "
                + " and scd.id.studentid = sm.studentid "
                + " and scd.id.subjectcomponentid = :subjectcomponentid "
                + " and scd.id.registrationid = :registrationid "
                + " group by sm.acadyear  order by sm.acadyear desc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("subjectcomponentid", subCompId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getProgramForChoice(String instituteid, String registrationid, String subjectid, String subCompId, String AcademicYear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select p.id.programid, p.programcode "
                + " from ProgramMaster p, StudentSubjectChoiceDetail scd, StudentMaster sm "
                + " where p.id.programid = sm.programid  "
                + " and coalesce(p.deactive,'N') = 'N' "
                + " and p.id.instituteid= :instituteid "
                + " and scd.id.instituteid=p.id.instituteid"
                + " and sm.instituteid=scd.id.instituteid "
                + " and scd.id.subjectid = :subjectid "
                + " and scd.id.subjectcomponentid = :subjectcomponentid "
                + " and scd.id.registrationid = :registrationid "
                + " and sm.acadyear = :acadyear "
                + " and scd.id.studentid = sm.studentid "
                + " and scd.id.stynumber = sm.stynumber"
                + " group by  p.id.programid,p.programcode order by p.programcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("subjectcomponentid", subCompId).
                    setParameter("acadyear", AcademicYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getBranchForChoice(String instituteid, String registrationid, String subjectid, String subCompId, String AcademicYear, String programId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.id.branchid ,b.branchcode "
                + " from BranchMaster b , StudentSubjectChoiceDetail scd, StudentMaster sm  "
                + " where sm.branchid = b.id.branchid "
                + " and b.id.programid = sm.programid "
                + " and coalesce(b.deactive,'N') = 'N' "
                + " and b.id.instituteid= :instituteid "
                + " and scd.id.instituteid = b.id.instituteid"
                + " and sm.instituteid = scd.id.instituteid "
                + " and scd.id.subjectid = :subjectid "
                + " and scd.id.subjectcomponentid = :subjectcomponentid "
                + " and scd.id.registrationid = :registrationid "
                + " and sm.acadyear = :acadyear "
                + " and sm.programid = :programid "
                + " and scd.id.studentid = sm.studentid "
                + " and scd.id.stynumber = sm.stynumber"
                + " group by  b.id.branchid,b.branchcode "
                + " order by b.branchcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("subjectcomponentid", subCompId).
                    setParameter("registrationid", registrationid).
                    setParameter("acadyear", AcademicYear).
                    setParameter("programid", programId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStyTypeChoice(String instituteid, String registrationid, String subjectid, String AcademicYear, String programId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select s.id.stytypeid, s.stytype "
                + " from  StyType s,  StudentSubjectChoiceMaster scm, StudentMaster sm"
                + " where scm.stytypeid = s.id.stytypeid   "
                + " and coalesce(s.deactive,'N') = 'N'"
                + " and s.id.instituteid= :instituteid "
                + " and scm.id.instituteid = s.id.instituteid "
                + " and sm.instituteid = scm.id.instituteid "
                + " and scm.id.subjectid = :subjectid "
                + " and scm.id.registrationid = :registrationid "
                + " and sm.acadyear = :acadyear "
                + " and sm.programid = :programid "
                + " and scm.id.studentid = sm.studentid "
                + " and scm.id.stynumber = sm.stynumber"
                + " group by  s.id.stytypeid, s.stytype ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("acadyear", AcademicYear).
                    setParameter("programid", programId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStyNumberChoice(String instituteid, String registrationid, String subjectid, String AcademicYear, String programId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select scm.id.stynumber, scm.id.stynumber "
                + " from StudentSubjectChoiceMaster scm, StudentMaster sm"
                + " where coalesce(scm.deactive,'N') = 'N'"
                + " and scm.id.instituteid= :instituteid "
                + " and scm.id.instituteid = sm.id.instituteid "
                + " and scm.id.subjectid = :subjectid "
                + " and scm.id.registrationid = :registrationid "
                + " and sm.acadyear = :acadyear "
                + " and sm.programid = :programid "
                + " and scm.id.studentid = sm.studentid "
                + " and scm.id.stynumber = sm.stynumber"
                + " group by scm.id.stynumber order by scm.id.stynumber ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("acadyear", AcademicYear).
                    setParameter("programid", programId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubSectionAllocation(String instituteid, String regId, String departmentId, String subjectId, String subCompId, String acadYear, String programid, String branchId, String styNum, String styType) {
        List l = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder qryString = new StringBuilder();
                qryString.append(" select s.studentid, sd.subsectionid, s.enrollmentno, s.name, s.academicyear,  ");
                qryString.append(" pm.programcode, pw.subsectioncode, sd.stynumber, st.stytype, sc.subjectrunning, ");
                qryString.append(" (select sec.sectioncode from SectionMaster sec where sec.instituteid = pw.instituteid and sec.sectionid = pw.sectionid) sectioncode, ");
                qryString.append(" (select bm.basketcode from BasketMaster bm where bm.basketid = sc.basketid and bm.instituteid = sc.instituteid and bm.stynumber = sd.stynumber and bm.sectionid = s.sectionid and bm.programid = s.programid)");
                qryString.append(" from StudentSubjectChoiceDetail sd, StudentMaster s, StudentSubjectChoiceMaster sc, ");
                qryString.append(" StyType st, ProgramMaster pm, ProgramWiseSubsection pw, ProgramSubjectTagging pst ");
                qryString.append(" where s.studentid = sd.studentid and sd.studentid = sc.studentid and sd.instituteid = sc.instituteid ");
                qryString.append(" and sd.instituteid = s.instituteid and sd.registrationid = sc.registrationid ");
                qryString.append(" and sc.instituteid=pst.instituteid and sc.registrationid=pst.registrationid ");
                qryString.append(" and pw.academicyear=pst.academicyear and pm.programid=pst.programid ");
                qryString.append(" and pw.sectionid=pst.sectionid and sc.stynumber=pst.stynumber ");
                qryString.append(" and sc.basketid=pst.basketid and sc.subjectid=pst.subjectid and sc.subjectid = sd.subjectid ");
                qryString.append(" and s.academicyear = pw.academicyear and sd.stynumber = pw.stynumber ");
                if (instituteid != null && !instituteid.isEmpty()) {
                    qryString.append(" and sd.instituteid='" + instituteid + "'  ");
                }
                if (regId != null && !regId.isEmpty()) {
                    qryString.append(" and sd.registrationid='" + regId + "' ");
                }
                if (subjectId != null && !subjectId.isEmpty()) {
                    qryString.append(" and sd.subjectid='" + subjectId + "' ");
                }
                if (subCompId != null && !subCompId.isEmpty()) {
                    qryString.append(" and sd.subjectcomponentid='" + subCompId + "'");
                }
                if (acadYear != null && !acadYear.isEmpty()) {
                    qryString.append(" and s.academicyear='" + acadYear + "' ");
                }
                if (programid != null && !programid.isEmpty()) {
                    qryString.append(" and pm.programid='" + programid + "'");
                }
                if (branchId != null && !branchId.isEmpty()) {
                    qryString.append(" and s.branchid='" + branchId + "'");
                }
                if (styNum != null && !styNum.isEmpty()) {
                    qryString.append(" and sd.stynumber='" + styNum + "'");
                }
                if (styType != null && !styType.isEmpty()) {
                    qryString.append(" and st.stytypeid='" + styType + "'");
                }
                qryString.append(" and sd.registrationid = sc.registrationid");
                qryString.append(" and st.stytypeid=sc.stytypeid");
                qryString.append(" and pm.instituteid=s.instituteid ");
                qryString.append(" and pm.instituteid=pw.instituteid  ");
                qryString.append(" and pm.programid=s.programid ");
                qryString.append(" and pm.programid=pw.programid ");
                qryString.append(" and pst.subjectrunning='Y' ");
                qryString.append(" and pst.departmentid='" + departmentId + "'");
                qryString.append(" and (pw.subsectionid(+)=sd.subsectionid )");
                qryString.append(" group by s.name,s.academicyear,pm.programcode,sd.stynumber,st.stytype, pw.subsectioncode,s.enrollmentno,sd.subsectionid,s.studentid,sc.basketid,sc.subjectrunning,pw.instituteid,pw.sectionid,s.programid, sc.instituteid ,s.sectionid  ");
                qryString.append("  order by   st.stytype, sc.subjectrunning,  sd.subsectionid,s.enrollmentno,s.name ");
                return session.createSQLQuery(qryString.toString()).list();
            }
        });

        return l;
    }

    public List getSubsection(String instituteid, String registrationid, String subjectid, String sectionid, String acadyear, String stynumber, String component, String departmentid) {
        String basketcode = "";
        List list1 = null;
        List list2 = null;
        String qry = " select distinct bm.basketcode from ProgramSubjectTagging pst, BasketMaster bm"
                + " where pst.subjectid= :subjectid "
                + " and pst.id.instituteid= :instituteid "
                + " and pst.id.registrationid = :registrationid "
                + " and pst.departmentid = :departmentid "
                + " and pst.id.instituteid = bm.id.instituteid"
                + " and pst.basketid = bm.id.basketid"
                + " and pst.sectionid = bm.sectionid"
                + " and pst.stynumber = bm.stynumber";
        try {
            list1 = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("departmentid", departmentid).list();
            basketcode = (list1 != null && !list1.isEmpty()) ? list1.get(0).toString() : "";

            String qry2 = " select pws.id.subsectionid, pws.subsectioncode from ProgramWiseSubsection pws "
                    + " where pws.id.instituteid= :instituteid and pws.id.sectionid = :sectionid and  "
                    + " pws.id.academicyear = :academicyear and  pws.id.stynumber = :stynumber ";
            if (("A".equals(basketcode) && "L".equals(component))
                    || ("A".equals(basketcode) && "P".equals(component))
                    || ("I".equals(basketcode) && "L".equals(component))
                    || ("S".equals(basketcode) && "L".equals(component))
                    || ("S".equals(basketcode) && "P".equals(component))
                    || ("A".equals(basketcode) && "L".equals(component))
                    || ("A".equals(basketcode) && "L".equals(component))) {
                qry2 = qry2 + " and pws.subsectiontype = 'A' ";
            } else if (("A".equals(basketcode) && "T".equals(component))
                    || ("S".equals(basketcode) && "T".equals(component))) {
                qry2 = qry2 + " and pws.subsectiontype = 'T' ";
            } else if ((basketcode.startsWith("B") && "L".equals(component))
                    || (basketcode.startsWith("B") && "P".equals(component))) {
                qry2 = qry2 + " and pws.subsectiontype = 'E' and  pws.subsectioncode like 'ELE%'";
            } else if ((basketcode.startsWith("B") && "T".equals(component))) {
                qry2 = qry2 + " and pws.subsectiontype = 'E' and  pws.subsectioncode like 'TUT-ELE%'";
            }
            list2 = getHibernateSession().createQuery(qry2).
                    setParameter("instituteid", instituteid).
                    setParameter("sectionid", sectionid).
                    setParameter("academicyear", acadyear).
                    setParameter("stynumber", Byte.parseByte(stynumber)).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list2;
    }

    public List getPSTSectionChoice(String instituteid, String registrationid, String subjectid, String departmentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sm.id.sectionid, sm.sectioncode "
                + " from ProgramSubjectTagging pst, SectionMaster sm, ProgramWiseSubsection pws, "
                + " StudentMaster st, ProgramMaster pm, BasketMaster bm, StudentRegistration_info sr, StyType sp "
                + " where pst.id.instituteid = sm.id.instituteid and pst.id.instituteid = pws.id.instituteid "
                + " and pst.id.instituteid = st.instituteid and pws.id.instituteid = pm.id.instituteid and pst.id.instituteid = sp.id.instituteid "
                + " and sr.id.instituteid = sp.id.instituteid "
                + " and pws.id.instituteid = bm.id.instituteid and pst.id.instituteid = sr.id.instituteid and pst.id.registrationid = sr.id.registrationid "
                + " and pst.sectionid = sm.id.sectionid and pst.sectionid = pws.id.sectionid and pst.sectionid = sr.sectionid "
                + " and pst.sectionid = bm.sectionid and sr.subsectionid = pws.id.subsectionid and pst.academicyear = pws.id.academicyear "
                + " and pst.academicyear = sr.academicyear and st.id.studentid = sr.id.studentid "
                + " and pst.programid = pm.id.programid and pst.programid = pws.id.programid "
                + " and pst.programid = st.programid and pst.programid = bm.programid "
                + " and pst.stynumber = pws.id.stynumber and pst.stynumber = sr.stynumber "
                + " and pst.stynumber = bm.stynumber and pst.basketid = bm.id.basketid and sr.stytypeid = sp.id.stytypeid "
                + " and pst.id.instituteid = :instituteid and pst.id.registrationid = :registrationid "
                + " and pst.subjectid = :subjectid and pst.departmentid= :departmentid "
                + " order by 1");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("departmentid", departmentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getBackPaperAttemptParameters(String instituteid, String parameterid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select new map( pa.id.parameterid as parameterid,pa.parametervalue as parametervalue, pa.parameter as parameter )"
                + " from Parameters pa where pa.id.instituteid=:instituteid and pa.id.parameterid=:parameterid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("parameterid", parameterid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getStudentSubjectChoiceDetailData(String instituteid, String registrationid, String studentid) {
        List list = new ArrayList();
        try {
            String qry = "from StudentSubjectChoiceDetail s"
                    + " where s.id.instituteid =:instituteid"
                    + " and s.id.registrationid =:registrationid"
                    + " and s.id.studentid =:studentid";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid)
                    .setParameter("studentid", studentid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteStudentSubjectChoiceDetailData(String instituteid, String registrationid, String studentid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from StudentSubjectChoiceDetail s where s.id.instituteid =:instituteid and s.id.registrationid =:registrationid"
                + " and s.id.studentid =:studentid");
        try {
            Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid);
            qry.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
