/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramMinMaxLimit;
import com.jilit.irp.persistence.dto.ProgramMinMaxLimitId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface ProgramMinMaxLimitIDAO extends IDAO {

    public int checkIfChildExist(final ProgramMinMaxLimitId programMinMaxLimit);

    //  public List getProgramMinMaxLimit(ModelMap map);
    public List getProgramMinMaxLimit(String instituteid, String acad_year);

    public List getSemester(String branchid, String acad_year, String instituteid);

    public List getStynoForPST(String instituteid, String registrationid, String academicyear, String programid, String branchid);

    public List getStynoForPST_TeachingSchem(String instituteid, String academicyear, String programid);

    public List getProgramMinMaxLimitEdit(String instituteid, String acad_year, String branchid, String programid, String stynumber);

//
    public String doValidate(final ProgramMinMaxLimitId id);
//

    public String insertProgramMinMaxLimit(final List<ProgramMinMaxLimit> programMinMaxLimitList);
//

    public String updateProgramMinMaxLimit(final List<ProgramMinMaxLimit> programMinMaxLimitList);
//
//    public ArrayList getProgramBranchData(final String instituteid, final String academicYearId,final String sectiontype) ;
//
//    public List getMinMaxLimit_SCCT(String instituteid,String academicyear,String programid,String branchid,byte styno);

    public Collection<?> getStudentMinMaxCradits(final String clientid, final String groupid, final String branchid, final String programid, final String stynumber);

    public Collection<?> getMinMaxLimt(final String instituteid, final String branchid, final String programid, final byte stynumber, final String academicyear);

}
