/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.persistence.dto.SubjectMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author singh.amarjeet
 */
public interface SubjectMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public Collection<?> findAllWithNVL(String instituteid);

    public Collection<?> findAllForMaster(String instituteid);

    public int checkIfChildExist(final SubjectMasterId subjectMasterId);

    public List<String> doValidate(final SubjectMaster subjectmaster, final String mode);

    public List getReqSubjectForBranchChange(String instituteid);

    public List getSubjectForDST(String instituteid);

    public List getSubjectCode(String instituteid);

    public List getSub_Code_Fst(String instituteid, String registrationid, String depid);

    public List getSub_Wise_SubSec_Report(String instituteid, String registrationid, String departid, String subjectid);

    public List getCoordinate(String instituteid, String registrationid, String staff_type);

    public List getSubjectCode_SubjectWise(String instituteid, String registrationid);

    public List getGridSubjectcomponent(final String subjectid, final String instituteid);

    public List getSubjectCodeForAudit(String instituteid, String registrationid, String programid, String sectionid, String academicyear);

    public List getDataForLTPPassingMarks(String registrationId, String instituteId, String subjectTypeId, String departmentId, Short styNum);

    public List getSubjectCodeUsingFacultySubjectTagging(String instituteid, String registrationid);

    public List getAllSubjectCode_SCR(String instituteid, String registrationid, String Academicyear, String Programid, String secId, String basketcode);

    public List getSubjectChoicesReportDatas(String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, String sem, String deptid, String subjectrunning);

    public List getSubjectChoiceNotFilledByStudentReport(String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, String sem, String deptid);

    public List getSubjectCodeDeactive(String instituteid);

    public List getSubPerProgSty(String instituteid, String programid, String styno);

    public List getMarks(String instituteid, String subjectid);

    public List checkSubjectComponent(String instituteid, String subjectid, String subcomid);

    public List getSubjectsForSupplementary(String instituteid, String registrationid, String parentregistrationid, String studentid);

    public List getBasketStyTypeStyno(String instituteid, String registrationid, String studentid, String subjectid);

    public List getSubjectComponentDetail(String instituteid, String subjectid);

    public List getReqSubject(String instituteid);

    public List getSubjectsForSummer(String instituteid, String registrationid, String studentid);

    public List getBasketidSubjecttypeid(String instituteid, String subjectid, String programid, String sectionid);

    public List getSubjectCodeAcademicyearWise(String instituteid, String registrationid, String academicyear, String departmentid);

    public List getComponentCodeForFST(String instituteid, String registrationid, String academicyear, String subjectid);
}
