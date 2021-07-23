/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentSpecializationMaster;
import com.jilit.irp.persistence.dto.StudentSpecializationMasterId;
import java.util.List;

/**
 *
 * @author ankit.kumar
 */
public interface StudentSpecializationMasterIDAO extends IDAO {

    public String getenrollmentCode(String instituteid, String programid, String specid);

    public List getStudentSpecializationMaster(String ins_id);

    public List doValidate(final StudentSpecializationMaster dto, final String mode);

    public List editStudentSpecializationMaster(String ins_id, String prog_id, String spec_id);

    public int checkIfChildExist(final StudentSpecializationMasterId id);
//
//     public String getadmissiontypeId(String studentid);
//
//     public String getenrollmentCode_AdmissionType(String instituteid, String programid,String admissiontypeid);
//
//     public String getadmissiontypeDate(String studentid);
//
//     public List getSpecilization(String instituteid, String programid);
//
//     public List checkIfSpecilizationCodeExist(String speccode,String instituteid, String programid);
//
//     public List getAdmissiontype(String instituteid, String programid);
//
//     public List checkIfAdmissiontypeCodeExist(String admissiontype,String instituteid, String programid);
//
//
}
