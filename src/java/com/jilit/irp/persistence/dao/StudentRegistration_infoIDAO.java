package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface StudentRegistration_infoIDAO extends IDAO {

    public List getStudentMasterData(String instituteid, String acadYear, String styNum, String branchId, String sectionId);

    public List getStudentRegData(String instituteid, String regId, String academicyear, String styNum, String branchId, String sectionId);

    public List getStyNoFOrNoDuesReport(String instituteid, String regid, String acadyear, String programid);

    public List getStudentRegistration_InfoData(String instituteid, String registrationid, String studentid);

    public void deleteStudentRegistration_InfoData(String instituteid, String registrationid, String studentid);
    
    public List getStyNoFOrStudentBackPaperReport(String instituteid, String registrationid, String programid, String branchid);
}
