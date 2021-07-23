package com.jilit.irp.data.common;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author mohit1.kumar
 */
public interface CommonComboIservice {

    public List getProgramMaster();

    public void getadmittedCatgory(ModelMap model);

    public void getAllStudentCategory(ModelMap model);

    public void getAllStudentQuota(ModelMap model);

    public List getsub_admittedCategory(HttpServletRequest req);

    public void getAllRegsitrationCode(ModelMap model);

    public void getAllProgramMaster(ModelMap model);

    public void getAllAcademicYear(ModelMap model);

    public void getAllProgramtype(ModelMap model);

    public void getAcademicYearDeactive(ModelMap model);

    public void getRegistrationCodeLov(ModelMap model);

    public void getRegistrationCodeForReportWise(ModelMap model);

    public List getAllSectionMaster1_child_SMF(String instituteId, String programid, String academicyear, byte stynumber, String branchid);

    public List getSubsectionForStudentmaster(String sectionid, String instituteid, String academicyear, String programid, String styNo);

    public List countryCode_TSM();

    public List stateCode_TSM(String countryid);

    public List cityCode_TSM(String countryid, String stateid);

}
