/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;
import com.jilit.irp.persistence.dto.StudentAttendance;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bijendra1.kumar
 */
public interface DateWiseTimeTableAttendanceEntrySchedulerIDAO extends IDAO {

  /*  public String insertStudentAttendance(final List<StudentAttendance> recordsToInsert);

    public String editStudentAttendance(final List<StudentAttendance> recordsToInsert);

    public List<String> doValidate(final StudentAttendance studentAttendance, final String mode);

    public Collection<?> getDateWiseStudentAttendanceList(String studentid, String fstid, String attendancetype);

    public List getMaxLastDayAttendance(String instituteid, String companyid);

    public List getSemesterCodeForAttendance(String instituteid);

    public List getFacultySubjectsForDateWiseTimeTableAttendanceEntry(String instituteid, String registrationid, String staffid, String day, String attendancedate, String maxDays);

    public List getGridData(String instituteid, String employeeid, String registrationid, String subjectid, String subjectcomponentid, String sectionid, String subsectionid, String programid);

    public List checkAttendanceStatus1(String instituteid, Date attendancedate, String slotid, String registrationid, String programid, String sectionid, String subsectionids, String subjectid, String subjectcomponentid, String ttreferenceid);

    public List getMaxEditAttendanceAllowedDays(String instituteid, String companyid);

    public List getSuspendedSlots(final String instituteid, final Date attendancedate, final String slotid, final String registrationid, final String programid, final String sectionid, final String subsectionids, final String subjectid, final String subjectcomponentid, final String staffid);

    public List getLoadTransferDateWise(String instituteid,Date attendancedate, String slotid, String registrationid, String programid, String sectionid, String subsectionids, String subjectid, String subjectcomponentid, String staffid, String selfstaffid);

    public List getLoadTransferDateWiseNotGiven(String instituteid,Date attendancedate, String slotid, String registrationid, String programid, String sectionid, String subsectionids, String subjectid, String subjectcomponentid, String staffid);

    public List checkAttendanceStatus(String instituteid, Date attendancedate, String slotid, String registrationid, String programid, String sectionid, String subsectionids, String subjectid, String subjectcomponentid, String staffid);

    public List getDbAttendanceData(String instituteid, String programid, String sectionid, String subjectid, String subjectcomponentid, String registrationid, String subsectionids, String slotid, String staffid, String attendancedate);

    public List getAttenanceData(String programid, String sectionid, String subjectid, String subjectcomponentid, String registrationid, String subsectionid, String instituteid, String attendancedate);

    public List getActivityStatus(String instituteid, String studentid, String attendancedate);

    public String checkNoofAbsentDays(String instituteid, String companyid);

    public List getstudentAttRecord(String studentid, String studentfstid, String instid, String attendancedate);

    public List getAttendanceReportData(String instituteid, String programid, String sectionid, String subjectid, String subjectcomponentid, String registrationid, String subsectionids, String slotid, String staffid, String attendancedate);

    public List getLeaveStatus(String instituteid, String studentid, String attendancedate);

    public boolean saveObjlist(List objList);

     public String GetSubjWiseBatchesShedular(String pInstID,String  pRegID,String pTTTRnsID);

*/
}
