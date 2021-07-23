/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentAttendance;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v.kumar
 */
public interface StudentAttendanceIDAO extends IDAO {

    public boolean saveObjlist(List objList);

    public List getStudentBackPaperListWithFailGrade(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr);

    public List getStudentBackPaperSubjectList(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr);

    public List getFailGradeStudentSubjectRegistrationDeatil(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid);

    public List getStudentCompulsorySubjectList(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr, String registrationid);

    public List getRegularStudentCompulsorySubjectData(String instituteid, String programid, String branchid, String stynumber, String order, String academicyr, String registrationid);

    public List getRegularStudentElectiveSubjectData(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid);

    public List getFailGradeStudentSubjectNotRegisteredDeatil(final String instituteid, final String programid, final String branchid, final String stynumber, final String order, final String academicyr, final String registrationid);

    public Collection<?> getStudentAttendance(String instituteid, String studentid, String studentfstid);

    public int checkStudentEventSubjectMarks(String instituteid, String registrationid, String studentid, String subjectid);

    public int checkStudentAttendance(String instituteid, String registrationid, String studentid, String subjectid);
}
