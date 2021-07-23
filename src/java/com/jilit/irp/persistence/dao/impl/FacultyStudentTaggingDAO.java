/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.FacultyStudentTaggingIDAO;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.StudentEventSubjectMarks;
import com.jilit.irp.persistence.dto.StudentPreviousAttendence;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.bso.biz.BusinessService;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 *
 * @author v.kumar
 */
public class FacultyStudentTaggingDAO extends HibernateDAO implements FacultyStudentTaggingIDAO {

    private static final Log log = LogFactory.getLog(FacultyStudentTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all FacultyStudentTagging records via Hibernate from the database");
        return this.find("from FacultyStudentTagging as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(FacultyStudentTagging.class, id);
    }

    public String insertFacultyStudentTagging1(final List<FacultyStudentTagging> recordsToInsert,
            final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<FacultySubjectTagging> fstList, final List<ProgramSubjectTagging> pstid, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, BusinessService businessService) {
        String retList = "fail";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();

            for (int i = 0; i < pstid.size(); i++) {
                System.err.println("************* InstOrUpd PST value" + i);
                session.saveOrUpdate((ProgramSubjectTagging) pstid.get(i));
                //  businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((ProgramSubjectTagging) pstid.get(i));
            }
            for (int i = 0; i < fstList.size(); i++) {
                System.err.println("************* InstOrUpd FST value" + i);
                session.saveOrUpdate((FacultySubjectTagging) fstList.get(i));
                //  businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((FacultySubjectTagging) fstList.get(i));
            }
            for (int i = 0; i < recordsToInsert.size(); i++) {
                System.err.println("************* InstOrUpd FST value" + i);
                session.saveOrUpdate((FacultyStudentTagging) recordsToInsert.get(i));
                // businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((FacultyStudentTagging) recordsToInsert.get(i));
            }

            for (int i = 0; i < recordsToUpdPR.size(); i++) {
                System.err.println("************* InstOrUpd PRFST value" + i);
                PRFacultyStudentTagging a = (PRFacultyStudentTagging) recordsToUpdPR.get(i);
                session.saveOrUpdate((PRFacultyStudentTagging) recordsToUpdPR.get(i));
            }

            for (int i = 0; i < recordsToInsOrUpdChoiceMaster.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceMaster) recordsToInsOrUpdChoiceMaster.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoicedetail.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceDetail) recordsToInsOrUpdChoicedetail.get(i));
            }
            retList = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            businessService.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            businessService.closeSession();
        }
        return retList;
    }

    public String insertFacultyStudentTagging2(final List<FacultyStudentTagging> recordsToInsertFST, final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<FacultySubjectTagging> fstList, final List<ProgramSubjectTagging> pstid, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, BusinessService businessService) {
        String retList = "fail";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();
//
//            if (recordsToUpdPR.size() >= 0) {
//                String hql = "update PRFacultyStudentTagging pr set pr.sstpopulated = ?, pr.populatedbyuser = ?, pr.populationdate = ?, pr.deactive = ? where pr.id.instituteid = ? and pr.id.studentid = ?";
//                hql += " and pr.id.fstid in (select fst.id.fstid from FacultySubjectTagging fst where fst.id.instituteid=? and fst.registrationid = ?) ";
//                Query query = this.getSession().createQuery(hql);
//                query.setString(0, "");
//                query.setString(1, "");
//                query.setDate(2, null);
//                query.setString(3, "Y");
//                query.setString(4, instituteid);
//                query.setString(5, studId);
//                query.setString(6, instituteid);
//                query.setString(7, regId);
//                int rowCount = query.executeUpdate();
//                System.err.println(rowCount + " Records(s) Updated");
//            }
            for (int i = 0; i < recordsToInsertFST.size(); i++) {
                System.err.println("************* InstOrUpd FST value" + i);
                session.saveOrUpdate((FacultyStudentTagging) recordsToInsertFST.get(i));
                businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((FacultyStudentTagging) recordsToInsertFST.get(i));
            }

            for (int i = 0; i < recordsToUpdPR.size(); i++) {
                System.err.println("************* InstOrUpd PRFST value" + i);
                PRFacultyStudentTagging a = (PRFacultyStudentTagging) recordsToUpdPR.get(i);
                session.saveOrUpdate((PRFacultyStudentTagging) recordsToUpdPR.get(i));
            }
            for (int i = 0; i < fstList.size(); i++) {
                System.err.println("************* InstOrUpd FST value" + i);
                session.saveOrUpdate((FacultySubjectTagging) fstList.get(i));
                businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((FacultySubjectTagging) fstList.get(i));
            }
            for (int i = 0; i < pstid.size(); i++) {
                System.err.println("************* InstOrUpd PST value" + i);
                session.saveOrUpdate((ProgramSubjectTagging) pstid.get(i));
                businessService.insertOrUpdateInIdGen();
//                businessService.insertInIdGenerationControl((ProgramSubjectTagging) pstid.get(i));
            }

            for (int i = 0; i < recordsToInsOrUpdChoiceMaster.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceMaster) recordsToInsOrUpdChoiceMaster.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoicedetail.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceDetail) recordsToInsOrUpdChoicedetail.get(i));
            }
            retList = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    @Override
    public String insertFacultyStudentTagging(final List<FacultyStudentTaggingId> recordsToDelete,
            final List<StudentRegistration> recordsToUpdSR, final List<PRFacultyStudentTagging> recordsToDeletePRFST, final String regId, final String instituteid, final String studId, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, final List<StudentPreviousAttendence> studentPreviousAttendence) {
        String retList = "fail";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();

            for (int i = 0; i < studentPreviousAttendence.size(); i++) {
                session.saveOrUpdate((StudentPreviousAttendence) studentPreviousAttendence.get(i));
            }
            for (int i = 0; i < recordsToDelete.size(); i++) {
                FacultyStudentTaggingId taggingId = (FacultyStudentTaggingId) recordsToDelete.get(i);
                Object obj = findByPrimaryKey(taggingId);
                if (obj != null) {
                    session.delete((FacultyStudentTagging) obj);
                }
            }
            for (int i = 0; i < recordsToDeletePRFST.size(); i++) {
                PRFacultyStudentTagging taggingdao = (PRFacultyStudentTagging) recordsToDeletePRFST.get(i);
                if (taggingdao != null) {
                    session.delete(taggingdao);
                }
            }
            for (int i = 0; i < recordsToUpdSR.size(); i++) {
                StudentRegistration a = (StudentRegistration) recordsToUpdSR.get(i);
                session.saveOrUpdate((StudentRegistration) recordsToUpdSR.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoiceMaster.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceMaster) recordsToInsOrUpdChoiceMaster.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoicedetail.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceDetail) recordsToInsOrUpdChoicedetail.get(i));
            }

            retList = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    @Override
    public String insertFacultyStudentTagging3(final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail) {
        String retList = "fail";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();

//            if (recordsToUpdPR.size() >= 0) {
//                String hql = "update PRFacultyStudentTagging pr set pr.sstpopulated = ?, pr.populatedbyuser = ?, pr.populationdate = ?, pr.deactive = ? where pr.id.instituteid = ? and pr.id.studentid = ?";
//                hql += " and pr.id.fstid in (select fst.id.fstid from FacultySubjectTagging fst where fst.id.instituteid=? and fst.registrationid = ?) ";
//                Query query = this.getSession().createQuery(hql);
//                query.setString(0, "");
//                query.setString(1, "");
//                query.setDate(2, null);
//                query.setString(3, "Y");
//                query.setString(4, instituteid);
//                query.setString(5, studId);
//                query.setString(6, instituteid);
//                query.setString(7, regId);
//                int rowCount = query.executeUpdate();
//                System.err.println(rowCount + " Records(s) Updated");
//            }
            for (int i = 0; i < recordsToUpdPR.size(); i++) {
                System.err.println("************* InstOrUpd PR value" + i);
                PRFacultyStudentTagging a = (PRFacultyStudentTagging) recordsToUpdPR.get(i);
                session.saveOrUpdate((PRFacultyStudentTagging) recordsToUpdPR.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoiceMaster.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceMaster) recordsToInsOrUpdChoiceMaster.get(i));
            }
            for (int i = 0; i < recordsToInsOrUpdChoicedetail.size(); i++) {
                session.saveOrUpdate((StudentSubjectChoiceDetail) recordsToInsOrUpdChoicedetail.get(i));
            }
            retList = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public Collection<?> getStudentMarksAwarded(final String studentid, final String subjectid, final String instituteid,String registrationid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentEventSubjectMarks.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.studentid", studentid));
                criteria.add(Restrictions.eq("master.subjectid", subjectid));
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.marksawarded1").as("marksawarded1")).add(Projections.property("master.marksawarded2").as("marksawarded2")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            }
        });
        return list;
    }

    public int checkIfChildExist(final FacultyStudentTaggingId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                FacultyStudentTagging dto = (FacultyStudentTagging) session.get(FacultyStudentTagging.class, id);
                //int i1 = ((Integer) session.createFilter(dto.getStudentresultdetail_pubs(), "select count(*)").list().get(0)).intValue();
                // int i2 = ((Integer) session.createFilter(dto.getStudentwisegrade_pubs(), "select count(*)").list().get(0)).intValue();
                // int i3 = ((Integer) session.createFilter(dto.getStudentexamattendances(), "select count(*)").list().get(0)).intValue();
                //int i4 = Integer.parseInt((session.createFilter(dto.getStudentpreviousattendences(), "select count(*)").list().get(0)).toString());                
                //   int i5 = ((Integer) session.createFilter(dto.getStudentattendances(), "select count(*)").list().get(0)).intValue();
                int i6 = Integer.parseInt((session.createFilter(dto.getStudenteventsubjectmarkses(), "select count(*)").list().get(0)).toString());
                //int i7 = ((Integer) session.createFilter(dto.getStudentwisegrades(), "select count(*)").list().get(0)).intValue();
                // int i8 = ((Integer) session.createFilter(dto.getStudentresultdetails(), "select count(*)").list().get(0)).intValue();
                return i6;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public void commomDeleteForApplicationMaster(final String query) {
        Session session = null;
        Transaction tx = null;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }

            org.hibernate.Query quy = session.createQuery(query);
            quy.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.clear();
                session.close();
                tx = null;
            }
        }

    }

    public List getFSTID(final String instituteId, final String fstids, final String studentid) {
        List list = null;

        String qryString = "select fst.fstid from FacultyStudentTagging fst "
                + "where fst.id.instituteid='" + instituteId + "' "
                + "and  fst.fstid in " + fstids + " "
                + "and  fst.id.studentid='" + studentid + "' ";

        try {
            list = getHibernateTemplate().find(qryString);
            //System.out.println("Size is ::getCounsellingCategoryComboData():: " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getStudentFstId(String instituteid, String studentid, String fstid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sft.id.studentfstid from FacultyStudentTagging sft where sft.id.instituteid=:instituteid and sft.id.studentid=:studentid and sft.fstid=:fstid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("studentid", studentid).
                    setParameter("fstid", fstid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list.get(0).toString();
    }

    @Override
    public String updateSM(String d) {
        String retList = "fail";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();
            String hql = "update StudentMaster set dateofbirth = ? where studentid ='10STU20090000001' ";
            Query query = this.getSession().createQuery(hql);
            query.setString(0, d);
            int rowCount = query.executeUpdate();
            System.err.println(rowCount + " Records(s) Updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

}
