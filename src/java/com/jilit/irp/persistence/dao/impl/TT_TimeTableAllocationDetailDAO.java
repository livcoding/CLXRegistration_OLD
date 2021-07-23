/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TT_TimeTableAllocationDetailIDAO;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author mohit1.kumar
 */
public class TT_TimeTableAllocationDetailDAO extends HibernateDAO implements TT_TimeTableAllocationDetailIDAO {

    private static final Log log = LogFactory.getLog(TT_TimeTableAllocationDetailDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all TT_TimeTableAllocationDetail records via Hibernate from the database");
        return this.find("from TT_TimeTableAllocationDetail as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_TimeTableAllocationDetail.class, id);
    }

    @Override
    public void deleteTtDetailOnTTransREg(String tttransid, String regId, String instituteId) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from TT_TimeTableAllocationDetail ttm where ttm.id.tttransid=:tttransid and ttm.id.registrationid=:regId and ttm.id.instituteid=:instId ");
        try {
            Query query = getHibernateSession().createQuery(sb.toString()).
                    setParameter("tttransid", tttransid).
                    setParameter("regId", regId).
                    setParameter("instId", instituteId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteTtAllocationOnTTransREg(String tttransid, String regId) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from TT_TimeTableAllocation ttm where ttm.id.tttransid=:tttransid and ttm.id.registrationid=:regId ");
        try {
            Query query = getHibernateSession().createQuery(sb.toString()).
                    setParameter("tttransid", tttransid).
                    setParameter("regId", regId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List getTimeTableDetailData(String instituteId, String registrationId, String acadmicYear, String programId, String sectionId, String subSectionId, int styNumber, String styTypeId, String subjectId, String ttrefrenceId, String subjectcomponentId) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" select ttd "
                    + " from TT_TimeTableAllocationDetail ttd where"
                    + " ttd.id.instituteid=:instituteId"
                    + " and ttd.id.registrationid=:registrationId and "
                    + " ttd.id.academicyear=:acadmicYear "
                    + " and ttd.id.programid=:programId "
                    + " and ttd.id.sectionid=:sectionId "
                    + " and ttd.id.subsectionid=:subSectionId"
                    + " and ttd.id.stynumber=:styNumber"
                    + " and ttd.id.stytypeid=:styTypeId"
                    + " and exists(select 1 from TT_TimeTableAllocation"
                    + " tt where ttd.id.instituteid =tt.instituteid "
                    + " and ttd.id.registrationid=tt.id.registrationid "
                    + " and ttd.id.tttransid=tt.id.tttransid"
                    + " and tt.subjectid=:subjectId  "
                    + " and tt.ttreferenceid=:ttrefrenceId "
                    + " and tt.subjectcomponentid=:subjectcomponentId"
                    + " )");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("registrationId", registrationId).
                    setParameter("instituteId", instituteId).
                    setParameter("ttrefrenceId", ttrefrenceId).
                    setParameter("subjectcomponentId", subjectcomponentId).
                    setParameter("subjectId", subjectId).
                    setParameter("acadmicYear", acadmicYear).
                    setParameter("programId", programId).
                    setParameter("sectionId", sectionId).
                    setParameter("subSectionId", subSectionId).
                    setParameter("styNumber", styNumber).
                    setParameter("styTypeId", styTypeId).
                    list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getFacultySubjectTaggingData(String instituteId, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, byte styNumber, String subjectId, String ttRefrenceId, String subjectcomponentId) {
        List list = null;
//        FacultySubjectTagging fst=new FacultySubjectTagging();
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" select fst from FacultySubjectTagging  fst "
                    + " where fst.id.instituteid=:instituteId "
                    + " and fst.registrationid=:registrationId  "
                    + " and fst.academicyear=:academicYear"
                    + " and fst.programid=:programId "
                    + " and fst.sectionid=:sectionId "
                    + " and fst.subsectionid=:subsectionId "
                    + " and fst.stynumber=:styNumber "
                    + " and fst.stytypeid=:styTypeId"
                    + " and fst.subjectid=:subjectId "
                    + " and fst.ttreferenceid=:ttRefrenceId "
                    + " and fst.subjectcomponentid=:subjectcomponentId ");

            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("registrationId", registrationId).
                    setParameter("instituteId", instituteId).
                    setParameter("academicYear", academicYear).
                    setParameter("programId", programId).
                    setParameter("sectionId", sectionId).
                    setParameter("subsectionId", subsectionId).
                    setParameter("styNumber", styNumber).
                    setParameter("styTypeId", styTypeId).
                    setParameter("subjectId", subjectId).
                    setParameter("ttRefrenceId", ttRefrenceId).
                    setParameter("subjectcomponentId", subjectcomponentId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getTimeTableDetailDataForMerging(String instituteId, String registrationId, String acadmicYear, String programId, String sectionId, String subSectionId, int styNumber, String styTypeId, String subjectId, String subjectcomponentId) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {

            str.append(" select ttd "
                    + " from TT_TimeTableAllocationDetail ttd where"
                    + " ttd.id.instituteid=:instituteId"
                    + " and ttd.id.registrationid=:registrationId and "
                    + " ttd.id.academicyear=:acadmicYear "
                    + " and ttd.id.programid=:programId "
                    + " and ttd.id.sectionid=:sectionId "
                    + " and ttd.id.subsectionid=:subSectionId"
                    + " and ttd.id.stynumber=:styNumber"
                    + " and ttd.id.stytypeid=:styTypeId"
                    + " and exists(select 1 from TT_TimeTableAllocation"
                    + " tt where ttd.id.instituteid =tt.instituteid "
                    + " and ttd.id.registrationid=tt.id.registrationid "
                    + " and ttd.id.tttransid=tt.id.tttransid"
                    + " and tt.subjectid=:subjectId  "
                    + " and tt.subjectcomponentid=:subjectcomponentId"
                    + " )");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("registrationId", registrationId).
                    setParameter("instituteId", instituteId).
                    setParameter("subjectcomponentId", subjectcomponentId).
                    setParameter("subjectId", subjectId).
                    setParameter("acadmicYear", acadmicYear).
                    setParameter("programId", programId).
                    setParameter("sectionId", sectionId).
                    setParameter("subSectionId", subSectionId).
                    setParameter("styNumber", styNumber).
                    setParameter("styTypeId", styTypeId).
                    list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getFacultySubjectTaggingDataForMerging(String instituteId, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, byte styNumber, String subjectId, String basketid, String subjectcomponentId) {
        List list = null;
//        FacultySubjectTagging fst=new FacultySubjectTagging();
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" select fst from FacultySubjectTagging  fst "
                    + " where fst.id.instituteid=:instituteId "
                    + " and fst.registrationid=:registrationId  "
                    + " and fst.academicyear=:academicYear"
                    + " and fst.programid=:programId "
                    + " and fst.sectionid=:sectionId "
                    + " and fst.subsectionid=:subsectionId "
                    + " and fst.stynumber=:styNumber "
                    + " and fst.stytypeid=:styTypeId"
                    + " and fst.subjectid=:subjectId "
                    + " and fst.basketid=:basketid "
                    + " and fst.subjectcomponentid=:subjectcomponentId ");

            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("registrationId", registrationId).
                    setParameter("instituteId", instituteId).
                    setParameter("academicYear", academicYear).
                    setParameter("programId", programId).
                    setParameter("sectionId", sectionId).
                    setParameter("subsectionId", subsectionId).
                    setParameter("styNumber", styNumber).
                    setParameter("styTypeId", styTypeId).
                    setParameter("subjectId", subjectId).
                    setParameter("basketid", basketid).
                    setParameter("subjectcomponentId", subjectcomponentId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkTT_TimeTableAllocationData(String instituteid, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, int styNumber, String subjectId) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" select tta from TT_TimeTableAllocation tta where tta.id.instituteid=:instituteid "
                    + " and tta.id.registrationid=:registrationId and tta.subjectid=:subjectId "
                    + " and exists ( select ttd.id.tttransid from TT_TimeTableAllocationDetail ttd where ttd.id.instituteid=tta.id.instituteid "
                    + " and tta.id.tttransid=ttd.id.tttransid  and ttd.id.registrationid=tta.id.registrationid "
                    + " and ttd.id.academicyear =:academicYear and ttd.id.programid=:programId and ttd.id.sectionid=:sectionId "
                    + " and ttd.id.stynumber=:styNumber and ttd.id.stytypeid=:styTypeId and ttd.id.subsectionid=:subsectionId ) ");

            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", registrationId).
                    setParameter("academicYear", academicYear).
                    setParameter("programId", programId).
                    setParameter("sectionId", sectionId).
                    setParameter("subsectionId", subsectionId).
                    setParameter("styNumber", styNumber).
                    setParameter("styTypeId", styTypeId).
                    setParameter("subjectId", subjectId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkMultifacultyData(String instituteid, String registrationId, String tttransid) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" select mf from TT_MultiFacultyTeachingLoad mf where mf.id.instituteid=:instituteid and mf.id.registrationid=:registrationId and mf.id.tttransid=:tttransid ");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", registrationId).
                    setParameter("tttransid", tttransid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkDataInTTAllocation(String instituteid, String registrationId, String subjectid) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append("select tta.staffid,tta.stafftype,tta.subjectcomponentid,tta.runningdepartmentid,tta.id.tttransid,tta.ttreferenceid,tta.id.instituteid,sc.subjectcomponentcode"
                    + " from TT_TimeTableAllocation tta,SubjectComponent sc "
                    + " where tta.id.instituteid=:instituteid and tta.id.registrationid=:registrationId and tta.subjectid=:subjectid"
                    + " and sc.id.instituteid=tta.id.instituteid and sc.id.subjectcomponentid=tta.subjectcomponentid ");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", registrationId).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkDataInOfferSubTaggingDetail(String instituteid, String registrationId, String subjectid) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append("select ostd.staffid,ostd.stafftype,ostd.id.subjectcomponentid,ost.departmentid,'empty' as tttransid,'empty' as tttreferenceid,ost.id.instituteid,sc.subjectcomponentcode "
                    + " from OfferedODSubjectTagging ost,OfferedODSubjectTaggingDetail ostd,SubjectComponent sc"
                    + " where ost.id.instituteid=:instituteid and ost.id.registrationid=:registrationId "
                    + " and coalesce(ost.odsubjectid,ost.currentsubjectid)=:subjectid"
                    + " and ost.id.instituteid=ostd.id.instituteid"
                    + " and ost.id.registrationid=ostd.id.registrationid"
                    + " and ost.id.offersubjectid=ostd.id.offersubjectid"
                    + " and sc.id.instituteid=ost.id.instituteid and sc.id.subjectcomponentid=ostd.id.subjectcomponentid "
                    + " and coalesce(ost.deactive,'N')='N'");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", registrationId).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getMergedWithCode(String instituteid, String registrationId, String tttransid) {
        List list = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append("select ttad.mergewithsubsectioncode,im.institutecode,pm.programcode,ttad.id.academicyear,sm.sectioncode,pws.subsectioncode"
                    + " from TT_TimeTableAllocationDetail ttad,ProgramMaster pm,SectionMaster sm,ProgramWiseSubsection pws,InstituteMaster im"
                    + " where ttad.id.instituteid = :instituteid and ttad.id.registrationid = :registrationId"
                    + " and ttad.id.tttransid = :tttransid and pm.id.instituteid=ttad.id.instituteid"
                    + " and pm.id.programid=ttad.id.programid and sm.id.instituteid=ttad.id.instituteid"
                    + " and sm.id.sectionid=ttad.id.sectionid and pws.id.instituteid=ttad.id.instituteid"
                    + " and pws.id.programid=ttad.id.programid and pws.id.sectionid=ttad.id.sectionid"
                    + " and pws.id.academicyear=ttad.id.academicyear and pws.id.stynumber=ttad.id.stynumber"
                    + " and pws.id.subsectionid=ttad.id.subsectionid and im.instituteid=ttad.id.instituteid");
            list = getHibernateSession().createQuery(str.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationId", registrationId).
                    setParameter("tttransid", tttransid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateMergeWithSecSubsection(String instituteid, String tttransid, String mergewithsubsectioncode) {
        try {
            String hql = "update  TT_TimeTableAllocationDetail ttad "
                    + "set ttad.mergewithsubsectioncode = ? "
                    + "where ttad.id.tttransid = ? and ttad.id.instituteid <> ?";
            Query query = this.getSession().createQuery(hql);
            query.setString(0, mergewithsubsectioncode);
            query.setString(1, tttransid);
            query.setString(2, instituteid);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
