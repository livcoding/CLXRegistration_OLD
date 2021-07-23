package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.SectionMaster;
import java.util.List;
import com.jilit.irp.persistence.dto.SectionMasterId;
import java.util.Collection;

/**
 *
 * @author ankur.goyal
 */
public interface SectionMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public int checkIfChildExist(SectionMasterId id);

    public List doValidate(final SectionMaster sectionMaster, final String mode);

    public List getSectionCode_Pst(String ins_id, String prg_id, String acad_year, String reg_id);

    public Collection<?> getSection(final String instituteId, final String sectioncode);

    public List getSectionForSubjectFinalization(String instituteid, String regid, String acadyear, String programid, List branchid);

    public List getAllSectionMaster(String instituteid);

    public List getAllSectionMaster_NotSaved(String instituteid, String bas_code,String programid,String styno);

    public List getAllSectionMaster_Fst(String instituteid, String reg_id, String programid, String acad_year);

    public List getSectionCode(String ins_id, String branchid);
    
    public List getSectionCodeByProg(String instituteid, String programid);

    public List getSectionMaster(String instituteid, String registrationid, String subjectid, String acad_year, Byte stynumber, String programid);

    public List getTSRSection(final List stynumber, final List branchid, final List acadyear, final String programid, final String instid);

    public Collection<?> getSection_SMF(final String instituteId, final String programid, final String academicyear, final byte stynumber, final String branchid);

    public List getSectionDataWithBranch(final String instituteid, final String programid, final String branchid);

    public List getAll_SectionData_SCR(final String registrationid, final String programid, final String instituteid);

    public List getSubSectionListForSectionCode(String instituteid, String registrationid, String sectionId);
}
