package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import java.util.List;

/**
 *
 * @author ashutosh1.kumar
 */
public interface DropElectiveSubjectIDAO extends IDAO {

    public List getRegistrationCodeForDropElective(String instituteid);

    public List getAllSubjectType(String instituteid, String registrationid);

    public List getSubjectToBeDroped(String instituteid, String registrationid, String subjecttypeid);

    public List getDataInDataGrid(String instituteid, String registrationid, String subjectid);

    public List getAllStudentRecord(String instituteid, String registrationid, String subjectid, String subjecttypeid);

    public List getAllSubjectRecord(String instituteid, String registrationid, String studentid, String basketid, String subjectid);

    public List getTotalNoOfStudent(String instituteid, String registrationid, String basketid, String subjectid);

    public List getTotalAssignedCount(String instituteid, String registrationid, String studentid);

    public List<StudentSubjectChoiceMaster> getAllStudentRecordBatchWise(String instituteid, String registrationid, String subjectid, List acdyrlist, List prlist, List brlist, List stynolist, List stytypelist);

}
