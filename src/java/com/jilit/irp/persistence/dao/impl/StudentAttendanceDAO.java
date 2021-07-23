/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentAttendanceIDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.StudentAttendance;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class StudentAttendanceDAO extends HibernateDAO implements StudentAttendanceIDAO {

    private static final Log log = LogFactory.getLog(StudentAttendanceDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentAttendance records via Hibernate from the database");
        return this.find("from StudentAttendance as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAttendance.class, id);
    }

    public boolean saveObjlist(List objList) {
        Session session = null;
        Transaction tx = null;
        boolean flag = false;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }
            for (int i = 0; i < objList.size(); i++) {
                session.saveOrUpdate(objList.get(i));
            }
            tx.commit();
            if (tx.wasCommitted()) {
                flag = true;
            }
        } catch (Exception e) {
            tx.rollback();
            flag = false;
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return flag;
    }

    public List getStudentBackPaperSubjectList(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct sm.subjectcode, sm.subjectdesc ,sr.id.stynumber  from StudentMaster s, SubjectMaster sm, StudentResult sr ");
        qry.append(" where s.instituteid=sm.id.instituteid  and s.instituteid=sr.id.instituteid  and sm.id.instituteid=sr.id.instituteid ");
        qry.append(" and s.studentid=sr.id.studentid  and sm.id.subjectid=sr.id.subjectid and s.programid= :programid ");
        qry.append(" and s.instituteid= :instituteid and s.branchid= :branchid and sr.fail='Y' and s.acadyear in (" + academicyr + ") ");
        if (!stynumber.equalsIgnoreCase("All")) {
            qry.append(" and  sr.id.stynumber <= :stynumber");
        }
        qry.append(" order by sr.id.stynumber ");

        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("programid", programid)
                    .setParameter("stynumber", new Byte(stynumber))
                    .setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
            list = Collections.EMPTY_LIST;
        }
        return list;
    }

    public List getStudentBackPaperListWithFailGrade(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    StringBuilder qry = new StringBuilder();
                    qry.append(" select  S.ENROLLMENTNO,SM.SUBJECTCODE, SM.SUBJECTDESC,coalesce(SR.REREGTYPE,'-'),S.ACADEMICYEAR  ");
                    qry.append(" from StudentMaster s, SubjectMaster sm, StudentResult sr  where s.instituteid=sm.instituteid");
                    qry.append(" and s.instituteid=sr.instituteid  and sm.instituteid=sr.instituteid  and s.studentid=sr.studentid");
                    qry.append(" and sm.subjectid=sr.subjectid  and s.programid='" + programid + "'  and s.instituteid='" + instituteid + "'  and s.branchid='" + branchid + "'");
                    qry.append("  and sr.fail='Y'  and s.ACADEMICYEAR in (" + academicyr + ")  ");
                    if (!stynumber.equalsIgnoreCase("All")) {
                        qry.append("  and sr.STYNUMBER <='" + stynumber + "'  ");
                    }
                    qry.append(" order by s.enrollmentno ");

                    return session.createSQLQuery(qry.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getFailGradeStudentSubjectRegistrationDeatil(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {

                    StringBuilder qry1 = new StringBuilder();

                    qry1.append(" SELECT S.ENROLLMENTNO, SMM.SUBJECTCODE, SMM.SUBJECTDESC, Sf.ACADEMICYEAR, sc.STYNUMBER, sc.REREGTYPE ");
                    qry1.append(" FROM STUDENTMASTER s,SUBJECTMASTER smm,STUDENTSUBJECTCHOICEMASTER sc,studentregistration sr,studentregistration_info sf ");
                    qry1.append(" WHERE S.INSTITUTEID = SMM.INSTITUTEID AND SC.INSTITUTEID = sr.instituteid AND SC.INSTITUTEID = sf.instituteid ");
                    qry1.append(" AND sc.registrationid = sr.registrationid AND sc.registrationid = SF.REGISTRATIONID AND SC.STUDENTID = SR.STUDENTID ");
                    qry1.append(" AND sr.registrationid = sf.registrationid AND sr.studentid = sf.studentid AND S.INSTITUTEID = sc.INSTITUTEID ");
                    qry1.append("  AND SMM.INSTITUTEID = sc.INSTITUTEID AND S.STUDENTID = sc.STUDENTID AND SMM.SUBJECTID = Sc.SUBJECTID ");
                    qry1.append("  AND S.PROGRAMID = '" + programid + "' AND S.INSTITUTEID = '" + instituteid + "' AND S.BRANCHID = '" + branchid + "' ");
                    qry1.append("  AND sc.registrationid = '" + registrationid + "' AND");
                    if (!stynumber.equalsIgnoreCase("All")) {
                        qry1.append("  sf.stynumber = '" + stynumber + "' ");
                    }
                    qry1.append("  AND sc.stytypeid NOT IN (SELECT st.stytypeid FROM StyType st WHERE ST.INSTITUTEID = sc.instituteid AND ST.STYTYPE = 'REG') ");
                    qry1.append("  AND sf.ACADEMICYEAR IN (" + academicyr + ") ORDER BY S.ENROLLMENTNO ");
                    return session.createSQLQuery(qry1.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStudentCompulsorySubjectList(final String instituteid, final String programid, final String branchid, final String stynumber, String order, final String academicyr, final String registrationid) {

        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    StringBuilder qry = new StringBuilder();
                    qry.append(" select distinct subm.SUBJECTCODE, subm.SUBJECTDESC, sscm.STYNUMBER from ");
                    qry.append(" STUDENTMASTER sm, SUBJECTMASTER subm, STUDENTSUBJECTCHOICEMASTER sscm,  BASKETMASTER bm where ");
                    qry.append(" sm.INSTITUTEID=subm.INSTITUTEID and sm.INSTITUTEID=sscm.INSTITUTEID and subm.INSTITUTEID=sscm.INSTITUTEID ");
                    qry.append(" and sm.STUDENTID=sscm.STUDENTID and subm.SUBJECTID=sscm.SUBJECTID  and sm.PROGRAMID='" + programid + "' and ");
                    qry.append(" sm.INSTITUTEID='" + instituteid + "'  and sm.BRANCHID='" + branchid + "' and sscm.registrationid='" + registrationid + "' ");
                    if (!stynumber.equalsIgnoreCase("All")) {
                        qry.append("  and sm.STYNUMBER ='" + stynumber + "'");
                    }
                    qry.append(" and (sm.ACADEMICYEAR in (" + academicyr + " ) and sscm.BASKETID=bm.BASKETID ");
                    qry.append(" and sscm.INSTITUTEID=bm.INSTITUTEID and sscm.STYNUMBER=bm.STYNUMBER and bm.SUBJECTTYPEID in ( select st.SUBJECTTYPEID   ");
                    qry.append("  from SUBJECTTYPEMASTER st  where bm.INSTITUTEID=St.INSTITUTEID  and    st.SUBJECTTYPE in ( 'C','S') )");
                    qry.append(" and (  sscm.STYTYPEID in (   select  stytype3_.STYTYPEID from  STYTYPE stytype3_  where    stytype3_.INSTITUTEID=sscm.INSTITUTEID ");
                    qry.append("   and stytype3_.STYTYPE='REG'  ) )  )    order by  subm.SUBJECTCODE ");
                    return session.createSQLQuery(qry.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public List getRegularStudentCompulsorySubjectData(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr, String registrationid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct s.enrollmentno,s.stynumber  "
                + " from StudentMaster s,SubjectMaster sm, StudentSubjectChoiceMaster sc "
                + " where s.instituteid=sm.id.instituteid  "
                + " and s.instituteid=sc.id.instituteid "
                + " and sm.id.instituteid=sc.id.instituteid "
                + " and s.studentid=sc.id.studentid "
                + " and sm.id.subjectid=sc.id.subjectid  "
                + " and s.programid='" + programid + "' "
                + " and s.instituteid='" + instituteid + "' "
                + " and s.branchid='" + branchid + "' ");
        if (!stynumber.equalsIgnoreCase("All")) {
            qry.append(" and  s.stynumber = '" + stynumber + "'");
        }
        qry.append("and sc.stytypeid  in (select st.id.stytypeid from StyType st where st.id.instituteid=sc.id.instituteid and st.stytype='REG' ) "
                + " and s.acadyear in (" + academicyr + ")  "
                + " and exists (select 1 from StudentRegistration where id.instituteid=sc.id.instituteid and id.registrationid=sc.id.registrationid and id.studentid=sc.id.studentid  and PREVENTFREEZED='Y' )"
                + " order by s.stynumber,s.enrollmentno  ");
        try {
            list = getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
            list = Collections.EMPTY_LIST;
        }
        return list;
    }

    public List getRegularStudentElectiveSubjectData(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    StringBuilder qry = new StringBuilder();
                    qry.append(" select distinct e.basketcode,b.subjectcode,b.subjectdesc, d.enrollmentno "
                            + " from studentsubjectchoicemaster a, subjectmaster b, studentmaster d, basketmaster e"
                            + " where a.instituteid='" + instituteid + "'"
                            + " and a.registrationid='" + registrationid + "'"
                            + " and a.stytypeid = ( select c.stytypeid from stytype c where c.instituteid =a.instituteid and c.stytype='REG')");
                    if (!stynumber.equalsIgnoreCase("All")) {
                        qry.append(" and  a.stynumber = '" + stynumber + "'");
                    }
                    qry.append("and a.instituteid=b.instituteid"
                            + " and a.subjectid=b.subjectid"
                            + " and a.subjectrunning='Y'"
                            + " and a.instituteid=d.instituteid"
                            + " and a.studentid=d.studentid"
                            + " and d.programid='" + programid + "'");
                    if (!branchid.equalsIgnoreCase("All")) {
                        qry.append(" and  d.branchid = '" + branchid + "'");
                    }
                    qry.append(" and d.academicyear in (" + academicyr + ")"
                            + " and a.basketid=e.basketid"
                            + " and a.instituteid=e.instituteid"
                            + " and a.stynumber=e.stynumber"
                            + " and e.subjecttypeid in (select   st.subjecttypeid  from  subjecttypemaster st  where e.instituteid=st.instituteid "
                            + " and   st.subjecttype in ('D','I','E') ) "
                            + "  and exists (select instituteid from studentregistration where instituteid=a.instituteid and registrationid=a.registrationid and studentid=a.studentid and preventfreezed='y')");
                    return session.createSQLQuery(qry.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//for fail grade student subject not registered deatil

    public List getFailGradeStudentSubjectNotRegisteredDeatil(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    StringBuilder qry1 = new StringBuilder();
                    qry1.append(" SELECT d.enrollmentno, t.stynumber, e.subjectcode, e.subjectdesc, t.REREGTYPE  FROM (SELECT INSTITUTEID, STUDENTID, STYNUMBER, SUBJECTID ");
                    qry1.append(" FROM STUDENTRESULT a   WHERE A.FAIL = 'Y' AND A.INSTITUTEID = '" + instituteid + "' MINUS SELECT INSTITUTEID,  STUDENTID, ");
                    qry1.append("  STYNUMBER,  SUBJECTID FROM STUDENTSUBJECTCHOICEMASTER b  WHERE B.INSTITUTEID = '" + instituteid + "'   AND B.REGISTRATIONID = '" + registrationid + "') c, ");
                    qry1.append(" STUDENTMASTER d, subjectmaster e, STUDENTRESULT t, studentregistration_info a, ");
                    qry1.append("  studentregistration b   WHERE     a.instituteid = d.instituteid AND A.INSTITUTEID = b.instituteid  AND a.registrationid = b.registrationid ");
                    qry1.append("  AND a.studentid = b.studentid AND a.studentid = d.studentid AND a.registrationid = '" + registrationid + "'  AND coalesce(b.PREVENTFREEZED, 'N') = 'N' ");
                    qry1.append("  AND c.INSTITUTEID = d.INSTITUTEID AND c.studentid = d.STUDENTID  AND C.INSTITUTEID = e.INSTITUTEID AND c.SUBJECTID = e.SUBJECTID ");
                    qry1.append("  AND a.programid = '" + programid + "' AND a.INSTITUTEID = '" + instituteid + "' AND a.BRANCHID = '" + branchid + "' ");
                    qry1.append("  AND a.stynumber = DECODE ('" + stynumber + "', 'All', d.stynumber, '" + stynumber + "') AND a.ACADEMICYEAR IN (" + academicyr + ")  AND C.INSTITUTEID = t.INSTITUTEID ");
                    qry1.append("  AND c.studentid = t.STUDENTID AND c.subjectid = t.subjectid ORDER BY d.ENROLLMENTNO, t.stynumber DESC ");

                    return session.createSQLQuery(qry1.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Collection<?> getStudentAttendance(String instituteid, String studentid, String studentfstid) {
        log.info("Retrieving all StudentAttendance records via Hibernate from the database");
        String str = "";
        str += "from StudentAttendance as tname where tname.id.instituteid = '" + instituteid + "' and tname.id.studentid ='" + studentid + "' and tname.id.studentfstid='" + studentfstid + "'";
        return this.find(str);
    }

    @Override
    public int checkStudentEventSubjectMarks(String instituteid, String registrationid, String studentid, String subjectid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(sesm.id.instituteid) from StudentEventSubjectMarks sesm where sesm.id.instituteid=:instituteid and sesm.id.registrationid=:registrationid and sesm.id.studentid=:studentid and sesm.subjectid=:subjectid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(list.get(0).toString());
    }

    @Override
    public int checkStudentAttendance(String instituteid, String registrationid, String studentid, String subjectid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(sa.id.instituteid) from StudentAttendance sa where sa.id.instituteid=:instituteid and sa.registrationid=:registrationid and sa.id.studentid=:studentid and sa.subjectid=:subjectid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(list.get(0).toString());
    }
}
