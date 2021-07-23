/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.V_StudentComponentDetailIDAO;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.V_StudentComponentDetail;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

/**
 *
 * @author v.kumar
 */
public class V_StudentComponentDetailDAO extends HibernateDAO implements V_StudentComponentDetailIDAO {

    private static final Log log = LogFactory.getLog(V_StudentComponentDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all V_StudentComponentDetail records via Hibernate from the database");
        return this.find("from V_StudentComponentDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(V_StudentComponentDetail.class, id);
    }

    public List getClassWiseStudentList(String registrationid, String sectionid, String subjectcomponentid, String subjectid, String subsectionid, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct s.name ,s.enrollmentno,s.studentid,sub.subjectcode,sub.subjectdesc,sc.subjectcomponentcode,sc.subjectcomponentdesc,fst.id.instituteid, fst.id.fstid, pm.programcode "
                + " from StudentMaster s, FacultyStudentTagging sft, FacultySubjectTagging fst,TT_TimeTableAllocation tt, V_Staff v, SubjectMaster sub, SubjectComponent sc, ProgramMaster pm "
                + " where fst.id.instituteid = sft.id.instituteid and tt.staffid = v.employeeid and v.stafftype = tt.stafftype and sub.id.instituteid = fst.id.instituteid "
                + " and sub.id.subjectid = fst.subjectid and fst.id.fstid = sft.fstid and fst.id.instituteid = tt.id.instituteid and fst.registrationid = tt.id.registrationid "
                + " and fst.ttreferenceid=tt.ttreferenceid and fst.ttreferenceid = tt.ttreferenceid and s.studentid = sft.id.studentid and fst.id.instituteid = sc.id.instituteid "
                + " and fst.subjectcomponentid = sc.id.subjectcomponentid and pm.id.programid = fst.programid and pm.id.instituteid = fst.id.instituteid "
                + " and fst.subjectcomponentid in (select sc.id.subjectcomponentid from SubjectComponent sc where sc.id.instituteid=tt.id.instituteid and sc.subjectcomponentcode=:subjectcomponentid) "
                + " and ((fst.registrationid=:registrationid and fst.id.instituteid = :instituteid "
                + " and fst.subjectid=:subjectid and fst.subsectionid=:subsectionid and fst.sectionid=:sectionid) "
                + " or exists( select cl.id.instituteid from SubjectForCommonLoad cl where cl.id.maininstituteid=:instituteid "
                + " and cl.id.mainregistrationid=:registrationid and cl.id.instituteid= sft.id.instituteid and cl.id.registrationid= sft.registrationid "
                + " and cl.id.subjectid= sft.subjectid )) ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("registrationid", registrationid).
                    setParameter("sectionid", sectionid).
                    setParameter("subjectcomponentid", subjectcomponentid).
                    setParameter("subjectid", subjectid).
                    setParameter("subsectionid", subsectionid).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSubjectFacultyCumCoordinatorWiseStudentList(String instituteid, String registrationid, String subjectid, String facultyid, String coordinatorid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        if (!facultyid.equals("")) {
            sb.append(" select  distinct v.employeename,v.employeecode,pm.programcode,sec.sectioncode,pws.subsectiondesc,sm.name,sm.enrollmentno,pws.subsectioncode "
                    + " from FacultySubjectTagging fst, FacultyStudentTagging sft, TT_TimeTableAllocation tt , StudentMaster sm, V_Staff v, SectionMaster sec, ProgramMaster pm, ProgramWiseSubsection pws "
                    + " where fst.id.instituteid = sft.id.instituteid and fst.id.fstid = sft.fstid and fst.id.instituteid = tt.id.instituteid and fst.ttreferenceid = tt.ttreferenceid "
                    + " and fst.id.instituteid = pm.id.instituteid and pm.id.programid = fst.programid and fst.id.instituteid = sec.id.instituteid and fst.sectionid = sec.id.sectionid "
                    + " and sft.id.instituteid = sm.instituteid and sft.id.studentid = sm.studentid and fst.id.instituteid = pws.id.instituteid and fst.academicyear = pws.id.academicyear "
                    + " and fst.programid = pws.id.programid and fst.stynumber = pws.id.stynumber and fst.sectionid = pws.id.sectionid and pws.id.subsectionid = fst.subsectionid "
                    + " and tt.staffid = v.employeeid and fst.subjectid=:subjectid and v.employeeid=:facultyid and fst.id.instituteid=:instituteid and fst.registrationid=:registrationid ");
            try {
                list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).setParameter("facultyid", facultyid).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sb = null;
            }
        }
        if (!coordinatorid.equals("")) {
            sb.append(" select distinct v.employeename,v.employeecode,pm.programcode,sec.sectioncode,pws.subsectiondesc,sm.name,sm.enrollmentno,pws.subsectioncode "
                    + " from FacultySubjectTagging fst, FacultyStudentTagging sft, FSTwiseCoordinator tt , StudentMaster sm, V_Staff v, SectionMaster sec, ProgramMaster pm, ProgramWiseSubsection pws "
                    + " where fst.id.instituteid=sft.id.instituteid and fst.id.fstid=sft.fstid and fst.id.instituteid=tt.id.instituteid and fst.id.fstid=tt.id.fstid "
                    + " and fst.id.instituteid=pm.id.instituteid and pm.id.programid=fst.programid and fst.id.instituteid =sec.id.instituteid and fst.sectionid=sec.id.sectionid "
                    + " and sft.id.instituteid=sm.instituteid and sft.id.studentid=sm.studentid and fst.id.instituteid=pws.id.instituteid and fst.academicyear=pws.id.academicyear "
                    + " and fst.programid=pws.id.programid and fst.stynumber=pws.id.stynumber and fst.sectionid=pws.id.sectionid and pws.id.subsectionid=fst.subsectionid "
                    + " and tt.id.staffid = v.employeeid and fst.subjectid = :subjectid and fst.id.instituteid = :instituteid and fst.registrationid=:registrationid and tt.id.staffid=:coordinatorid "
                    + " and tt.id.coordinatortypeid in(select ctt.id.coordinatortypeid from CoordinatorType ctt where ctt.id.instituteid=tt.id.instituteid and ctt.coordinatortype='A') ");
            try {
                list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).setParameter("coordinatorid", coordinatorid).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sb = null;
            }
        }
        if (facultyid.equals("") && coordinatorid.equals("")) {
            sb.append(" select  distinct v.employeename,v.employeecode,pm.programcode,sec.sectioncode,pws.subsectiondesc,sm.name,sm.enrollmentno,pws.subsectioncode ");
            sb.append(" from FacultySubjectTagging fst, FacultyStudentTagging sft, TT_TimeTableAllocation tt , StudentMaster sm, V_Staff v, SectionMaster sec, ProgramMaster pm, ProgramWiseSubsection pws ");
            sb.append(" where fst.instituteid = sft.instituteid and fst.fstid = sft.fstid and fst.instituteid = tt.instituteid and fst.ttreferenceid = tt.ttreferenceid ");
            sb.append(" and fst.instituteid = pm.instituteid and pm.programid = fst.programid and fst.instituteid = sec.instituteid and fst.sectionid = sec.sectionid ");
            sb.append(" and sft.instituteid = sm.instituteid and sft.studentid = sm.studentid and fst.instituteid = pws.instituteid and fst.academicyear = pws.academicyear ");
            sb.append(" and fst.programid = pws.programid and fst.stynumber = pws.stynumber and fst.sectionid = pws.sectionid and pws.subsectionid = fst.subsectionid ");
            sb.append(" and tt.staffid = v.employeeid and fst.subjectid='" + subjectid + "' and fst.instituteid='" + instituteid + "' and fst.registrationid='" + registrationid + "' ");
            sb.append(" UNION ");
            sb.append(" select distinct v.employeename,v.employeecode,pm.programcode,sec.sectioncode,pws.subsectiondesc,sm.name,sm.enrollmentno,pws.subsectioncode ");
            sb.append(" from FacultySubjectTagging fst, FacultyStudentTagging sft, FSTwiseCoordinator tt , StudentMaster sm, V_Staff v, SectionMaster sec, ProgramMaster pm, ProgramWiseSubsection pws ");
            sb.append(" where fst.instituteid=sft.instituteid and fst.fstid=sft.fstid and fst.instituteid=tt.instituteid and fst.fstid=tt.fstid ");
            sb.append(" and fst.instituteid=pm.instituteid and pm.programid=fst.programid and fst.instituteid =sec.instituteid and fst.sectionid=sec.sectionid ");
            sb.append(" and sft.instituteid=sm.instituteid and sft.studentid=sm.studentid and fst.instituteid=pws.instituteid and fst.academicyear=pws.academicyear ");
            sb.append(" and fst.programid=pws.programid and fst.stynumber=pws.stynumber and fst.sectionid=pws.sectionid and pws.subsectionid=fst.subsectionid ");
            sb.append(" and tt.staffid = v.employeeid and fst.subjectid = '" + subjectid + "' and fst.instituteid = '" + instituteid + "' and fst.registrationid='" + registrationid + "' ");
            System.out.println("gg" + sb.toString());
            try {
                list = getHibernateSession().createSQLQuery(sb.toString()).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid) {
        List list = new ArrayList();
        String criteriaqry = "select fst.id.studentid from FacultyStudentTagging fst,FacultySubjectTagging ft where fst.id.instituteid=ft.id.instituteid and fst.fstid=ft.id.fstid and fst.id.instituteid=:instituteid"
                + " and ft.registrationid=:registrationid and fst.id.studentid=:studentid ";
        try {
            list = getHibernateSession().createQuery(criteriaqry).
                    setParameter("instituteid", instituteid)
                    .setParameter("registrationid", registrationid)
                    .setParameter("studentid", studentid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
