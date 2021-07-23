/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectWiseStudentListBeforeFinalIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramWiseSubsection;
import com.jilit.irp.persistence.dto.ProgramWiseSubsectionId;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * import com.jilit.irp.iservice.SubjectWiseStudentListBeforeFinalIService;
 *
 * /**
 *
 * @author ankit.kumar
 */
@Service
public class SubjectWiseStudentListBeforeFinalService extends ReportManager implements SubjectWiseStudentListBeforeFinalIService, ServletContextAware {
    
    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;
    
    private ServletContext context;
    
    public void setServletContext(ServletContext context) {
        this.context = context;
    }
    
    @Override
    public void getSemesterCode(Model model) {
        
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<RegistrationMaster> list = (List<RegistrationMaster>) daoFactory.getUtilDAO().findSimpleData("getAllRegistrationCode_SSC", new Object[]{instituteid, instituteid});
            model.addAttribute("sem_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public List getSubjectCode(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjecttypeid = req.getParameter("sub_type");
        List list = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getSubjectMasterDAO().getSubjectCode_SubjectWise(instituteid, registrationid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
    
    @Override
    public List getAcademicYear(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjectid[] = req.getParameter("sub_id").split(",");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = new ArrayList();
        try {
            list = (List) daoFactory.getAcademicYearDAO().getAcademicYear(instituteid, subjectid[0], registrationid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
    
    @Override
    public List getProgramCode(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjectid[] = req.getParameter("sub_id").split(",");
        String academicyear = req.getParameter("acad_year");
        List list = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid, registrationid[0], subjectid[0], academicyear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
    
    @Override
    public List getSemester(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjectid[] = req.getParameter("sub_id").split(",");
        String academicyear = req.getParameter("acad_year");
        String programid[] = req.getParameter("prog_code").split(",");
        List list2 = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list2 = (List) daoFactory.getStyDescDAO().getSemesterNumber(instituteid, registrationid[0], subjectid[0], academicyear, programid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list2;
        
    }
    
    @Override
    public List getSectionMaster(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjectid[] = req.getParameter("sub_id").split(",");
        String academicyear = req.getParameter("acad_year");
        String programid[] = req.getParameter("prog_code").split(",");
        String stynumber = req.getParameter("sem");
        List list3 = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list3 = (List) daoFactory.getSectionMasterDAO().getSectionMaster(instituteid, registrationid[0], subjectid[0], academicyear, Byte.parseByte(stynumber), programid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list3;
        
    }
    
    @Override
    public List getsubsection(HttpServletRequest req) {
        String registrationid[] = req.getParameter("reg_id").split("~@~");
        String subjectid[] = req.getParameter("sub_id").split(",");
        String academicyear = req.getParameter("acad_year");
        String programid[] = req.getParameter("prog_code").split(",");
        String stynumber = req.getParameter("sem");
        String sectionid[] = req.getParameter("sec").split(",");
        List list = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramWiseSubsectionDAO().getsubsection(instituteid, academicyear, programid[0], stynumber, sectionid[0], subjectid[0], registrationid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
    
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String exportto = request.getParameter("exportto");
            String subjectid = "";
            String subjectcode = "";
            String programid = "";
            String programcode = "";
            String subsectionid = "";
            
            String regid_code = request.getParameter("regid_code");
            String regid_arr[] = regid_code.split("~@~");
            String registrationid = regid_arr[0];
            String registrationcode = regid_arr[1];
            
            if (!request.getParameter("sub_id").toString().equals("all")) {
                String subjectid_arr[] = request.getParameter("sub_id").split(",");
                subjectid = subjectid_arr[0];
                subjectcode = subjectid_arr[1];
            } else {
                subjectid = request.getParameter("sub_id");
            }
            
            if (!request.getParameter("prog_code").equals("all")) {
                String programcode_arr[] = request.getParameter("prog_code").split(",");
                programid = programcode_arr[0];
                programcode = programcode_arr[1];
            } else {
                programid = request.getParameter("prog_code");
            }
            
            String academicyear = request.getParameter("acad_year");
            String stynumber = request.getParameter("sem");
            String sectionid = request.getParameter("sec");
            
            if (!request.getParameter("sub_sec").equals("all")) {
                String subsectionid_arr[] = request.getParameter("sub_sec").split(",");
                subsectionid = subsectionid_arr[0];
            } else {
                subsectionid = request.getParameter("sub_sec");
            }
            
            String subjecttypecode = request.getParameter("subtype_code");
            String subjecttypeid = request.getParameter("sub_type");
            
            String level = request.getParameter("level");
            String path = null;
            HashMap parameters = null;
            List data = null;
            Map map = null;
            String sectioncode = "";
            List list = null;
            if (level.equals("one")) {
//                list = (List) daoFactory.getUtilDAO().findSimpleData("findAllStudentsFromStudentSubjectChoiceMaster", new Object[]{instituteid, academicyear, academicyear, programid, programid, sectionid, sectionid, registrationid, subjectid, subjectid, subjectid, subjectid, instituteid, subjecttypeid, subjecttypeid, stynumber.equals("all") ? "all" : Byte.parseByte(stynumber), stynumber.equals("all") ? "all" : Byte.parseByte(stynumber), subsectionid, subsectionid});
                list = daoFactory.getStudentSubjectChoiceMasterDAO().findAllStudentsFromStudentSubjectChoiceMaster(instituteid, academicyear, programid, sectionid, registrationid, subjectid, subjecttypeid, stynumber, subsectionid);
                System.err.println("Running Subject List::" + list.size());
                if (!list.isEmpty()) {
                    data = new ArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        Object[] object = (Object[]) list.get(i);
                        map = new HashMap();
                        map.put("AcademicYear", object[0].toString());
                        map.put("ProgramCode", object[9].toString());
                        map.put("BranchCode", object[10].toString());
                        map.put("EnrollmentNo", object[5].toString());
                        map.put("StudentName", object[6].toString());
                        map.put("Semester", object[3].toString());
                        map.put("SubjectCode", object[11].toString() + " (" + object[12].toString() + ")");
                        map.put("SubjectDesc", object[12] != null ? object[12].toString() : "");
                        ProgramWiseSubsection pws = (ProgramWiseSubsection) daoFactory.getProgramWiseSubsectionDAO().findByPrimaryKey(new ProgramWiseSubsectionId(instituteid, object[0].toString(), object[1].toString(), Byte.valueOf(object[3].toString()), object[8].toString(), object[7].toString()));
                        map.put("subsection", pws != null ? pws.getSubsectioncode() : "");
                        data.add(map);
                    }
                }
                parameters = new HashMap();
                parameters.put("RegistrationCode", registrationcode);
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                if (ims != null) {
                    parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("subjectcode", (subjectcode == null) ? "" : subjectcode);
                    parameters.put("subjecttypecode", (subjecttypecode == null) ? "" : subjecttypecode);
                    parameters.put("academicyear", (academicyear == null) ? "" : academicyear);
                    parameters.put("programcode", (programcode == null) ? "" : programcode);
                    parameters.put("sectioncode", (sectioncode == null) ? "" : sectioncode);
                    parameters.put("stynumber", (stynumber == null) ? "" : stynumber);
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
                    parameters.put("registrationcode", "");
                    parameters.put("subjectcode", "");
                    parameters.put("subjecttypecode", "");
                    parameters.put("academicyear", "");
                    parameters.put("programcode", "");
                    parameters.put("sectioncode", "");
                    parameters.put("stynumber", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                }
                path = request.getRealPath("/jrxml/SubjectWiseStudentListReport.jrxml");
            }
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "SubjectWiseStudentListReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "SubjectWiseStudentListReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "SubjectWiseStudentListReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "SubjectWiseStudentListReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
