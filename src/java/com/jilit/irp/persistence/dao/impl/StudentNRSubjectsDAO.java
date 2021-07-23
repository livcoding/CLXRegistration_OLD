/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentNRSubjectsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentNRSubjects;
import com.jilit.irp.persistence.dto.StudentNRSubjectsId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Transaction;

/**
 *
 * @author akshya.gaur
 */
public class StudentNRSubjectsDAO extends HibernateDAO implements StudentNRSubjectsIDAO {

    private static final Log log = LogFactory.getLog(StudentNRSubjectsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentNRSubjects records via Hibernate from the database");
        return this.find("from StudentNRSubjects as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentNRSubjects.class, id);
    }

    @Override
    public List getStudentInfo(String instituteid, String enrollmentno) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.studentid,sm.name,sm.acadyear,sm.stynumber,bm.branchdesc"
                + " from StudentMaster sm,BranchMaster bm"
                + " where sm.instituteid=:instituteid"
                + " and sm.enrollmentno=:enrollmentno "
                + " and bm.id.instituteid=sm.instituteid"
                + " and bm.id.branchid=sm.branchid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("enrollmentno", enrollmentno).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStudentNRSubjects(String instituteid, String studentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select snrs.id.instituteid,snrs.id.studentid,sm.id.subjectid,sm.subjectcode,sm.subjectdesc,"
                + " snrs.registered,"
                + " (select rm.registrationcode from RegistrationMaster rm where rm.id.instituteid=snrs.id.instituteid and rm.id.registrationid=snrs.nrregisteredid) as leftsemester,"
                + " (select rm.registrationcode from RegistrationMaster rm where rm.id.instituteid=snrs.id.instituteid and rm.id.registrationid=snrs.registerid)as regsemester,"
                + " snrs.id.academicyear,pm.id.programid,pm.programcode,snrs.id.stynumber,secm.id.sectionid,secm.sectioncode,bm.basketdesc,snrs.remarks"
                + " from StudentNRSubjects snrs,SubjectMaster sm,BasketMaster bm,ProgramMaster pm,SectionMaster secm"
                + " where snrs.id.studentid=:studentid"
                + " and snrs.id.instituteid=:instituteid"
                + " and sm.id.instituteid=snrs.id.instituteid"
                + " and sm.id.subjectid=snrs.id.subjectid"
                + " and bm.id.instituteid=snrs.id.instituteid"
                + " and bm.id.basketid=snrs.basketid"
                + " and pm.id.instituteid=snrs.id.instituteid"
                + " and pm.id.programid=snrs.id.programid"
                + " and secm.id.instituteid=snrs.id.instituteid"
                + " and secm.id.sectionid=snrs.id.sectionid order by sm.subjectcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getPreviousRegCode(String instituteid, String studentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid,rm.registrationcode from RegistrationMaster rm "
                + " where rm.id.instituteid=:instituteid"
                + " and exists(select sr.id.studentid from StudentRegistration sr"
                + " where sr.id.instituteid=rm.id.instituteid"
                + " and sr.id.registrationid=rm.id.registrationid"
                + " and sr.id.studentid=:studentid) order by rm.registrationcode");
        try { 
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getPreviousSubject(String instituteid, String studentid,String subjecttype, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select  sm.id.subjectid,sm.subjectcode,sm.subjectdesc,sm.credits,bm.basketdesc,bm.id.basketid,pm.id.programid,sec.id.sectionid,ay.id.academicyear,st.id.stynumber"
                + " from SubjectMaster sm,BasketMaster bm,ProgramMaster pm,SectionMaster sec,Academicyear ay,StyDesc st"
                + " where sm.id.instituteid=:instituteid and bm.subjecttypeid=:subjecttype "
                + " and coalesce(sm.deactive,'N')='N'"
                + " and exists( "
                + " select pst.basketid"
                + " from ProgramSubjectTagging pst,"
                + " StudentRegistration_info stinfo"
                + " where pst.id.instituteid= sm.id.instituteid "
                + " and stinfo.id.instituteid=sm.id.instituteid"
                + " and pst.id.instituteid=bm.id.instituteid"
                + " and pst.basketid=bm.id.basketid"
                + " and stinfo.academicyear=pst.academicyear"
                + " and stinfo.programid=pst.programid"
                + " and stinfo.stynumber=pst.stynumber"
                + " and stinfo.sectionid=pst.sectionid  "
                + " and pm.id.instituteid=pst.id.instituteid"
                + " and pm.id.programid=pst.programid"
                + " and ay.id.instituteid=pst.id.instituteid"
                + " and ay.id.academicyear=pst.academicyear"
                + " and sec.id.instituteid=pst.id.instituteid"
                + " and sec.id.sectionid=pst.sectionid"
                + " and st.id.instituteid=pst.id.instituteid"
                + " and st.id.stynumber=pst.stynumber"
                + " and stinfo.id.registrationid=pst.id.registrationid"
                + " and stinfo.id.studentid=:studentid"
                + " and pst.subjectid=sm.id.subjectid"
                + " and pst.id.registrationid=:registrationid)");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("studentid", studentid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjecttype", subjecttype).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
     public List doValidate(final StudentNRSubjects dto, final String mode) {

        List <String>errors= new ArrayList<String>();
        Integer count=new Integer(0);

            count= (Integer) getHibernateTemplate().executeFind(new HibernateCallback()
            {
                @Override
                public Object doInHibernate(Session session)
                {
                    Criteria criteria=session.createCriteria(StudentNRSubjects.class);
                     criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
//                     criteria.add(Restrictions.eq("id.registrationid", dto.getId().getRegistrationid()));
                     criteria.add(Restrictions.eq("id.studentid", dto.getId().getStudentid()));
                     criteria.add(Restrictions.eq("id.subjectid", dto.getId().getSubjectid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if(count.intValue()>0)
             {
            errors.add("Subject already exist for this student...!");
             }
    return errors;
    }
//
//
//    public List addStudentSubjectNotOfferData(ASObject asobject) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public List updateStudentSubjectNotOfferData(ASObject asoj) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//     public String getCommitInsert(List<StudentNRSubjects> studentNRSubjects,List<StudentSubjectChoiceDetail> studentSubjectChoiceDetail,List<StudentSubjectChoiceMaster> studentSubjectChoiceMaster) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();
//            tx = session.beginTransaction();
//
//            for (int m = 0; m < studentNRSubjects.size(); m++) {
//                System.err.println("************* value" + m);
//                session.saveOrUpdate(studentNRSubjects.get(m));
//            }
//
//             for (int L = 0; L < studentSubjectChoiceDetail.size(); L++) {
//                System.err.println("************* value" + L);
//                session.delete(studentSubjectChoiceDetail.get(L));
//            }
//            for (int k = 0; k < studentSubjectChoiceMaster.size(); k++) {
//                System.err.println("************* value" + k);
//                session.delete(studentSubjectChoiceMaster.get(k));
//            }
//
//            retList = "Data Save Successfully";
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            retList = "Error in tx update";
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return retList;
//    }

}
