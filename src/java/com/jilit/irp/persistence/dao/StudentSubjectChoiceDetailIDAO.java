/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface StudentSubjectChoiceDetailIDAO extends IDAO {

    public List StyNumberList(String instituteId, String academicYear);

    public List branchList(String instituteId, String academicYear);

    public List getSectionList(String instituteId, String acadYear, String styNum, String branchId);

    public List getSubSectionListForStuMas(String instituteId, String acadYear, String styNum, String branchId, String sectionId);

    public List getSubsectionForCombo2(String instid, String stynumber, String academicyear, String branchid, String nextSemValue);

    public List getRegistraionCodeForStuReg(String instituteId);

    public List getAcademicYear(String instituteId, String regId);

    public List getStyNumber(String instituteId, String academicYear, String regId);

    public List getBranchForStuReg(String instituteId, String academicYear, String regId);

    public List getSectionForStuReg(String instituteId, String academicYear, String regId, String stynumber, String branchid);

    public List getSubsectionForCombo(String instid, String styNum, String academicyear, String branchid);

    public List getSubjectCode(String instituteId, String regId);

    public List getDepartmentCode(String instituteId, String regId, String subjectId);

    public List getSubjectCompCode(String instituteId, String subjectId);

    public List getPSTSection(String instituteid, String registrationid, String subjectid, String departmentid);

    public List getAcademicYearForChoice(String instituteid, String registrationid, String subjectid, String subCompId);

    public List getProgramForChoice(String instituteid, String registrationid, String subjectid, String subCompId, String AcademicYear);

    public List getBranchForChoice(String instituteid, String registrationid, String subjectid, String subCompId, String AcademicYear, String programId);

    public List getStyTypeChoice(String instituteid, String registrationid, String subjectid, String AcademicYear, String programId);

    public List getStyNumberChoice(String instituteid, String registrationid, String subjectid, String AcademicYear, String programId);

    public List getSubSectionAllocation(String instituteid, String regId, String departmentId, String subjectId, String subCompId, String acadYear, String programid, String branchId, String styNum, String styType);

    public List getSubsection(String instituteid, String registrationid, String subjectid, String sectionid, String acadyear, String stynumber, String component, String departmentid);

    public List getPSTSectionChoice(String instituteid, String registrationid, String subjectid, String departmentid);

//    public List checkTimeTableFinalised(String instituteid, String registrationid, String departmentid, String subjectid, String subcompid);
    public List getBackPaperAttemptParameters(String instituteid, String parameterid);
//
//    public List getStudentSmsDetail(String instituteid, String studentid);
//
//    public List getParentSmsDetail(String instituteid, String studentid);
//
//    public List getSubSectionLOV(String instituteid, String studentid, int lno);
//
//    public List checkIfgetSubSectionCodeExist(String instituteid, String code, int lno);
//
//    public List getSubSectionCombo(String instituteid, String subjectid, String subjectcomponentid, String registrationid, String acadyear, String programid, int lno);
//

    public List getStudentSubjectChoiceDetailData(String instituteid, String registrationid, String studentid);
//
//    public List getSubSectionLOV1(String instituteid, String arg, int lno, String acadyear, String stynumber, String branchid);
//
//    public List checkIfgetSubSectionCodeExist1(String instituteid, String arg, String code, int lno, String acadyear, String stynumber, String branchid);
//
//    public List getSubSectionLOV2(String instituteid, int lno, String acadyear, String stynumber, String branchid);
//
//    public List checkIfgetSubSectionCodeExist2(String instituteid, String code, int lno, String acadyear, String stynumber, String branchid);

//    public List getSubjectComponentForXL(String instituteid, String registrationid, String stynumber);
//
//     public List getSubSectionLOVdata(String instituteid, String registrationid,String subjectid, String departmentid) ;
//
//     public List getSubsectionForXL(String instituteid, String registrationid, String academicyear, String programid, String branchid, String stynumber, String sectionid);
//
    public void deleteStudentSubjectChoiceDetailData(String instituteid, String registrationid, String studentid);
}
