package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.StudentNRSubjects;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashutosh1.kumar
 */
public interface StudentSubjectChoiceMasterIDAO extends IDAO {

    public String saveStudentSubjectChoiceData(final List<StudentSubjectChoiceMaster> choiceMasterList, final List<StudentNRSubjects> nrSubjectsList, final List<PRStudentSubjectChoiceCount> subjectChoiceCountList, boolean studentlogin);

    public List getSubjectData(final String instid, final String regid, final String acadYear, final String programid, final String semester, final String section, final String subjectType);

    public Map getAllRegdStudentNotInSubjectChoice(final String instituteid, final String registrationid, final String acad_year, final String programid, final String sectionids, float totalcredit, String styno);

    public List getprogSubDetail(String regId, String instId, String subId, String basketId, int styNo);

    public List getStudentSubjectRegistrationDetailsReportData(final String instituteid, final String programid, final String branchid, final String registrationid, final String orderby, String sortedby);

    public List<Object[]> getStudentSubjectChoice(final String studentid, final String instituteid, final String registrationid);

    public List getEnrollmentNumber(String instituteid, String registrationid);

    public List getAllSubjectWiseStudentList(String instituteid, String registrationid, String employeeid);

    public List getAllSubjectWiseStudentExcelList(List searchData);

    public Map getStudentDataForStudentSubjectFinalization(final String instid, final String regid, final List criteriaList, final boolean checkCrLimit, HttpServletRequest request);

    public List getStudentFSTids(final String inst, final String regid, final String studentid);

    public List getSubjectRegistrationData(List instituteid, List registrationid, String status, String stytype, String requestfrom);

    public List getSubjectWiseReportData(String instituteid, String registrationid, String stytype);

    public boolean update_STR(List updateObj);

    public String saveStudentSubjectFinalize(final List<PRFacultyStudentTagging> prfstList, final List<FacultyStudentTagging> saveListFS, final List<StudentRegistration> updateList);

    public List getSubSectionNumber(String instituteid, String registrationid, String academicyear, String subjectid, String section);

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid);

    public List getStudentSubjectChoiceMasterData(String instituteid, String registrationid, String studentid);

    public void deleteStudentSubjectChoiceMasterData(String instituteid, String registrationid, String studentid);

    public List getGridDateForSupplementary(String instituteid, String registrationid, String studentid);

    public List checkDataInPRFST(String instituteid, String fstid, String studentid);

    public List getMainInstidRegidSubid(String instituteid, String registrationid, String subjectid);

    public List getChildInstidRegidSubid(String maininstituteid, String mainregistrationid, String mainsubjectid);

    public List getAutidOrEquivalentsubjectid(String instituteid, String registrationid, String subjectid, byte stynumber, String studentid);

    public List findAllStudentsFromStudentSubjectChoiceMaster(String instituteid, String academicyear, String programid, String sectionid, String registrationid, String subjectid, String subjecttypeid, String stynumber, String subsectionid);
}
