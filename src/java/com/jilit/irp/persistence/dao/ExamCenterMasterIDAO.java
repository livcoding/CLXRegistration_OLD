/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ExamCenterMaster;
import com.jilit.irp.persistence.dto.ExamCenterMasterId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface ExamCenterMasterIDAO extends IDAO {

    public int checkIfChildExist(final ExamCenterMasterId examcenterid);
/*
    public List<String> doValidate(final ExamCenterMaster examCenterMaster, final String mode);

    public List getExamCentreCodeInSeatingPlanAllocation(String instituteid, String registrationid, String exameventid, String spid, String datetime);

    public List checkIfExamCentreCodeInSeatingPlanAllocationExists(String instituteid, String registrationid, String exameventid, String spid, String datetime, String examcentercode);

    public List<Object[]> getExamCentreCodeInInvigilationDutyAllocation(String instituteid, String registrationid, String exameventid, String spid, String datetime);

    public List<Object[]> checkIfGetExamCentreCodeInInvigilationDutyAllocation(String instituteid, String registrationid, String exameventid, String spid, String datetime, String examcentercode);

    public List<Object[]> getExamCentreCodeInSeatingPlanAllocation2(String instituteid, String registrationid, String exameventid, String spid, String datetime, String DT, String datetimeTo);

    public List<Object[]> checkIfExamCentreCodeInSeatingPlanAllocationExists2(String instituteid, String registrationid, String exameventid, String spid, String datetime, String examcentercode, String DT, String datetimeTo);

    public List<Object[]> getAllExamCenterMaster(final String instituteid);

    public List getExamCenterData(final String instituteid);

    public List<Object[]> getExamCentreCode(String instituteid, String registrationid, String exameventid, String spid);

    public List<Object[]> checkIfExamCentreCodeExists(String instituteid, String registrationid, String exameventid, String spid, String examcentercode);

    public String getExamCenterMaster_Code(final String examcenterid);

    public List getExamCenterMaster(final String instituteid);

    public List checkIfExamCenterCodeExists(final String instituteid, final String eccode);

    public List<Object[]> getExamCentreCodeInSeatingPlanAllocation3(String instituteid, String registrationid, String exameventid, String spid, String datetime, String DT, String datetimeTo);

    public List<Object[]> checkIfExamCentreCodeInSeatingPlanAllocationExists3(String instituteid, String registrationid, String exameventid, String spid, String datetime, String examcentercode, String DT, String datetimeTo);

    public List getAllBlocksForSuspendedSlots(String instituteid, String registrationid, String subjectcomponents, String staffs, String subjects, String departments);
*/
}
