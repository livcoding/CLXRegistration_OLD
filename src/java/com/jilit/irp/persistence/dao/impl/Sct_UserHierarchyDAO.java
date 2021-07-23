/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Sct_UserHierarchyIDAO;
import com.jilit.irp.persistence.dto.Sct_UserHierarchy;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author soa university
 */
public class Sct_UserHierarchyDAO extends HibernateDAO implements Sct_UserHierarchyIDAO {

    private static final Log log = LogFactory.getLog(Sct_UserHierarchyDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_UserHierarchy records via Hibernate from the database");
        return this.find("from Sct_UserHierarchy as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_UserHierarchy.class, id);
    }

    public Integer getMaxSlNo(final String userid, final String uhtype) {

        long slno = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_UserHierarchy.class);
                criteria.add(Restrictions.eq("id.userid", userid));
                criteria.add(Restrictions.eq("id.uhtype", uhtype));
                criteria.setProjection(Projections.count("id.userid"));
                return criteria.list().get(0);
            }
        });
        return (int) slno;
    }

    public List studentPersonalAcademicInfo(final String instituteid, final String studentid, final String regid, final String programid, final String branchid, final String academicyear, final String subsectionid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String qryString = " select SM.NAME, SM.FATHERSNAME,SM.ENROLLMENTNO, SM.ACADEMICYEAR, "
                            + " 'Y' as phoneNo, "
                            + " (select IM.INSTITUTENAME from INSTITUTEMASTER im where im.INSTITUTEID='" + instituteid + "') as school , "
                            + " (select PM.programcode from programmaster pm where PM.PROGRAMID='" + programid + "' and PM.INSTITUTEID='" + instituteid + "') as programdesc, "
                            + " (select BM.subsectioncode from ProgramWiseSubsection bm where  BM.PROGRAMID=sm.PROGRAMID and BM.INSTITUTEID=sm.INSTITUTEID "
                            + "  and bm.academicyear=sm.academicyear and bm.stynumber=sm.stynumber and bm.subsectionid='" + subsectionid + "')as subsection, "
                            + " (select PM.PROGRAMCODE from programmaster pm where PM.PROGRAMID='" + programid + "' and PM.INSTITUTEID='" + instituteid + "') as PROGRAMCODE, "
                            + " (SELECT MAX(SUBJECTCHOICEDATE)  "
                            + " FROM StudentSubjectChoiceMaster SSC WHERE SSC.INSTITUTEID=SM.INSTITUTEID AND SSC.REGISTRATIONID=SR.REGISTRATIONID AND SSC.STUDENTID=SM.STUDENTID) as REGDATE,SR.REGCONFIRMATIODATE,sm.dateofbirth,sm.mothersname,sm.parentoccupation,sm.parentannualincome, "
                            + " sm.parenteducationalbackground,sm.parentorgmobile,sa.caddress1,sa.paddress1,rm.registrationcode, (select bm.branchdesc from branchmaster bm  where  bm.branchid='" + branchid + "' and bm.instituteid='" + instituteid + "'),"
                            + "(select  sp.semailid  from  StudentPhone sp where sp.studentid='" + studentid + "'),(select  sp.pemailid   from   StudentPhone sp   where  sp.studentid='" + studentid + "'),(select  SP.SCELLNO  from   StudentPhone sp   where   sp.studentid='" + studentid + "'),(select  SP.PCELLNO  from   StudentPhone sp   where   sp.studentid='" + studentid + "')  from studentmaster sm, STUDENTREGISTRATION sr,RegistrationMaster rm,studentadddress sa where "
                            + " sa.studentid=sm.studentid and SM.STUDENTID='" + studentid + "' and sr.REGISTRATIONID='" + regid + "' and SM.INSTITUTEID='" + instituteid + "' and SM.INSTITUTEID=SR.INSTITUTEID "
                            + " and SM.STUDENTID=SR.STUDENTID and rm.registrationid=sr.REGISTRATIONID and rm.instituteid=sr.instituteid ";
                    return session.createSQLQuery(qryString).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStudentPhoneInfo(String studentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sp.scellno,sp.pcellno,sp.semailid,sp.pemailid from StudentPhone sp where "
                + " sp.studentid = :studentid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List studentSubjectInfo(final String instituteid, final String studentid, final String regid) {
        List list = null;
        try {

            String qryString = " select distinct sm.subjectdesc,sm.subjectcode, "
                    + " (select coalesce(pst.credits,0) "
                    + " from ProgramSubjectTagging pst "
                    + " where pst.id.instituteid=fst.id.instituteid and fst.subjectid=pst.subjectid and fst.registrationid=pst.id.registrationid "
                    + " and pst.basketid=fst.basketid and pst.academicyear=fst.academicyear and pst.programid=fst.programid and pst.sectionid=fst.sectionid and pst.stynumber=fst.stynumber)as credits,coalesce(sm.subjectflag, ' ') as subjectflag ,st.subjecttype,coalesce(sft.auditsubject,'N') as auditsubject,ba.basketcode  "
                    + " from FacultySubjectTagging fst, FacultyStudentTagging sft, "
                    + " SubjectMaster sm,BasketMaster ba,SubjectTypeMaster st where fst.id.instituteid=sft.id.instituteid   and fst.id.fstid=sft.fstid  "
                    + " and fst.id.instituteid=sm.id.instituteid and fst.subjectid=sm.id.subjectid "
                    + " and ba.id.instituteid= st.id.instituteid  and ba.subjecttypeid = st.id.subjecttypeid  and fst.basketid = ba.id.basketid  and ba.id.instituteid = fst.id.instituteid  and fst.id.instituteid=:instituteid "
                    + " and fst.registrationid=:registration and sft.id.studentid=:studentid "
                    + " and exists (select pst1.id.instituteid from ProgramSubjectTagging pst1 where "
                    + " pst1.subjectid=fst.subjectid and pst1.id.instituteid=fst.id.instituteid and pst1.subjectrunning='Y'"
                    + " and pst1.id.registrationid=fst.registrationid and pst1.academicyear=fst.academicyear and pst1.programid=fst.programid and pst1.sectionid=fst.sectionid and pst1.stynumber=fst.stynumber and pst1.basketid=fst.basketid) order by  ba.basketcode,sm.subjectcode asc ";
            list = getHibernateSession().createQuery(qryString).
                    setParameter("instituteid", instituteid).
                    setParameter("registration", regid).
                    setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List studentSubjectInfoChoice(final String instituteid, final String studentid, final String regid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String qryString = " select distinct sm.subjectdesc,sm.subjectcode, "
                            + " (select coalesce(pst.credits,0)"
                            + " from programsubjecttagging pst, programsubjectdetail psd, subjectcomponent sc  where psd.instituteid=sc.instituteid "
                            + " and psd.subjectcomponentid=sc.subjectcomponentid  and pst.instituteid=psd.instituteid "
                            + " and pst.programsubjectid=psd.programsubjectid and pst.instituteid=fst.instituteid "
                            + " and fst.subjectid=pst.subjectid and fst.registrationid=pst.registrationid and pst.basketid=fst.basketid "
                            + " group by pst.credits) credits from studentsubjectchoicemaster fst , subjectmaster sm "
                            + " where fst.instituteid=sm.instituteid and fst.subjectid=sm.subjectid    and fst.instituteid='" + instituteid + "' "
                            + " and fst.registrationid='" + regid + "' and fst.studentid='" + studentid + "' order by sm.subjectcode  ";
                    return session.createSQLQuery(qryString).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
