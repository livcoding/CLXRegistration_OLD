/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramMaxSty;
import com.jilit.irp.persistence.dto.ProgramMaxStyId;
import com.jilit.irp.persistence.dto.StyDesc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Shimona.Khandelwal
 */
public interface ProgramMaxStyIDAO extends IDAO {

    public List getProgramMaxStyData(String instituteid);

    public List editProgramMaxStyData(String instituteid, String programid, String acad_year, String branchid);
//

    public int checkIfChildExist(final ProgramMaxStyId id);

    public List getBranchCode(final String instituteid, final String progid, final String acad_year);


    public List<String> doValidate(final ProgramMaxSty programMaxSty);
//

    public String insertProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList);
//

    public String updateProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList);
    
    public List getSemester(String program_id, String acad_year);
    
    public List getSemesterCode(String ins_id, String prog_id);
  
//
//    public ArrayList getProgramBranchData(final String instituteid, final String academicYearId);
//
//    //public List getSemesterForAcadProgramBranch(String instituteid , String acadYear , String programid  ,String branchid);
//
//    public Collection<?> getBranch(final String instituteId,final String academicyear, final String branchCode);
//
//    public Collection<?> getBranchForPST(final String instituteId,final String programid, final String branchCode);
//
//    public String getSTYDescription(final String instituteId, final String academicyear, final String programid, final String branchid);
//
//    public int getMaxSTY(final String instituteId, final String academicyear, final String programid, final String branchid);
//
//    public List getMaxSTYWithALLOption(final String instituteId, final String academicyear, final String programid, final String branchid);
//
//    public List getAllProgramStyNumber(String instituteid, String registrationid);
//

//
//    public List getSTYDescFromInstituteAndSTYPatternWise(String instituteid, String stypattern, String stratSTY, String endSTY);
}
