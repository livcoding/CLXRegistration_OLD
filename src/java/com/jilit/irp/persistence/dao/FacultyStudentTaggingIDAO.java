package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dto.StudentPreviousAttendence;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocation;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface FacultyStudentTaggingIDAO extends IDAO {

    public String insertFacultyStudentTagging3(final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail);

    public String insertFacultyStudentTagging(final List<FacultyStudentTaggingId> recordsToDelete, final List<StudentRegistration> recordsToUpdSR, final List<PRFacultyStudentTagging> recordsToDeletePRFST, final String regId, final String instituteid, final String studId, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, final List<StudentPreviousAttendence> studentPreviousAttendence);

    public String insertFacultyStudentTagging1(final List<FacultyStudentTagging> recordsToInsert, final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<FacultySubjectTagging> fstList, final List<ProgramSubjectTagging> pstid, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, BusinessService businessService);

    public String insertFacultyStudentTagging2(final List<FacultyStudentTagging> recordsToInsertFST, final List<PRFacultyStudentTagging> recordsToUpdPR, final String regId, final String instituteid, final String studId, final List<FacultySubjectTagging> fstList, final List<ProgramSubjectTagging> pstid, final List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster, final List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail, BusinessService businessService);

    public int checkIfChildExist(final FacultyStudentTaggingId id);

    public void commomDeleteForApplicationMaster(final String query);

    public Collection<?> getStudentMarksAwarded(final String studentid, final String subjectid, final String instituteid,String registrationid);

    public List getFSTID(final String instituteId, final String fstids, final String studentid);

    public String getStudentFstId(String instituteid, String studentid, String fstid);

    public String updateSM(String d);
}
