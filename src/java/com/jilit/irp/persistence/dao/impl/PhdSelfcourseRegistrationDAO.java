package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PhdSelfcourseRegistrationIDAO;
import com.jilit.irp.persistence.dto.PhdSelfcourseRegistration;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ankit.kumar
 */
public class PhdSelfcourseRegistrationDAO extends HibernateDAO implements PhdSelfcourseRegistrationIDAO {

    private static final Log log = LogFactory.getLog(PhdSelfcourseRegistrationDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PhdSelfcourseRegistration records via Hibernate from the database");
        return this.find("from PhdSelfcourseRegistration as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PhdSelfcourseRegistration.class, id);
    }

    public List getPHDSubjectRegistrationDataPending(String instituteid, String registrationid, String status) {
        List list = null;
        final StringBuilder sb = new StringBuilder();
        sb.append(" select sm.enrollmentno,sm.studentid, sm.name, stureg.stynumber, phdreg.subjectcode, phdreg.id.subjectid, "
                + " sm.acadyear, sm.programid, sm.sectionid,sm.subsectionid,phdreg.credits, (select  dst.id.departmentid from DepartmentSubjectTagging dst "
                + " where dst.id.instituteid = phdreg.id.instituteid and rownum=1 and dst.id.subjectid = phdreg.id.subjectid) as departmentid,(select distinct "
                + " bm.id.basketid from BasketMaster bm where bm.id.instituteid = phdreg.id.instituteid and bm.programid = sm.programid and bm.sectionid = sm.sectionid "
                + " and bm.stynumber=stureg.stynumber and rownum=1) as basketid , phdreg.subjectdesc from PhdSelfcourseRegistration phdreg, "
                + " StudentRegistration stureg, StudentMaster sm, SubjectMaster sub where phdreg.id.instituteid = :instituteid and phdreg.id.registrationid = :registrationid"
                + " and phdreg.id.instituteid=stureg.id.instituteid and phdreg.id.registrationid = stureg.id.registrationid and phdreg.id.studentid=stureg.id.studentid and "
                + " phdreg.id.instituteid = sm.instituteid and sub.id.instituteid=phdreg.id.instituteid and sub.id.subjectid = phdreg.id.subjectid and phdreg.id.studentid = sm.studentid "
                + " and coalesce(phdreg.deactive,'N')='N' and coalesce(sm.activestatus,'A')='A' and coalesce(stureg.regallow,'N')='Y' and coalesce(status,'P')='P' "
                + " order by sm.enrollmentno,sm.studentid, sm.name, stureg.stynumber, phdreg.subjectcode,phdreg.subjectdesc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getPHDSubjectRegistrationDataApprove(String instituteid, String registrationid, String status) {
        List list = null;
        final StringBuilder sb = new StringBuilder();
        sb.append(" select sm.enrollmentno,sm.studentid, sm.name, stureg.stynumber, phdreg.subjectcode, phdreg.id.subjectid, "
                + " sm.acadyear, sm.programid, sm.sectionid,sm.subsectionid,phdreg.credits, (select dst.id.departmentid from DepartmentSubjectTagging dst "
                + " where dst.id.instituteid = phdreg.id.instituteid and rownum = 1 and dst.id.subjectid = phdreg.id.subjectid) as departmentid,(select distinct "
                + " bm.id.basketid from BasketMaster bm where bm.id.instituteid = phdreg.id.instituteid and bm.programid = sm.programid and bm.sectionid = sm.sectionid "
                + " and bm.stynumber = stureg.stynumber and rownum=1) as basketid,phdreg.subjectdesc from PhdSelfcourseRegistration phdreg, "
                + " StudentRegistration stureg, StudentMaster sm, SubjectMaster sub where phdreg.id.instituteid = :instituteid and phdreg.id.registrationid = :registrationid "
                + " and phdreg.id.instituteid = stureg.id.instituteid and phdreg.id.registrationid = stureg.id.registrationid and phdreg.id.studentid = stureg.id.studentid "
                + " and phdreg.id.instituteid = sm.instituteid and sub.id.instituteid = phdreg.id.instituteid and sub.id.subjectid = phdreg.id.subjectid and "
                + " phdreg.id.studentid = sm.studentid and coalesce(phdreg.deactive,'N')='N' and coalesce(sm.activestatus,'A')='A' and coalesce(stureg.regallow,'N')='Y' "
                + " and coalesce(status,'P')='A' order by sm.enrollmentno,sm.studentid, "
                + " sm.name, stureg.stynumber, phdreg.subjectcode,phdreg.subjectdesc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getPHDSubjectRegistrationDataCancelled(String instituteid, String registrationid, String status) {
        List list = null;
        final StringBuilder sb = new StringBuilder();
        sb.append(" select sm.enrollmentno,sm.studentid, sm.name, stureg.stynumber, phdreg.subjectcode, phdreg.id.subjectid, "
                + " sm.acadyear, sm.programid, sm.sectionid,sm.subsectionid,phdreg.credits, (select dst.id.departmentid from DepartmentSubjectTagging dst "
                + " where dst.id.instituteid = phdreg.id.instituteid and rownum = 1 and dst.id.subjectid = phdreg.id.subjectid) as departmentid,(select distinct bm.id.basketid "
                + " from BasketMaster bm where bm.id.instituteid = phdreg.id.instituteid and bm.programid = sm.programid and bm.sectionid = sm.sectionid "
                + " and bm.stynumber = stureg.stynumber and rownum=1) as basketid,phdreg.subjectdesc from PhdSelfcourseRegistration phdreg, "
                + " StudentRegistration stureg, StudentMaster sm, SubjectMaster sub where phdreg.id.instituteid= :instituteid and phdreg.id.registrationid = :registrationid and "
                + " phdreg.id.instituteid=stureg.id.instituteid and phdreg.id.registrationid = stureg.id.registrationid and phdreg.id.studentid = stureg.id.studentid and "
                + " phdreg.id.instituteid=sm.instituteid and sub.id.instituteid = phdreg.id.instituteid and sub.id.subjectid=phdreg.id.subjectid and phdreg.id.studentid = sm.studentid "
                + " and coalesce(phdreg.deactive,'N')='N' and coalesce(sm.activestatus,'A')='A' and coalesce(stureg.regallow,'N')='Y' and coalesce(status,'P')='C' "
                + " order by sm.enrollmentno,sm.studentid, sm.name, stureg.stynumber, phdreg.subjectcode,phdreg.subjectdesc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSubjectWiseReportData(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sscm.id.subjectid, sbm.subjectcode,sbm.subjectdesc, count(*),(select count(*) from PhdSelfcourseRegistration sscm1, "
                + " StudentRegistration sr where sscm1.id.instituteid = :instituteid and sscm1.id.registrationid = :registrationid and "
                + " sscm1.id.subjectid = sscm.id.subjectid and coalesce(sr.sstpopulated,'N')='Y' and coalesce(sr.regconfirmation,'N')='Y' and "
                + " sscm1.id.registrationid = sr.id.registrationid and sscm1.id.instituteid = sr.id.instituteid and sscm1.id.studentid = sr.id.studentid) "
                + " from PhdSelfcourseRegistration sscm, SubjectMaster sbm where sscm.id.instituteid = :instituteid and sscm.id.registrationid = :registrationid "
                + " and sscm.id.instituteid = sbm.id.instituteid and sscm.id.subjectid = sbm.id.subjectid group by sscm.id.subjectid, sbm.subjectcode, "
                + " sbm.subjectdesc order by sscm.id.subjectid, sbm.subjectcode,sbm.subjectdesc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
