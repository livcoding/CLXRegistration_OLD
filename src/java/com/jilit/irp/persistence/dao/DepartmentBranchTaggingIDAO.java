/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

/**
 *
 * @author v.kumar
 */
import com.jilit.irp.persistence.dto.DepartmentBranchTagging;
import java.util.List;

public interface DepartmentBranchTaggingIDAO extends IDAO {

    public List getDepartmentBranchTaggingList(String instituteid);

    public List<String> doValidate(final DepartmentBranchTagging departmentBranchTagging, final String mode);
   
    public List getPopUpData(String instituteid, String programid, String branchid);

    /*   public List getDepartmentId_SCCT(String instituteid, String academicyear, String programid, String branchid);
        
    public List checkIfAcademicYearExists_ExcludePrevious(String instituteid, String pracademicyear,String academicyear);
    
    public List getProgramCode_DBT(String instituteid, String academicyear);
    
    public List checkIfProgramCodeExists_DBT(String instituteid, String academicyear, String programcode);
    
    public List getBranchCode_DBT(String instituteid, String academicyear, String programid);
    
    public List checkIfBranchCodeExists_DBT(String instituteid, String academicyear, String programid, String branchcode);

    public List getDepartmentAccordingToProgram(String instituteid, String programid);

    public List checkifDepartmentAccordingToProgramExist(String instituteid, String programid, String departmentcode);
     */
}
