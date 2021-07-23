package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Sis_StudentRegActivities;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author shimona.khandelwal
 */
public interface StudentRegistrationIDAO extends IDAO {

    public String getProgramType(String instituteid, String programid);

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid);

    public List getStudentRegistrationData(String instituteid, String registrationid, String studentid);

    public List checkforSubsectionId(String instituteid, String acadYr, String programid, String sectionid, String subsectionid, int styno);

    public List getAcadYear(final String instituteid, final String regid);

    public List getAcadYearStudentMaster(String instituteid, String regid);

    public List getSectionCode(String instituteid, String regid, String acad_year, String programid, String branchid, String stynumber);

    public List getAcadmicyearForSubjectFinalization(String instituteid, String regid);

    public List getAcadmicyearForFst(String instituteid, String regid);

    public List<Object[]> fetchStudentData(String instituteId, String acadYear, String progId, String secId, String indBulkFlag, String studentId);

    public List<Object[]> fetchStudentDataForNewReg(String instituteId, String acadYear, String progId, String secId, String indBulkFlag, String studentId);

    public List<Object[]> getRegAllData(String instituteId, String regId, String acadYear, String progId, String secId);

    public List<Object[]> getRegAllDataForNewReg(String instituteId, String regId, String acadYear, String progId, String secId);

    public List getActivityValues(String instituteid);

    public List getEnrolmentNo(String instituteid, String regid, String acadyear, String programid);

    public List getStudentWiseNoDuesGridData(String instituteid, String regid, String acadyear, String programid);

    public List checkDuplicateEntry(String instituteid, String registrationid, String studentid, String activityid);

    public void saveStudentWiseNoDues(Sis_StudentRegActivities dto);

    public abstract Object findByPrimaryKey2(final Serializable id);

    public void saveAcadWiseNoDues(List objects);

    public List getApprovalAuthorities(String instituteid, List activityids);

    public List getParametervalueFOrRegistrationConfirmation(String instituteid, String moduleid, String parameterid);

    public List getRegistrationNoDuesStatusReportData(String instituteid, String regid, String acadyear, String program, byte semester, List activityids);

    public void delteteStudentRegistrationData(String instituteid, String registrationid, String studentid);

    public String getStudentStytypeid(String instituteid, String registrationid, String studentid);
}
