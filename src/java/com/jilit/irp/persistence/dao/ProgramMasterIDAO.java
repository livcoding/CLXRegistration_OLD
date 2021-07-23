package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface ProgramMasterIDAO extends IDAO {

    public Collection<?> findAllInstituteWise(String instituteid);

    public int checkIfChildExist(final ProgramMasterId programMasterId);

    public List getBranchCode(final String institute, final String progid, final String acad_year);

    public List getBranchCodeForRegNoSetup(final String instituteid, final String progid, final String acad_year);

    public List getBranchCodeForDepartment(final String instituteid, final String progid, final String acad_year);

    public Collection<?> findAllProgramMasterData(final String instituteid);

    public List getprogramMasterList(String instituteid);

    public List getprogramMasterEdit(String instituteid, String programid, String programtypeid);

    public List<String> doValidate(final ProgramMaster programMaster, final String mode);

    public List getProgramCode(String instituteid);
    
    public List getSemesterCode(String instituteid);

    public List getProgramDataForEnrollGen(String instituteid, String academicyear);

    public List getProgramCode_Pst(String ins_id, String acar_year, String reg_id);

    public List getProgramCode(String instituteid, String registrationid, String subjectid, String acad_year);

    public List getProgramForAcadWiseRegistration(String instituteid, String regid);

    public List getAllRunningSubjectFromProgramSchemeAcad11(final String instituteid, final String programid, final List stynumber, final List sectionid, final List academicyear) ;

    public List getAllProgramCodes(final String instituteid, final String programtypeid);

    public List getAllRunningSubjectFromProgramSchemeAcad11_LTP(final String instituteid, final String programid, final String stynumber, final String sectionid, final String academicyear, final String subjectcode);

    public List getPST_Programcodes(String instituteid, String Regid, String academicyear);

    public Collection<?> findAll(String instituteid);

    public List getProgramCode(String instituteid, String regid, String acad_year);

    public List getBranchCode(String instituteid, String regid, String acad_year, String programid);

    public List getStynumber(String instituteid, String regid, String acad_year, String programid);

    public List getProgramForSubjectFinalization(String instituteid, String regid, String acadyear);

    public List getProgramForSubjectFst(String instituteid, String regid, String acadyear);
    
    public List getAllProgramWithBranchCode(final String instituteid, final String programtypeid);

}
