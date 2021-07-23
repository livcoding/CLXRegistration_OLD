/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SubjectCoordinatorIDAO;
import com.jilit.irp.persistence.dto.SubjectCoordinator;
import com.jilit.irp.util.JIRPSession;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author s.saurabh
 */
public class SubjectCoordinatorDAO extends HibernateDAO implements SubjectCoordinatorIDAO {

    private static final Log log = LogFactory.getLog(SubjectCoordinatorDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectCoordinator records via Hibernate from the database");
        return this.find("from SubjectCoordinator as tname");
    }

    public List getGridData(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sc.id.instituteid,sm.id.subjectid,sm.subjectcode,sm.subjectdesc,vs.employeeid,vs.employeecode,vs.employeename,vs.stafftype,coalesce(sc.deactive,'N'),sc.chiefcoordinator "
                + " from SubjectCoordinator sc , SubjectMaster sm, V_Staff vs"
                + " where sc.id.instituteid= :instituteid and"
                + " sm.id.instituteid=sc.id.instituteid and"
                + " sm.id.subjectid=sc.id.subjectid"
                + " and vs.employeeid= sc.id.staffid"
                + " and vs.placeofpostingid in(select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N')) order by sc.entrydatetime desc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectCoordinator.class, id);
    }

    public List<String> doValidate(SubjectCoordinator subjectCoordinator, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getCoordinate(String ins_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Select cr.id.coordinatortypeid,cr.coordinatortype,cr.coordinatortypecode,cr.coordinatortypecode,cr.coordinatortypedesc from CoordinatorType cr where cr.id.instituteid =:ins_id and coalesce(cr.deactive, 'N')='N'");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAcademicYear(String ins_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sm.acadyear from StudentMaster sm where sm.id.instituteid=:ins_id and sm.activestatus='A' order by sm.acadyear desc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getCoordinateWithSubjectCode(String instituteid, String subjectid, String staffid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" Select sm.subjectcode, sm.subjectdesc, vs.employeecode, vs.employeename, vs.designation, vs.department, coalesce(sc.deactive,'N') as deactive "
                + " from SubjectMaster sm,  SubjectCoordinator sc, V_Staff vs "
                + " where sc.id.instituteid=:instituteid ");
        if (!subjectid.equalsIgnoreCase("ALL")) {
            sb.append("  and sc.id.subjectid =:subjectid");
        }
        if (!staffid.equalsIgnoreCase("ALL")) {
            sb.append(" and sc.id.staffid =:staffid ");
        }
        sb.append(" and sc.id.staffid=vs.employeeid "
                + " and sm.id.instituteid =sc.id.instituteid and sm.id.subjectid =sc.id.subjectid and vs.employeeid =sc.id.staffid and vs.placeofpostingid in(select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N')) and coalesce(sc.deactive,'N')='N' ");
        try {
            if (!subjectid.equalsIgnoreCase("ALL")) {
                if (!staffid.equalsIgnoreCase("ALL")) {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("instituteid", instituteid).
                            setParameter("subjectid", subjectid).
                            setParameter("staffid", staffid).list();
                } else {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("instituteid", instituteid).
                            setParameter("subjectid", subjectid).list();
                }
            } else {
                if (!staffid.equalsIgnoreCase("ALL")) {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("instituteid", instituteid).
                            setParameter("staffid", staffid).list();
                } else {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("instituteid", instituteid).list();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getCoordinateWithoutSubjectCode(String ins_id, String staffid, String acadyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" Select vs.employeecode, vs.employeename, s.enrollmentno, s.name, s.gender, s.stynumber "
                + " from CoordinatorNameStudentWise sc, V_Staff vs, StudentMaster s "
                + " where sc.id.staffid=vs.employeeid and sc.id.studentid =s.studentid "
                + " and sc.id.instituteid =:ins_id ");
        if (!staffid.equalsIgnoreCase("ALL")) {
            sb.append("  and vs.employeeid=:staffid ");
        }
        if (!acadyear.equalsIgnoreCase("ALL")) {
            sb.append(" and s.acadyear=:acadyear ");
        }
        try {
            if (!staffid.equalsIgnoreCase("ALL")) {
                if (!acadyear.equalsIgnoreCase("ALL")) {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("ins_id", ins_id).
                            setParameter("staffid", staffid).
                            setParameter("acadyear", acadyear).list();
                } else {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("ins_id", ins_id).
                            setParameter("staffid", staffid).list();
                }
            }
             else {
                if (!acadyear.equalsIgnoreCase("ALL")) {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("ins_id", ins_id).
                            setParameter("acadyear", acadyear).list();
                }
                else {
                    list = getHibernateSession().createQuery(sb.toString()).
                            setParameter("ins_id", ins_id).list();
                }
             }
            
           } catch (Exception e) {
             e.printStackTrace(); 
           } finally {
            sb = null;
        }
        return list;
    }

    public List getCoordinator(String instituteid, String depid, String status) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select vs.employeeid, vs.employeecode, vs.employeename, vs.stafftype from V_Staff vs"
                + " where vs.placeofpostingid in(select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid and coalesce(im.deactive,'N')='N')");
        if (status.equals("S")) {
            sb.append("  and vs.departmentid =:depid");
        } else {
            sb.append("  and vs.departmentid <> :depid");
        }
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("depid", depid).list();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public List checkActiveSubject(String subid, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select coalesce(sc.deactive,'N') as deactive from SubjectCoordinator sc where sc.id.instituteid=:instituteid and sc.id.subjectid=:subid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subid", subid).list();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

}
