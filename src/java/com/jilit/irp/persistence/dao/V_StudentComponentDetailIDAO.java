/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.V_StudentComponentDetail;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface V_StudentComponentDetailIDAO {

    public List getSubjectFacultyCumCoordinatorWiseStudentList(String instituteid, String registrationid, String subjectid, String facultyid, String coordinatorid);

    public List getClassWiseStudentList(String registrationid, String sectionid, String subjectcomponentid, String subjectid, String subsectionid, String instituteid);
//    public Collection<?> getSubjectFacultyCumCoordinatorWiseStudentList(final String instituteid, final String registrationid, final String subjectid, final String facultyid, final String coordinatorid);
//    public Collection<?> getFacultyCumSubjectWiseStudentTotalList(final String registrationid, final String subjectcomponentid, final String orederby, final String sortedby);
//
//    public Collection<?> getFacultyCumSubjectWiseStudentTotalList(final String instituteid, final String registrationid, final String subjectcomponentid, final String orederby, final String sortedby);
//
//    public Collection<?> getStudentListSubjectWiseStytype(final String registrationid, final String subjectid, final String stytypeid, final String instituteid);
//
//    public Collection<?> getStudentListRegWise(final String registrationid, final String instituteid);
//
//    public Collection<?> getStudentListRegWiseNotInHold(final String registrationid, final String instituteid);
//
//    public Collection<?> getStudentListRegWiseCheckExist(final String registrationid, final String instituteid, final String enrollmentno);
//
//    public Collection<?> checkIfStudentListRegWiseExists(final String registrationid, final String instituteid, final String enrollmentno);
//
//    public List getProgramSubjectWise(final String instituteid, final String registrationid, final String subjectid);
//
//    public List getProgramSubjectWiseAttendance(final String instituteid, final String registrationid, final String subjectid, final String programid, final String sectionid, final String stynumber);
//
//    public List getShortAttendanceDataForReport(final String registrationid, final String programid, final String sectionid, final String sectionid0, final String stynumber, final String subjectid, final String orderby);
//
//    public List getStudentFSTID(final String instituteid, final String registrationid, final String studentid, final String fstid);
//

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid);
}
