package com.jilit.irp.data.common;

import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramType;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.StudentCategory;
import com.jilit.irp.persistence.dto.StudentQuota;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author mohit1.kumar
 */
@Service
public class CommonComboService implements CommonComboIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public List getProgramMaster() {
        List list = new ArrayList();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public void getadmittedCatgory(ModelMap model) {
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> categoryList = daoFactory.getStudentAdmissionCategoryDAO().getadmittedCategory(instituteid);
            if (categoryList != null && categoryList.size() > 0) {
                model.put("admtCatg", categoryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getsub_admittedCategory(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String admittedcategoryid = req.getParameter("admitCatId");
            List<Object[]> subcategoryList = daoFactory.getStudentAdmissionSubCategoryDAO().getsub_admittedCategory(instituteid, admittedcategoryid);
            if (subcategoryList != null && subcategoryList.size() > 0) {
                rtnlist.add(subcategoryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    public void getAllStudentCategory(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<StudentCategory> list = (List<StudentCategory>) daoFactory.getStudentCategoryDAO().getStudentCategoryList(instituteid);
            model.put("category", list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAllStudentQuota(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<StudentQuota> list = (List<StudentQuota>) daoFactory.getStudentQuotaDAO().getAllQuota(instituteid);
            model.put("quota", list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAllRegsitrationCode(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<RegistrationMaster> list = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().getAllRegistrationCode(instituteid);
            model.put("regisList", list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void getAllProgramMaster(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.put("programList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllAcademicYear(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<Academicyear> list = (List<Academicyear>) daoFactory.getAcademicYearDAO().findAll(instituteid);
            model.put("acadYearList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllProgramtype(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<ProgramType> list = (List<ProgramType>) daoFactory.getProgramTypeDAO().findAllInstituteWise(instituteid);
            model.put("programTypeList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAcademicYearDeactive(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getAcademicYearDAO().getAcademicYearDeactiveWise(instituteid);
            model.addAttribute("acadYear", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for semester or registration code lov
     * for master and transaction data.
     * @param model
     */
    public void getRegistrationCodeLov(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.addAttribute("semester", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRegistrationCodeNofastrack(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegCodeForOST(instituteid);
            model.addAttribute("semester", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getAllSectionMaster1_child_SMF(String instituteId, String programid, String academicyear, byte stynumber, String branchid) {
        List list = (List) daoFactory.getSectionMasterDAO().getSection_SMF(instituteId, programid, academicyear, stynumber, branchid);
        List<Object> retList = new ArrayList<Object>();
        List l = new ArrayList();
        HashMap data = null;
        for (int i = 0; i < list.size(); i++) {
            data = (HashMap) list.get(i);
            retList.add((String) data.get("sectioncode"));
            retList.add((String) data.get("sectiondesc"));
            retList.add((String) data.get("sectionid"));
        }
        l.add(retList);
        return l;
    }

    public List getSubsectionForStudentmaster(String sectionid, String instituteid, String academicyear, String programid, String styNo) {
        List list = null;
        List retlist = new ArrayList();
        try {
            list = (List) daoFactory.getStudentMasterDAO().find("select new map(a.id.subsectionid as subsectionid, a.subsectioncode as subsectioncode) from ProgramWiseSubsection a where a.id.instituteid='" + instituteid + "' "
                    + "and a.id.academicyear='" + academicyear + "' and a.id.programid='" + programid + "' and a.id.sectionid='" + sectionid + "' and a.id.stynumber='" + styNo + "' ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * @description This method is used for the Registration Code or Semester
     * Code lov for Report data.
     * @param model
     */
    public void getRegistrationCodeForReportWise(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getAllRegistrationCodeDesc(instituteid);
            model.addAttribute("regList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List countryCode_TSM() {
        List<Object[]> countrylist = (List<Object[]>) daoFactory.getCountryMasterDAO().getAllCountryCode();
        return countrylist;
    }

    public List stateCode_TSM(String countryid) {
        List<Object[]> statelist = (List<Object[]>) daoFactory.getStateMasterDAO().getAllStateCode(countryid);
        return statelist;
    }

    public List cityCode_TSM(String countryid, String stateid) {
        List rtnList = new ArrayList();
        List<Object[]> citylist = (List<Object[]>) daoFactory.getCityMasterDAO().getAllCityCode(countryid, stateid);
        return citylist;
    }

}
