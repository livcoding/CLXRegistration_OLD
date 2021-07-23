/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface V_ProgrmSecBranchSemIDAO extends IDAO {
    
    public List getSectionSubSectionDataBranchWiseQuery(String instituteid, String programid, String acadYear, Byte stynumber, String branchid);
//
//    public ArrayList getV_ProgramSectionSTYData(final String instituteid, final String academicYearWise);
//
//    public Collection<?> getProgramMasterOrProgramMasters(final String instituteId, final String academicYear, final String programCode);
//
//    public Collection<?> findSTYPattern_InstituteProgramBranch(String instituteId, String programId, String sectionCode);
//
//    public Collection<?> findSTYNumber_InstituteProgramBranch(String instituteId, String programId, String sectionCode, String styPattern);
//
//    public Collection<?> getAcademicYear(final String instituteId, final String academicYear);
//
//    public Collection<?> getSectionorSections(final String instituteId, final String academicYear, final String programId, final String sectionCode);
//
//    public Collection<?> getSTYPattern(final String instituteId, final String academicYear, final String programId, final String sectionId);
//
//    public ArrayList getV_AcademicyearSTYData(final String instituteid, final String academicYearWise);
//
//    public Collection<?> getSTYNumber(final String instituteId, final String academicYear, final String programId, final String sectionId, final String styPattern);
//
//    public ArrayList getBranchAndSectionSubsectionDetail(final String instid, final String programid, final String acadmicyear, final List criteriaList);
//
      public ArrayList getSectionSubsectionDetail(final String instid, final String acadmicyear, final String programid, final Byte stynumber, final String orderby);
//
//    public List getSectionDataBranchWiseQuery(String instituteid, String acadYear, String programid, String branchid, Byte stynumber);
//
//    public List getSubSectionDataBranchAndSectionWiseQuery(String instituteid, String acadYear, String programid, String branchid, Byte stynumber, List sectionid);
//
//    public List getAcademicYearForProgramScheme(final String instituteId);
}
