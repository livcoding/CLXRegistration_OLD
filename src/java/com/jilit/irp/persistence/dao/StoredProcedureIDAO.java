/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author sunny.singhal
 */
public interface StoredProcedureIDAO extends IDAO {

    // public String generateId(final String pCharacters);
//    public String generateId(final String pInstID, final String pCharacters);
//
//    public String showLink(final String pRightsID, final String pMemberID, final String pMemberType, final String pIPAddress);
//
//    public String noRegistrationPermited(final String pInstID, final String pStudID);
//
//    public String getStudentERPData(final String cid, final BigDecimal rank, final String studentprid, final String categoryid);
//
//    public String GetSubjWiseBatches(final String pInstID, final String pRegID, final String pStaffID, final String pProgramID, final String pSectionID, final String pSubjID, final String pSubCompID);
//
//    public String getTTAllocationAppCanlData(final String instituteid, final String registrationid, final String departmentid, final String status);
//
    public String saveTTAllocationData(final String instituteid, final String registrationid, final String mDay, final String mSTAFFTYPE, final String mSTAFFID, final String mFromTime, final String mToTime, final String mSLOTID);
//
//    public String studentAttendApproveForExam(final String instituteid, final String registrationid, final String pExamEventID);
//
//    public String approveElectiveSubjecs(final String instituteid, final String registrationid, final String pDocMode, final String pSubjectTypeID);
//

    public String RegistrationPermited(final String pInstID, final String pRegID, final String pStudID, final int stynumber, final String flag);
//
//    public String studentSubjectAttendProcess(final String pSessionID, final String pInstID, final String pRegistrationID, final String pProgramID, final String pBranchID, final String pSubjectID, final String pSectionID, final String pSubsectionID, final String pStyTypeID, final String pStudentID, final String pStyNumber);
//
//    public String saveApplicationData(final String pInstID, final String pCharacters, final String pCID, final String pPROGRAMTYPEID, final String pPROGRAMID, final String pAPPLICANTFIRSTNAME);
//
//    public String studentSubjectAttendProcessForFaculty(final String pSessionID, final String pInstID, final String pRegistrationID, final String pProgramID, final String pBranchID, final String pSubjectID, final String pSectionID, final String pSubsectionID, final String pStyTypeID, final String pStudentID, final String pEmployeeIdID, final String pStyNumber);
//
//    public String populateStaffAttendanceStatus(final String sessionid, final String instid, final String regid, final Date fromdate, final Date todate, final String staffid);
//
//    public String UpdateRecheckGrade(final String pInstituteID, final String pRegistrationID, final String pExamEventID, final String pSubjectID, final String pStudentID, final double newMarks);
//
//    public String saveRegistrationData(final String pInstID, final String pCharacters, final String pREGEMAILID, final String pPWDATA, final String pLOGINIP);
//
//    public String savePostPGFeeData(final String pInstituteID, final String pWithRegID, final String pStudentID, final String pFeeEventID, final String pSTYNumber, final String pSTYTypeID, final String pEnrollmentNo, final String pTotalFeePaidAmount, final String pPGTransactionID);
//
//    public String createBackUpTable();
//

    public String getTTApprovalAppCanlData(final String instituteid, final String registrationid, final String departmentid, final String status, final String programid, final String academicyear, final String sty);
//
//    public String studentSubjectAttendProcessDateWise(final String pSessionID, final String pInstID, final String pRegistrationID, final String pProgramID, final String pBranchID, final String pSubjectID, final String pSectionID, final String pSubsectionID, final String pStyTypeID, final String pStudentID, final String pStyNumber, final String pFromDateYYMMDD, final String pToDateYYYYMMDD);
//
//    public String saveMarksDataFromExcel(String pUserID, String pTaskID, String pProcessingType, String pErrorCode, String pReturnFlag, String pErrorMessage, String pSPFlag, String pSPCode);
//
//    public String saveGradeDataFromExcel(String pUserID, String pTaskID, String pProcessingType, String pErrorCode, String pReturnFlagvarchar2, String pErrorMessage, String pSPFlag, String pSPCode);
//

    public String subjectRegDataUploadExcel(String pUserID, String pTaskID, String pProcessingType, String pErrorCode, String pReturnFlag, String pErrorMessage, String pSPFlag, String pSPCode);
//
//   public String SchedularCorrectionTool(String pInstituteId, String  pRegistrationID, Date pFromDate, Date pToDate, String pStaffId, String pProgramId, String  pStyNo, String  pSubjectId,String pStaffType,String pMemberId,String pRemarks);
//
//   public String GetSubjWiseBatchesShedular(final String pInstID,final String pRegID, final String  pTTTRnsID);
}
