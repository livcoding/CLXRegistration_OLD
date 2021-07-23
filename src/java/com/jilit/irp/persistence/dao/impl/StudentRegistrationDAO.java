package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentRegistrationIDAO;
import com.jilit.irp.persistence.dto.ProgramTypeProgramTagging;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.Sis_StudentRegActivities;
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
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Shimona.Khandelwal
 */
public class StudentRegistrationDAO extends HibernateDAO implements StudentRegistrationIDAO {

    private static final Log log = LogFactory.getLog(StudentRegistrationDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentRegistration records via Hibernate from the database");
        return this.find("from StudentRegistration as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentRegistration.class, id);
    }

    public Object findByPrimaryKey1(String id) {
        return getHibernateTemplate().get(StudentMaster.class, id);
    }

    public Object findByPrimaryKey2(Serializable id) {
        return getHibernateTemplate().get(Sis_StudentRegActivities.class, id);
    }

    public List getAcadYear(String instituteid, String regid) {
        StringBuilder qry = new StringBuilder();
        qry.append("  select distinct sr.academicyear ");
        qry.append(" from StudentRegistration_info sr where sr.id.instituteid=:instituteid");
        qry.append(" and sr.id.registrationid=:regid order by sr.academicyear desc ");
        try {
            return getHibernateSession().createQuery(qry.toString()).setParameter("regid", regid).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    @Override
    public List getAcadYearStudentMaster(String instituteid, String regid) {
        StringBuilder qry = new StringBuilder();
        qry.append("    select distinct sm.acadyear from StudentMaster sm where sm.instituteid=:instituteid and coalesce(sm.activestatus,'A')='A' order by sm.acadyear desc");
        try {
            return getHibernateSession().createQuery(qry.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    public List getAcadmicyearForSubjectFinalization(String instituteid, String regid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct acadyear from StudentMaster where coalesce(activestatus,'A')='A' and instituteid = :instituteid"
                + " and id.studentid in (select distinct id.studentid from StudentRegistration where id.instituteid=:instituteid"
                + " and regallow='Y' and id.registrationid=:regid ) order by acadyear desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getAcadmicyearForFst(String instituteid, String regid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct academicyear,academicyear from ProgramSubjectTagging where "
                + " coalesce(deactive,'N')='N' and id.registrationid=:regid and id.instituteid=:instituteid order by academicyear desc ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public String getProgramType(String instituteid, String programid) {
        List l = new ArrayList();
        String progTypeId = "";
        String string = "select a from ProgramTypeProgramTagging  a where a.id.instituteid=:instituteid and a.id.programid=:programid";
        ProgramTypeProgramTagging ptpt = null;
        l = (ArrayList) getHibernateSession().createQuery(string).setParameter("instituteid", instituteid).setParameter("programid", programid).list();
        if (!l.isEmpty()) {
            ptpt = (ProgramTypeProgramTagging) l.get(0);
            progTypeId = ptpt.getId().getProgramtypeid();
        }
        return progTypeId;
    }

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentRegistration.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));
                criteria.add(Restrictions.eq("master.id.studentid", studentid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.studentid").as("studentid")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getStudentRegistrationData(String instituteid, String registrationid, String studentid) {
        String qry = "from StudentRegistration s"
                + " where s.id.instituteid =:instituteid "
                + " and s.id.registrationid =:registrationid "
                + " and s.id.studentid =:studentid ";
        return getHibernateSession().createQuery(qry).
                setParameter("instituteid", instituteid).setParameter("registrationid", registrationid)
                .setParameter("studentid", studentid)
                .list();
    }

    public List checkforSubsectionId(String instituteid, String acadYr, String programid, String sectionid, String subsectionid, int styno) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select pws.subsectioncode,pws.subsectiondesc from ProgramWiseSubsection  pws where pws.id.academicyear=:acadYr and  pws.id.programid=:programid");
        qry.append(" and pws.id.sectionid =:sectionid");
        qry.append(" and pws.id.subsectionid =:subsectionid");
        qry.append(" and pws.id.stynumber=:styno and pws.subsectiontype='A' ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).setParameter("acadYr", acadYr)
                    .setParameter("programid", programid)
                    .setParameter("sectionid", sectionid)
                    .setParameter("subsectionid", subsectionid)
                    .setParameter("styno", styno)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSectionCode(String instituteid, String regid, String acad_year, String programid, String branchid, String stynumber) {
        StringBuilder qry = new StringBuilder();
        qry.append("select distinct sm.sectioncode,sm.id.sectionid from SectionMaster sm   where sm.id.sectionid in(");
        qry.append("  select distinct sr.sectionid from StudentRegistration_info sr where sr.id.instituteid='" + instituteid + "'");
        qry.append(" and sr.id.registrationid= '" + regid + "' and sr.academicyear='" + acad_year + "' and sr.programid='" + programid + "' and sr.branchid in(" + branchid + ") and sr.stynumber in(" + stynumber + ")) order by sm.sectioncode ");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    @Override
    public List<Object[]> fetchStudentData(String instituteId, String acadYear, String progId, String secId, String indBulkFlag, String studentId) {
        List list = null;
        String criteriaqry = "select sm.instituteid,sm.studentid,sm.enrollmentno,sm.name,sm.rank,sm.programid,sm.acadyear,sm.branchid,sm.sectionid,sm.subsectionid,sm.stynumber,"
                + " pm.programcode,sec.sectioncode,sm.quotaid,sm.lateralentry from StudentMaster sm ,ProgramMaster pm, SectionMaster  sec where sm.instituteid=pm.id.instituteid and sm.programid=pm.id.programid"
                + " and sm.instituteid = sec.id.instituteid and sm.sectionid = sec.id.sectionid and coalesce(sm.programid,'N')<>'N' and coalesce(sm.branchid,'N')<>'N'and coalesce(sm.activestatus,'A')='A'"
                + " and sm.instituteid =:instituteid and sm.acadyear =:acadyear and sm.programid =:progId and sm.sectionid=:secId ";
        if (indBulkFlag.equals("I")) {
            criteriaqry = criteriaqry + " and  sm.studentid=:studentid ";
        }
        criteriaqry = criteriaqry + " order by sm.enrollmentno asc";
        try {
            if (indBulkFlag.equals("I")) {
                list = getHibernateSession().createQuery(criteriaqry).
                        setParameter("instituteid", instituteId).setParameter("acadyear", acadYear)
                        .setParameter("progId", progId)
                        .setParameter("secId", secId)
                        .setParameter("studentid", studentId)
                        .list();
            } else {
                list = getHibernateSession().createQuery(criteriaqry).
                        setParameter("instituteid", instituteId).setParameter("acadyear", acadYear)
                        .setParameter("progId", progId)
                        .setParameter("secId", secId)
                        .list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Object[]> fetchStudentDataForNewReg(String instituteId, String acadYear, String progId, String secId, String indBulkFlag, String studentId) {
        List list = null;
        String criteriaqry = "select sm.instituteid,sm.studentid,sm.enrollmentno,sm.name,sm.rank,sm.programid,sm.acadyear,sm.branchid, (select sec2.id.sectionid  from SectionMaster sec2 where  sm.id.instituteid=sec2.id.instituteid "
                + " and sm.sectionid=sec2.id.sectionid and sec2.id.instituteid=:instituteid) as sectionid, sm.subsectionid,sm.stynumber,"
                + " pm.programcode,(select sec1.sectioncode  from SectionMaster sec1 where  sm.id.instituteid=sec1.id.instituteid "
                + " and sm.sectionid=sec1.id.sectionid and sec1.id.instituteid=:instituteid) as sectioncode, "
                + " sm.quotaid,sm.lateralentry from StudentMaster sm ,ProgramMaster pm where sm.instituteid=pm.id.instituteid and sm.programid=pm.id.programid "
                + " and coalesce(sm.programid,'N')<>'N' and coalesce(sm.branchid,'N')<>'N'and coalesce(sm.activestatus,'A')='A'"
                + " and sm.instituteid =:instituteid and sm.acadyear =:acadyear and sm.programid =:progId and ( sm.stynumber = 1 or ( sm.stynumber = 3 and coalesce(sm.lateralentry,'N')='Y'))";
        if (indBulkFlag.equals("I")) {
            criteriaqry = criteriaqry + " and  sm.studentid=:studentid ";
        }
        criteriaqry = criteriaqry + " order by sm.enrollmentno asc";
        try {
            if (indBulkFlag.equals("I")) {
                list = getHibernateSession().createQuery(criteriaqry).
                        setParameter("instituteid", instituteId).setParameter("acadyear", acadYear)
                        .setParameter("progId", progId)
                        .setParameter("studentid", studentId)
                        .list();
            } else {
                list = getHibernateSession().createQuery(criteriaqry).
                        setParameter("instituteid", instituteId).setParameter("acadyear", acadYear)
                        .setParameter("progId", progId)
                        .list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Object[]> getRegAllData(String instituteId, String regId, String acadYear, String progId, String secId) {
        List list = null;
        String criteriaqry = "select sr.id.instituteid,sr.id.registrationid,sr.id.studentid,sr.stynumber,sr.regallow,sr.preventfrom,sr.preventto,sr.regallowdate,sr.remarks "
                + " ,st.stytype from StudentRegistration sr,StyType st where sr.stytypeid=st.id.stytypeid and sr.id.instituteid=st.id.instituteid"
                + " and exists(select 1 from StudentRegistration_info si where  sr.id.instituteid=si.id.instituteid  and sr.id.registrationid=si.id.registrationid  "
                + "  and sr.id.studentid=si.id.studentid  "
                + "  and si.academicyear=:acadYear  and si.id.instituteid=:instituteid  "
                + " and si.id.registrationid=:registrationid and si.programid=:progId and si.sectionid=:secId)  "
                + " and sr.id.instituteid=:instituteid and sr.id.registrationid=:registrationid ";
        try {
            list = getHibernateSession().createQuery(criteriaqry).
                    setParameter("instituteid", instituteId)
                    .setParameter("registrationid", regId)
                    .setParameter("acadYear", acadYear)
                    .setParameter("progId", progId)
                    .setParameter("secId", secId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Object[]> getRegAllDataForNewReg(String instituteId, String regId, String acadYear, String progId, String secId) {
        List list = null;
        String criteriaqry = "select sr.id.instituteid,sr.id.registrationid,sr.id.studentid,sr.stynumber,sr.regallow,sr.preventfrom,sr.preventto,sr.regallowdate,sr.remarks "
                + " ,st.stytype from StudentRegistration sr,StyType st where sr.stytypeid=st.id.stytypeid and sr.id.instituteid=st.id.instituteid"
                + " and exists(select 1 from StudentRegistration_info si where  sr.id.instituteid=si.id.instituteid  and sr.id.registrationid=si.id.registrationid  "
                + "  and sr.id.studentid=si.id.studentid  "
                + "  and si.academicyear=:acadYear  and si.id.instituteid=:instituteid  "
                + " and si.id.registrationid=:registrationid and si.programid=:progId )  "
                + " and sr.id.instituteid=:instituteid and sr.id.registrationid=:registrationid ";
        try {
            list = getHibernateSession().createQuery(criteriaqry).
                    setParameter("instituteid", instituteId)
                    .setParameter("registrationid", regId)
                    .setParameter("acadYear", acadYear)
                    .setParameter("progId", progId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getActivityValues(String instituteid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select am.id.activityid, am.activityname");
        qry.append(" from Sis_RegistrationActivityMaster am");
        qry.append(" where am.id.instituteid =:instituteid ");
        qry.append(" and coalesce(am.deactive,'N') = 'N' ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public List getEnrolmentNo(String instituteid, String regid, String acadyear, String programid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct a.enrollmentno, a.name, a.studentid, b.stynumber, pm.programcode, bm.branchcode, a.quotaid");
        qry.append(" from StudentMaster a, ProgramMaster pm, BranchMaster bm, StudentRegistration b");
        qry.append(" where a.instituteid =:instituteid ");
        qry.append(" and a.acadyear =:acadyear ");
        qry.append(" and a.instituteid = pm.id.instituteid ");
        qry.append(" and a.programid = pm.id.programid ");
        qry.append(" and pm.id.instituteid = bm.id.instituteid ");
        qry.append(" and pm.id.programid = bm.id.programid ");
        qry.append(" and a.branchid = bm.id.branchid ");
        qry.append(" and b.id.studentid = a.studentid");
        qry.append(" and b.id.instituteid = a.instituteid");
        qry.append(" and b.id.registrationid =:regid ");
        qry.append(" and coalesce(b.regallow,'Y') = 'Y' ");
        if (!programid.equalsIgnoreCase("All")) {
            qry.append(" and pm.id.programid =:programid) ");
        }
        qry.append(" and :programid=:programid order by a.enrollmentno");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("regid", regid).
                    setParameter("acadyear", acadyear).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public List getStudentWiseNoDuesGridData(String instituteid, String regid, String acadyear, String programid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select ac.id.registrationid,ac.id.studentid,ac.id.activityid,a.enrollmentno, a.name, a.stynumber, pm.programcode, bm.branchcode, ac.allowforregistration, ac.processed, ac.remarks, reg.activityname");
        qry.append(" from Sis_StudentRegActivities ac, StudentMaster a, ProgramMaster pm, BranchMaster bm, Sis_RegistrationActivityMaster reg");
        qry.append(" where ac.id.instituteid = a.instituteid and ac.id.studentid = a.studentid and ac.id.instituteid =:instituteid ");
        qry.append(" and ac.id.instituteid = reg.id.instituteid and ac.id.activityid = reg.id.activityid and a.acadyear =:acadyear ");
        qry.append(" and a.instituteid = pm.id.instituteid ");
        qry.append(" and a.programid = pm.id.programid ");
        qry.append(" and pm.id.instituteid = bm.id.instituteid ");
        qry.append(" and pm.id.programid = bm.id.programid ");
        if (!programid.equalsIgnoreCase("All")) {
            qry.append(" and pm.id.programid =:programid");
        }
        qry.append(" and :programid=:programid");
        qry.append(" and a.branchid = bm.id.branchid ");
        qry.append(" and ac.id.registrationid =:regid ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("regid", regid).
                    setParameter("acadyear", acadyear).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public List checkDuplicateEntry(String instituteid, String registrationid, String studentid, String activityid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select ac.id.registrationid");
        qry.append(" from Sis_StudentRegActivities ac");
        qry.append(" where ac.id.instituteid =:instituteid ");
        qry.append(" and ac.id.activityid =:activityid ");
        qry.append(" and ac.id.registrationid =:registrationid ");
        qry.append(" and ac.id.studentid =:studentid ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("studentid", studentid).setParameter("activityid", activityid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public void saveStudentWiseNoDues(Sis_StudentRegActivities record) {
        Session session = null;
        Transaction tx = null;

        try {
            session = getSession();
            tx = session.beginTransaction();
            session.save(record);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public void saveAcadWiseNoDues(List objects) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                session.saveOrUpdate(object);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public List getRegistrationNoDuesStatusReportData(String instituteid, String regid, String acadyear, String program, byte semester, List activityids) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select a.enrollmentno, a.name, pm.programcode, bm.branchcode, a.stynumber, reg.activityname, ac.remarks,ac.processed,ac.id.activityid, ac.allowforregistration, ac.approvalby,ac.approvaldate,ac.approvalremarks");
        qry.append(" from Sis_StudentRegActivities ac, StudentMaster a, ProgramMaster pm, BranchMaster bm, Sis_RegistrationActivityMaster reg");
        qry.append(" where ac.id.instituteid = a.instituteid and ac.id.studentid = a.studentid and ac.id.instituteid =:instituteid ");
        qry.append(" and ac.id.instituteid = reg.id.instituteid and ac.id.activityid = reg.id.activityid and a.acadyear =:acadyear ");
        qry.append(" and a.instituteid = pm.id.instituteid and a.programid =:program");
        qry.append(" and a.programid = pm.id.programid  and a.stynumber =:semester");
        qry.append(" and pm.id.instituteid = bm.id.instituteid ");
        qry.append(" and pm.id.programid = bm.id.programid ");
        qry.append(" and a.branchid = bm.id.branchid ");
        qry.append(" and ac.id.registrationid =:regid  and ac.id.activityid in(:activityids)");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear)
                    .setParameter("program", program).setParameter("semester", semester).setParameterList("activityids", activityids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public List getApprovalAuthorities(String instituteid, List activityids) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select r.id.activityid,v.employeename from  Sis_RegistrationActivityRights r, V_Staff v");
        qry.append(" where r.id.staffid=v.employeeid and  r.id.instituteid =:instituteid ");
        qry.append(" and r.id.activityid in(:activityids)");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).setParameterList("activityids", activityids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;

    }

    public List getParametervalueFOrRegistrationConfirmation(String instituteid, String moduleid, String parameterid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.parametervalue "
                + " from Parameters rm "
                + " where rm.id.instituteid=:instituteid "
                + " and rm.id.moduleid=:moduleid "
                + " and rm.id.parameterid=:parameterid  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("moduleid", moduleid).
                    setParameter("parameterid", parameterid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    @Override
    public void delteteStudentRegistrationData(String instituteid, String registrationid, String studentid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete StudentRegistration s where s.id.instituteid =:instituteid and s.id.registrationid =:registrationid "
                + " and s.id.studentid =:studentid");
        try {
            Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid);
            qry.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getStudentStytypeid(String instituteid, String registrationid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        String stytypeid = "";
        sb.append("select s.stytypeid from StudentRegistration s where s.id.instituteid =:instituteid and s.id.registrationid =:registrationid "
                + " and s.id.studentid =:studentid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).list();
            if (list.size() > 0) {
                stytypeid = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return stytypeid;

    }
}
