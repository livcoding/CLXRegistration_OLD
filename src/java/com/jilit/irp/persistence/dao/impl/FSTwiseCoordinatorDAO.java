package com.jilit.irp.persistence.dao.impl;

/**
 *
 * @author ashok.singh
 */
import com.jilit.irp.persistence.dao.FSTwiseCoordinatorIDAO;
import java.util.List;
import org.hibernate.Session;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.FSTwiseCoordinator;

public class FSTwiseCoordinatorDAO extends HibernateDAO implements FSTwiseCoordinatorIDAO {

    private static final Log log = LogFactory.getLog(FSTwiseCoordinatorDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all FSTwiseCoordinator records via Hibernate from the database");
        return this.find("from FSTwiseCoordinator as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(FSTwiseCoordinator.class, id);
    }

    public List getGridData(String instituteid, String subjectid,String registartionid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select fstwc.id.instituteid,"
                + " fstwc.id.coordinatortypeid, "
                + " fstwc.id.fstid, "
                + " fstwc.id.staffid, "
                + " fstwc.id.stafftype,"
                + " sc.subjectcomponentdesc,vs.employeename,vs.employeecode,ct.coordinatortypedesc,"
                + " fstwc.fromdate, "
                + " fstwc.todate, "
                + " fst.academicyear,pm.programcode,scm.sectiondesc,pws.subsectiondesc, "
                + " fst.stynumber,fstwc.id.stafftype,sm.subjectdesc from "
                + " FSTwiseCoordinator fstwc, "
                + " CoordinatorType ct, "
                + " V_Staff  vs,  "
                + " FacultySubjectTagging   fst, "
                + " SubjectMaster sm, "
                + " SectionMaster scm,  "
                + " ProgramMaster pm, "
                + " ProgramWiseSubsection pws, "
                + " SubjectComponent sc "
                + " where fstwc.subjectid=:subjectid "
                + " and fstwc.id.instituteid=:instituteid  "
                + " and fst.id.instituteid=  fstwc.id.instituteid  "
                + " and ct.id.instituteid=fstwc.id.instituteid  "
                + " and sm.id.instituteid=fstwc.id.instituteid  "
                + " and scm.id.instituteid=fstwc.id.instituteid  "
                + " and pws.id.instituteid=fstwc.id.instituteid  "
                + " and pm.id.instituteid=fstwc.id.instituteid  "
                + " and sc.id.instituteid=fstwc.id.instituteid "
                + " and fst.registrationid=:registartionid  "
                + " and fst.id.fstid=fstwc.id.fstid  "
                + " and vs.employeeid=fstwc.id.staffid  "
                + " and ct.id.coordinatortypeid=fstwc.id.coordinatortypeid  "
                + " and sm.id.subjectid=fstwc.subjectid  "
                + " and scm.id.sectionid=fst.sectionid  "
                + " and pws.id.sectionid=fst.sectionid  "
                + " and pws.id.subsectionid=fst.subsectionid  "
                + " and pws.id.academicyear=fst.academicyear  "
                + " and pws.id.programid=fst.programid  "
                + " and pws.id.stynumber=fst.stynumber  "
                + " and pm.id.programid=fst.programid  "
                + " and sc.id.subjectcomponentid=fst.subjectcomponentid  "
                + " order by fst.academicyear desc,  "
                + " pm.programcode,scm.sectiondesc,pws.subsectiondesc,fst.stynumber,sc.subjectcomponentdesc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("registartionid", registartionid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getCoordinatorType(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ct.id.coordinatortypeid,ct.coordinatortypecode,ct.coordinatortypedesc,ct.coordinatortype "
                + " from CoordinatorType ct where ct.id.instituteid=:instituteid "
                + "and ct.coordinatortype!='A'  and coalesce(ct.deactive,'N') = 'N'");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStaffCode1(String instituteid, String regId, String subjectId) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select vs.employeeid,vs.employeecode,vs.employeename,vs.stafftype from V_Staff vs where  "
                + " exists (select 1 from TT_TimeTableAllocation tt  "
                + " where tt.id.instituteid=:instituteid and tt.id.registrationid=:registrationId "
                + " and tt.subjectid=:subjectId and tt.staffid=vs.employeeid and coalesce(tt.deactive,'N') = 'N' ) ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", regId).
                    setParameter("subjectId", subjectId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStaffCode2(String instituteid, String staffType) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select vs.employeeid,vs.employeecode,vs.employeename,vs.stafftype from V_Staff vs where "
                + " vs.placeofpostingid in (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N'))   ");
        if (staffType.equalsIgnoreCase("TEACHING")) {
            sb.append(" and vs.employeetype = 'TEACHING' ");
        } else if (staffType.equalsIgnoreCase("NTEACHING")) {
            sb.append(" and vs.employeetype != 'TEACHING' ");
        } else {
        }
        sb.append(" order by vs.employeename ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAddGridData(String instituteid, String registrationid, String subjectid, String subjectcomponentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fst.id.instituteid,fst.id.fstid, "
                + " sc.subjectcomponentdesc,fst.academicyear,"
                + " pm.programdesc,sm.sectiondesc, "
                + " pwss.subsectiondesc,fst.stynumber, "
                + " (select count (*) "
                + " from FSTwiseCoordinator fstwc "
                + " where fstwc.id.instituteid = fst.id.instituteid "
                + " and fstwc.id.fstid = fst.id.fstid "
                + " and fstwc.subjectid = fst.subjectid) "
                + " as flag "
                + " from FacultySubjectTagging fst,ProgramMaster pm, "
                + " SectionMaster sm,SubjectComponent sc, "
                + " ProgramWiseSubsection pwss "
                + " where  fst.id.instituteid =:instituteid "
                + " and pm.id.instituteid = fst.id.instituteid "
                + " and sm.id.instituteid = fst.id.instituteid "
                + " and sc.id.instituteid = fst.id.instituteid "
                + " and pwss.id.instituteid = fst.id.instituteid "
                + " and pm.id.programid = fst.programid "
                + " and sm.id.sectionid = fst.sectionid "
                + " and sc.id.subjectcomponentid = fst.subjectcomponentid "
                + " and pwss.id.programid = fst.programid "
                + " and pwss.id.subsectionid = fst.subsectionid "
                + " and pwss.id.academicyear = fst.academicyear "
                + " and pwss.id.stynumber = fst.stynumber "
                + " and pwss.id.subsectionid=fst.subsectionid"
                + " and fst.registrationid =:registrationid "
                + " and fst.subjectid =:subjectid "
                + " and fst.subjectcomponentid =:subjectcomponentid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("subjectcomponentid", subjectcomponentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
