package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.DropElectiveSubjectIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author ashutosh1.kumar
 */
public class DropElectiveSubjectDao extends HibernateDAO implements DropElectiveSubjectIDAO {

    private static final Log log = LogFactory.getLog(RegistrationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from RegistrationMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RegistrationMaster.class, id);
    }

    public List getRegistrationCodeForDropElective(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select rm.id.registrationid, rm.registrationcode, rm.registrationdesc from RegistrationMaster rm"
                + " where exists (select 1 from ProgramSubjectTagging pst where pst.id.instituteid = rm.id.instituteid and pst.id.registrationid = rm.id.registrationid) "
                + " and rm.id.instituteid = :instituteid "
                + " and coalesce(rm.lockregistration,'N')='N'"
                + " and coalesce(rm.finalized,'N')='N'"
                + " and coalesce(rm.deactive,'N')='N' ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getAllSubjectType(String instituteid, String registrationid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select stm.id.subjecttypeid, stm.subjecttype, stm.subjecttypedesc from SubjectTypeMaster stm where "
                + " exists (select 1 from ProgramSubjectTagging pst where pst.id.instituteid = stm.id.instituteid and pst.id.registrationid =:registrationid) "
                + " and stm.id.instituteid =:instituteid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getSubjectToBeDroped(String instituteid, String registrationid, String subjecttypeid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sm.id.subjectid, sm.subjectcode, sm.subjectdesc  from SubjectMaster sm where"
                + " exists (select 1 from ProgramSubjectTagging pst where pst.id.instituteid = sm.id.instituteid and pst.id.registrationid =:registrationid  and pst.subjectid = sm.id.subjectid) "
                + " and sm.id.instituteid = :instituteid "
                + "  ");//and sm.subjecttypeid = :subjecttypeid
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).list();//.setParameter("subjecttypeid", subjecttypeid)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getDataInDataGrid(String instituteid, String registrationid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select  acad.id.academicyear, pm.id.programid, pm.programcode, bm.id.branchid, bm.branchcode, st.stytype, sri.stynumber, count(distinct sscm.id.studentid) as cont, st.id.stytypeid"
                + " from StudentSubjectChoiceMaster sscm, StudentRegistration_info sri, Academicyear acad, ProgramMaster pm, BranchMaster bm, StyType st"
                + " where  exists (select 1 from ProgramSubjectTagging pst where pst.id.instituteid = sscm.id.instituteid and pst.id.registrationid = sscm.id.registrationid and pst.subjectid = sscm.id.subjectid"
                + " and pst.programid =  sri.programid and pst.academicyear = sri.academicyear) "
                + " and sscm.id.instituteid = sri.id.instituteid "
                + " and sscm.id.instituteid = acad.id.instituteid "
                + " and sscm.id.instituteid = pm.id.instituteid "
                + " and sscm.id.instituteid = bm.id.instituteid "
                + " and sri.id.instituteid = st.id.instituteid "
                + " and sscm.id.registrationid = sri.id.registrationid "
                + " and sri.academicyear = acad.id.academicyear "
                + " and sri.programid = pm.id.programid "
                + " and pm.id.programid = bm.id.programid "
                + " and sri.branchid = bm.id.branchid "
                + " and sri.stytypeid = st.id.stytypeid "
                + " and sscm.id.studentid = sri.id.studentid "
                + " and sscm.id.instituteid = :instituteid "
                + " and coalesce(sscm.subjectrunning,'N')= sscm.subjectrunning "
                + " and sscm.id.registrationid =:registrationid "
                + " and sscm.id.subjectid = :subjectid "
                + " group by acad.id.academicyear, pm.id.programid, pm.programcode, bm.id.branchid, bm.branchcode, st.stytype, sri.stynumber, st.id.stytypeid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getAllStudentRecord(String instituteid, String registrationid, String subjectid, String subjecttypeid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select scm.id.subjectid, scm.subjectrunning, ssc.sgpa, ssc.cgpa, ssc.id.studentid, scm.basketid, scm.id.stynumber from StudentSgpaCgpa ssc, StudentSubjectChoiceMaster scm "
                + " where scm.id.instituteid = ssc.id.instituteid and scm.id.studentid = ssc.id.studentid and ssc.id.instituteid=:instituteid and scm.id.registrationid=:registrationid "
                + " and scm.id.subjectid = :subjectid and coalesce(scm.subjectrunning, 'Y') = 'Y' "
                + " and ssc.id.stynumber =(select max(ssc1.id.stynumber) from  StudentSgpaCgpa ssc1 where ssc.id.instituteid = ssc1.id.instituteid and ssc.id.studentid = ssc1.id.studentid)"
                + " and scm.basketid in (select bm.id.basketid from BasketMaster bm where bm.subjecttypeid=:subjecttypeid and bm.id.instituteid=:instituteid and coalesce(bm.deactive,'N') = 'N')"
                + " order by cgpa desc, ssc.id.studentid desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).setParameter("subjecttypeid", subjecttypeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getAllSubjectRecord(String instituteid, String registrationid, String studentid, String basketid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select scm.id.subjectid, scm.choice from StudentSubjectChoiceMaster scm "
                + " where scm.id.instituteid=:instituteid and scm.id.registrationid=:registrationid and scm.id.studentid=:studentid and scm.basketid=:basketid"
                + " and coalesce(subjectrunning,'N')='N' and scm.id.subjectid != :subjectid"
                + " order by scm.choice");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("studentid", studentid).setParameter("basketid", basketid).setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getTotalNoOfStudent(String instituteid, String registrationid, String basketid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select pst.noofstudents from ProgramSubjectTagging pst where "
                + " pst.id.instituteid =:instituteid and pst.id.registrationid = :registrationid and pst.subjectid = :ubjectid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("basketid", basketid).setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getTotalAssignedCount(String instituteid, String registrationid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from StudentSubjectChoiceMaster pst where "
                + " pst.id.instituteid = :instituteid and pst.id.registrationid =:registrationid and pst.id.subjectid =:subjectid "
                + " and pst.subjectrunning='Y'");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List<StudentSubjectChoiceMaster> getAllStudentRecordBatchWise(String instituteid, String registrationid, String subjectid, List acdyrlist, List prlist, List brlist, List stynolist, List stytypelist) {

        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select scm  from   StudentSubjectChoiceMaster scm "
                + " where  scm.id.instituteid=:instituteid and scm.id.registrationid=:registrationid "
                + " and scm.id.subjectid =:subjectid and coalesce(scm.subjectrunning, 'Y') = 'Y' "
                + " and exists(select  1 from  StudentRegistration_info ssc where scm.id.instituteid = ssc.id.instituteid and scm.id.studentid = ssc.id.studentid "
                + " and ssc.academicyear in (:acdyrlist) and ssc.programid in (:prlist) and ssc.branchid in (:brlist) and ssc.stynumber in (:stynolist) and ssc.stytypeid in (:stytypelist) )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).setParameterList("acdyrlist", acdyrlist).setParameterList("prlist", prlist).setParameterList("brlist", brlist).setParameterList("stynolist", stynolist).setParameterList("stytypelist", stytypelist).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

}
