/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.TeachingSchemeReportRegistrationWiseIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.DepartmentSubjectTagging;
import com.jilit.irp.persistence.dto.DepartmentSubjectTaggingId;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramScheme;
import com.jilit.irp.persistence.dto.ProgramSchemeDetail;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.SectionMaster;
import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.persistence.dto.SubjectAreaMaster;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ankit.kumar
 */
@Service
public class TeachingSchemeReportRegistrationWiseService extends ReportManager implements TeachingSchemeReportRegistrationWiseIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    private ServletContext context;

    @Autowired
    JIRPSession jirpsession;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getAcadmicYearData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Academicyear> acadyear = (List<Academicyear>) daoFactory.getUtilDAO().findSimpleData("getAllAcademicYear_PST", new String[]{instituteid, instituteid});
            model.addAttribute("acadyear_data", acadyear);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getSemesterdata(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<RegistrationMaster> sty_data = (List<RegistrationMaster>) daoFactory.getUtilDAO().findSimpleData("getAllRegistrationCode_PST", new String[]{instituteid, instituteid});
            model.addAttribute("sty_data", sty_data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getProgramData(HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String academicyear = req.getParameter("acadYear");
        String registrationid = req.getParameter("sty_code");
        List<ProgramMaster> programlist = null;
        try {
            programlist = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().getProgramCode_Pst(instituteid, academicyear, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programlist;

    }

    @Override
    public List getSemData(HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String academicyear = req.getParameter("acadYear");
        String registrationid = req.getParameter("sty_code");
        String programid = req.getParameter("program");
        List list = null;
        try {
            list = (List) daoFactory.getStyDescDAO().getStyNumber(instituteid, registrationid, academicyear, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSectionData(HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String academicyear = req.getParameter("acadYear");
        String registrationid = req.getParameter("sty_code");
        String programid = req.getParameter("program");
        List<StyDesc> list = null;
        try {
            list = (List<StyDesc>) daoFactory.getSectionMasterDAO().getSectionCode_Pst(instituteid, programid, academicyear, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid =jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reporttype = request.getParameter("reportType");
            String exportto = request.getParameter("exportto");
            String registrationid = request.getParameter("registrationid");
            String registrationcode = request.getParameter("registrationcode");
            String academicyear = request.getParameter("academicyear");
            String programid = request.getParameter("programid");
            String stynumber = request.getParameter("stynumber");
            String sectionid = request.getParameter("sectionid");
            String level = "one";
            String path = null;
            HashMap parameters = null;
            List data = null;
            Map map = null;
            Map map1 = null;
            String l = "", t = "", p = "";
            String programname = "";
            String sectioncode = "";
            List<Object[]> list = null;
            List<Object[]> list_ltp = null;
            List<ProgramScheme> list1 = null;
            if (level.equals("one")) {
                if (reporttype.equals("D")) {
                    list = (List<Object[]>) daoFactory.getProgramSubjectTaggingDAO().getAllRunningSubjectFromProgramSubjectTagging(instituteid, registrationid, programid, sectionid, academicyear, stynumber);
                    System.err.println("Running Subject List::" + list.size());
                    if (!list.isEmpty()) {
                        String subjectcode = "";
                        data = new ArrayList();
                        for (int i = 0; i < list.size(); i++) {
                            map = new HashMap();
                            map.put("SubjectCode", list.get(i)[1].toString());
                            subjectcode = list.get(i)[1].toString();
                            map.put("SubjectName", list.get(i)[2].toString());
                            list_ltp = (List<Object[]>) daoFactory.getProgramSubjectTaggingDAO().getAllRunningSubjectFromProgramSubjectTagging_LTP(instituteid, registrationid, programid, sectionid, academicyear, stynumber, subjectcode);
                            map1 = new HashMap();
                            for (int j = 0; j < list_ltp.size(); j++) {
                                if (list.get(i)[1].toString().equals(list_ltp.get(j)[1])) {
                                    if (list_ltp.get(j)[11].toString().equals("L")) {
                                        l = list_ltp.get(j)[12].toString();
                                        map1.put("l", l);
                                    } else if (list_ltp.get(j)[11].toString().equals("T")) {
                                        t = list_ltp.get(j)[12].toString();
                                        map1.put("t", t);
                                    } else if (list_ltp.get(j)[11].toString().equals("P")) {
                                        p = list_ltp.get(j)[12].toString();
                                        map1.put("p", p);
                                    }
                                }
                            }
                            map.put("l", (map1.get("l") == null) ? "0" : map1.get("l"));
                            map.put("t", (map1.get("t") == null) ? "0" : map1.get("t"));
                            map.put("p", (map1.get("p") == null) ? "0" : map1.get("p"));
                            map.put("Credit", list.get(i)[3].toString());

                            /**
                             * **********Following code has been added on
                             * 02-06-2014******************
                             */
                            map.put("stynumber", "Semester: " + list.get(i)[4].toString() + "");
                            String programidparam = list.get(i)[5] == null ? "" : list.get(i)[5].toString();
                            String sectionidparam = list.get(i)[6] == null ? "" : list.get(i)[6].toString();
                            String subjectid = list.get(i)[7] == null ? "" : list.get(i)[7].toString();
                            String programcode = daoFactory.getProgramSubjectTaggingDAO().getProgramCode(instituteid, programidparam);
                            map.put("programcode", "Program: " + programcode);
                            String sectioncodeparam = daoFactory.getProgramSubjectTaggingDAO().getSectionCode(instituteid, sectionidparam);
                            map.put("sectioncode", "Section: " + sectioncodeparam);
                            //String subjecttype = daoFactory.getSubjectMasterDAO().getSubjectType(instituteid, subjectid);
                            map.put("subjecttype", list.get(i)[11].toString());
                            map.put("basketcode", list.get(i)[12] == null ? "" : list.get(i)[12].toString());
                            map.put("deparment", list.get(i)[13] == null ? "" : list.get(i)[13].toString());
                            if ("Y".equals(list.get(i)[14])) {
                                map.put("running", "Yes");
                            } else {
                                map.put("running", "NO");
                            }

                            /**
                             * **********************************************************************
                             */
                            String subjectareacode = "";
                            DepartmentSubjectTagging departmentSubjectTagging = (DepartmentSubjectTagging) daoFactory.getDepartmentSubjectTaggingDAO().findByPrimaryKey(new DepartmentSubjectTaggingId(list.get(i)[0].toString(), list.get(i)[7].toString(), list.get(i)[8].toString()));
                            if (departmentSubjectTagging != null) {
                                if (departmentSubjectTagging.getSubjectareaid() != null) {
                                    String sub_area_id = departmentSubjectTagging.getSubjectareaid().toString();
                                  //  SubjectAreaMaster subjectAreaMaster = (SubjectAreaMaster) daoFactory.getSubjectAreaMasterDAO().findByPrimaryKey(departmentSubjectTagging.getSubjectareaid().toString());
                                    SubjectAreaMaster subjectAreaMaster = (SubjectAreaMaster) daoFactory.getSubjectAreaMasterDAO().findByPrimaryKey(sub_area_id);
                                    if (subjectAreaMaster != null) {
                                        subjectareacode = subjectAreaMaster.getSubjectareacode();
                                    }
                                }
                            }

                            map.put("SubjectArea", subjectareacode);
                            data.add(map);
                            if (i == 0) {
                                programname = list.get(i)[9].toString();
                                sectioncode = list.get(i)[10].toString();
                            }
                        }
                    }
                    path = request.getRealPath("/jrxml/NewGropupBy.jrxml");
                } else {
                    list1 = (List<ProgramScheme>) daoFactory.getProgramSubjectTaggingDAO().getAllRunningSubjectFromProgramScheme(instituteid, programid, stynumber, sectionid);
                    if (list1 != null && !list1.isEmpty()) {
                        if (!list1.isEmpty()) {
                            data = new ArrayList();
                            String pElectiveId = "";
                            boolean flag = true;
                            //String lElectiveid = "";
                            for (int i = 0; i < list1.size(); i++) {
                                ProgramScheme pst = list1.get(i);
                                // map = new HashMap();
                                flag = true;
                                if (pst.getElectiveid() != null) {
                                    if (!pst.getElectiveid().equals(pElectiveId)) {
                                        map = new HashMap();
                                        pElectiveId = pst.getElectiveid();
                                        ElectiveMaster electiveMaster = (ElectiveMaster) daoFactory.getElectiveMasterDAO().findByPrimaryKey(new ElectiveMasterId(instituteid, pst.getElectiveid()));
                                        map.put("SubjectCode", electiveMaster.getElectivecode());
                                        map.put("SubjectName", electiveMaster.getElectivedesc());
                                        map.put("Credit", electiveMaster.getCredits());
                                        map.put("Credit", electiveMaster.getCredits());
                                        DepartmentSubjectTagging departmentSubjectTagging = (DepartmentSubjectTagging) daoFactory.getDepartmentSubjectTaggingDAO().findByPrimaryKey(new DepartmentSubjectTaggingId(pst.getId().getInstituteid(), pst.getSubjectid(), pst.getDepartmentid()));
                                        map.put("SubjectArea", departmentSubjectTagging == null ? "" : departmentSubjectTagging.getSubjectareamaster().getSubjectareacode());
                                    } else {
                                        flag = false;
                                    }
                                } else {
                                    map = new HashMap();
                                    map.put("SubjectCode", pst.getSubjectmaster().getSubjectcode());
                                    map.put("SubjectName", pst.getSubjectmaster().getSubjectdesc());
                                    map.put("Credit", Short.valueOf(Byte.toString(pst.getCredits())));
                                    DepartmentSubjectTagging departmentSubjectTagging = (DepartmentSubjectTagging) daoFactory.getDepartmentSubjectTaggingDAO().findByPrimaryKey(new DepartmentSubjectTaggingId(pst.getId().getInstituteid(), pst.getSubjectid(), pst.getDepartmentid()));
                                    map.put("SubjectArea", departmentSubjectTagging == null ? "" : departmentSubjectTagging.getSubjectareamaster().getSubjectareacode());

                                }
                                if (flag) {
                                    List<ProgramSchemeDetail> psds = (List<ProgramSchemeDetail>) daoFactory.getUtilDAO().findSimpleData("findRelativeProgramSchemeDetailData", new Object[]{instituteid, pst.getId().getProgramschemeid()});
                                    if (!psds.isEmpty()) {
                                        for (int j = 0; j < psds.size(); j++) {
                                            ProgramSchemeDetail programSchemeDetail = (ProgramSchemeDetail) psds.get(j);
                                            SubjectComponent sc = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(instituteid, programSchemeDetail.getId().getSubjectcomponentid()));
                                            if (sc != null) {
                                                if (sc.getSubjectcomponentcode().equals("L")) {
                                                    map.put("Lecture", Short.valueOf((programSchemeDetail.getLtppassingmarks()).toString()));
                                                } else if (sc.getSubjectcomponentcode().equals("T")) {
                                                    map.put("Tutorial", Short.valueOf(programSchemeDetail.getLtppassingmarks().toString()));
                                                } else if (sc.getSubjectcomponentcode().equals("P")) {
                                                    map.put("Practical", Short.valueOf(programSchemeDetail.getLtppassingmarks().toString()));
                                                }

                                            }

                                        }
                                    } else {
                                        map.put("Lecture", new Short("0"));
                                        map.put("Tutorial", new Short("0"));
                                        map.put("Practical", new Short("0"));
                                    }
                                    data.add(map);
                                }
                                if (i == 0) {
                                    programname = pst.getProgrammaster().getProgramdesc();
                                    sectioncode = pst.getSectionmaster().getSectioncode();
                                }
                            }
                        }
                    }
                    path = request.getRealPath("/jrxml/NewGroupBy1.jrxml");
                }
                parameters = new HashMap();
                parameters.put("RegistrationCode", registrationcode);
                parameters.put("AcademicYear", academicyear);
                parameters.put("ProgramName", programname);
                parameters.put("StyNumber", stynumber);
                parameters.put("SectionCode", sectioncode);
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                if (ims != null) {
                    parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState() == null ? "" : ims.getState());
                    parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                    parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                    parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                } else {
                    parameters.put("institutename", "");
                    parameters.put("address1", "");
                    parameters.put("address2", "");
                    parameters.put("address3", "");
                    parameters.put("state", "");
                    parameters.put("city", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                }
            }

            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "TeachingSchemeReport");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
