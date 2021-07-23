package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentRegistration_infoIDAO;
import com.jilit.irp.persistence.dto.StudentRegistration_info;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 *
 * @author akshya.gaur
 */
public class StudentRegistration_infoDAO extends HibernateDAO implements StudentRegistration_infoIDAO {

    private static final Log log = LogFactory.getLog(StudentRegistration_infoDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentRegistration_info records via Hibernate from the database");
        return this.find("from StudentRegistration_info as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentRegistration_info.class, id);
    }

    public List getStudentRegistration_InfoData(String instituteid, String registrationid, String studentid) {
        String qry = "from StudentRegistration_info s "
                + " where s.id.instituteid =:instituteid "
                + " and s.id.registrationid = :registrationid "
                + " and s.id.studentid =:studentid ";
        return getHibernateSession().createQuery(qry).
                setParameter("instituteid", instituteid).setParameter("registrationid", registrationid)
                .setParameter("studentid", studentid)
                .list();
    }

    public List getStudentRegData(String instituteid, String regId, String academicyear, String styNum, String branchId, String sectionId) {
        List list = new ArrayList();
        StringBuilder qry = new StringBuilder();
        qry.append("select sm.enrollmentno, sm.name, sm.id.studentid, ");
        qry.append("(select sec.sectioncode from SectionMaster sec where sec.id.sectionid = sri.sectionid and sec.id.instituteid = sri.id.instituteid), ");
        qry.append(" (select pws.subsectioncode from ProgramWiseSubsection pws where");
        qry.append(" pws.id.stynumber = sri.stynumber and pws.id.academicyear = sri.academicyear and ");
        qry.append(" pws.branchid = sri.branchid and  pws.id.programid = sri.programid and pws.id.subsectionid = sri.subsectionid and pws.id.sectionid = sri.sectionid and pws.id.instituteid = sri.id.instituteid) as subsectioncode, ");
        qry.append(" (select coalesce(sr.regallow,'N') from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as regallow,");
        qry.append(" (select coalesce(sr.regconfirmation,'N') from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid)as regconfirmation,");
        qry.append(" (select sr.regconfirmatiodate from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as regconfirmatiodate,");
        qry.append(" (select coalesce(sr.preventfreezed,'N') from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as preventfreezed,");
        qry.append(" (select sr.prfreezeddate from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as prfreezeddate,");
        qry.append(" (select sr.preventfrom from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as preventfrom,");
        qry.append(" (select sr.preventto from StudentRegistration sr where sr.id.instituteid = sri.id.instituteid and sr.id.registrationid = sri.id.registrationid and sr.id.studentid = sri.id.studentid) as preventto");
        qry.append(" from StudentRegistration_info sri, StudentMaster sm ");
        qry.append(" where sri.id.instituteid=sm.id.instituteid and sri.id.studentid=sm.id.studentid ");
        qry.append(" and sri.id.instituteid= :instituteid and  sri.id.registrationid = :registrationid and sri.academicyear = :academicyear ");
        qry.append(" and sri.branchid = :branchid ");
        qry.append(" and sri.stynumber = :stynumber and sri.sectionid = :sectionid ");
        qry.append(" and sri.stytypeid = (select st.id.stytypeid from StyType st where st.id.instituteid = sri.id.instituteid and st.stytype = 'REG') ");
        qry.append(" group by sm.enrollmentno, sm.name, sm.id.studentid, sri.stynumber, sri.sectionid , sri.id.instituteid, sri.subsectionid, sri.branchid, sri.programid, sri.programid, sri.academicyear,sri.id.registrationid,sri.id.studentid ");
        qry.append(" order by sm.enrollmentno ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).
                    setParameter("registrationid", regId).
                    setParameter("stynumber", Byte.parseByte(styNum)).
                    setParameter("branchid", branchId).
                    setParameter("sectionid", sectionId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;
    }

    @Override
    public List getStudentMasterData(String instituteid, String acadYear, String styNum, String branchId, String sectionId) {
        List list = new ArrayList();
        StringBuilder qry = new StringBuilder();
        qry.append("select sm.enrollmentno, sm.name, sm.id.studentid, ");
        qry.append("(select sec.sectioncode from SectionMaster sec where sec.id.sectionid = sm.sectionid and sec.id.instituteid = sm.instituteid), ");
        qry.append(" (select pws.subsectioncode from ProgramWiseSubsection pws where");
        qry.append(" pws.id.stynumber = sm.stynumber and pws.id.academicyear = sm.acadyear and ");
        qry.append(" pws.branchid = sm.branchid and  pws.id.programid = sm.programid and pws.id.subsectionid = sm.subsectionid and pws.id.sectionid = sm.sectionid and pws.id.instituteid = sm.instituteid), ");
        qry.append(" (select pws.subsectioncode from ProgramWiseSubsection pws where");
        qry.append(" pws.id.stynumber = (sm.stynumber+1) and pws.id.academicyear = sm.acadyear and ");
        qry.append(" pws.branchid = sm.branchid and  pws.id.programid = sm.programid and pws.id.subsectionid = sm.subsectionid and pws.id.sectionid = sm.sectionid and pws.id.instituteid = sm.instituteid), ");
        qry.append(" sm.activestatus ");
        qry.append(" from StudentMaster sm ");
        qry.append(" where sm.instituteid= :instituteid  and sm.acadyear = :acadyear ");
        qry.append(" and sm.branchid = :branchid ");
        qry.append(" and sm.stynumber = :stynumber and sm.sectionid = :sectionid ");
        qry.append(" group by sm.enrollmentno, sm.name, sm.id.studentid, sm.stynumber,sm.sectionid , sm.instituteid,sm.subsectionid,sm.branchid,sm.programid,sm.programid,sm.academicyear,sm.activestatus  ");
        qry.append(" order by sm.enrollmentno ");
        try {
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("acadyear", acadYear).
                    setParameter("branchid", branchId).
                    setParameter("stynumber", Byte.parseByte(styNum)).
                    setParameter("sectionid", sectionId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }
        return list;
    }

    public List getStyNoFOrNoDuesReport(String instituteid, String regid, String acadyear, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct stynumber from StudentRegistration_info where academicyear=:acadyear "
                + " and id.registrationid =:regid  and id.instituteid=:instituteid  and programid=:programid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    @Override
    public void deleteStudentRegistration_InfoData(String instituteid, String registrationid, String studentid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete StudentRegistration_info s where s.id.instituteid =:instituteid and s.id.registrationid = :registrationid "
                + "and s.id.studentid =:studentid");
        try {
            Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid);
            qry.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getStyNoFOrStudentBackPaperReport(String instituteid, String registrationid, String programid, String branchid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sri.stynumber from StudentRegistration_info sri "
                + "  where sri.id.instituteid=:instituteid ");
        if (!registrationid.equals("")) {
            sb.append("  and sri.id.registrationid=:registrationid");
        } else {
            registrationid = "null";
            sb.append("  and :registrationid=:registrationid");
        }
        if (!branchid.equalsIgnoreCase("All")) {
            sb.append("  and sri.branchid=:branchid");
        } else {
            sb.append("  and :branchid=:branchid");
        }
        sb.append("  and sri.programid=:programid "
                + "  order by sri.stynumber ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("programid", programid).setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }
}
