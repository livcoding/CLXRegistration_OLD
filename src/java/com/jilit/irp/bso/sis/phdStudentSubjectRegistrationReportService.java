package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.phdStudentSubjectRegistrationReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.V_Staff;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.PhdResearchRegistration;
import com.jilit.irp.persistence.dto.PhdSelfcourseRegistration;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
@Service
public class phdStudentSubjectRegistrationReportService extends ReportManager implements phdStudentSubjectRegistrationReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    private ServletContext context;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public List getAllPhdStudents(ModelMap model, HttpServletRequest request) {
        List list = new ArrayList();
        try {
            String registrationid = request.getParameter("sem_code");
            String regType = request.getParameter("regType");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            if (regType.equals("C")) {
                list = (List) daoFactory.getUtilDAO().findSimpleData("findAllStudents_PhdSelfcourseRegistration", new Object[]{instituteid, registrationid});
            } else {
                list = (List) daoFactory.getUtilDAO().findSimpleData("findAllStudents_PhdResearchRegistration", new Object[]{instituteid, registrationid});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStudentInfo(HttpServletRequest request) {
        List retObj = new ArrayList();
        HashMap data = null;
        try {
            String registrationid = request.getParameter("sem_code");
            String studentid = request.getParameter("sem_code");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List studRegList = (List) daoFactory.getUtilDAO().findSimpleData("findStudentFromStudentregistration", new String[]{instituteid, registrationid, studentid});
            if (!studRegList.isEmpty()) {
                StudentRegistration registration = (StudentRegistration) studRegList.get(0);
                Map generalInfo = new HashMap();
                generalInfo.put("enrollmentno", registration.getStudentmaster().getEnrollmentno());
                generalInfo.put("studentid", registration.getStudentmaster().getStudentid());
                generalInfo.put("name", registration.getStudentmaster().getName());
                generalInfo.put("registrationcode", registration.getRegistrationmaster().getRegistrationcode());
                generalInfo.put("stynumber", registration.getStynumber());
                generalInfo.put("programcode", registration.getStudentmaster().getBranchmaster().getProgrammaster().getProgramcode());
                generalInfo.put("branchcode", registration.getStudentmaster().getBranchmaster().getBranchcode());
                generalInfo.put("registrationid", registration.getRegistrationmaster().getId().getRegistrationid());
                generalInfo.put("programdesc", registration.getStudentmaster().getBranchmaster().getProgrammaster().getProgramdesc());
                generalInfo.put("branchdesc", registration.getStudentmaster().getBranchmaster().getBranchdesc());
                retObj.add(generalInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObj;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpession.getJsessionInfo().getUsername();
            String registrationid = request.getParameter("sem_code");
            String studentid = request.getParameter("enr_no");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String registrationtype = request.getParameter("reg");
            String enrollmentno = "";
            String branchcode = "";
            String programcode = "";
            String branchdesc = "";
            String programdesc = "";
            String registrationcode = "";
            String name = "";
            Byte stynumber = 0;
            List studRegList = (List) daoFactory.getUtilDAO().findSimpleData("findStudentFromStudentregistration", new String[]{instituteid, registrationid, studentid});
            if (!studRegList.isEmpty()) {
                StudentRegistration registration = (StudentRegistration) studRegList.get(0);
                enrollmentno = registration.getStudentmaster().getEnrollmentno();
                branchcode = registration.getStudentmaster().getBranchmaster().getBranchcode();
                programcode = registration.getStudentmaster().getBranchmaster().getProgrammaster().getProgramcode();
                branchdesc = registration.getStudentmaster().getBranchmaster().getBranchdesc();
                programdesc = registration.getStudentmaster().getBranchmaster().getProgrammaster().getProgramdesc();
                registrationcode = registration.getRegistrationmaster().getRegistrationcode();
                name = registration.getStudentmaster().getName();
                studentid = registration.getStudentmaster().getStudentid();
                stynumber = registration.getStynumber();
                registrationid = registration.getRegistrationmaster().getId().getRegistrationid();
            }
            List data = null;
            String exportto = request.getParameter("radioValue");
            String level = "one";
            String path = null;
            HashMap parameters = null;
            parameters = new HashMap();
            if (studentid != null) {
                if (registrationtype.equals("C")) {
                    List list = (List) daoFactory.getUtilDAO().findSimpleData("findSubjectsFromPhdSelfCourseRegistration", new Object[]{instituteid, registrationid, studentid});
                    data = new ArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        PhdSelfcourseRegistration phdSelfcourseRegistration = (PhdSelfcourseRegistration) list.get(i);
                        Map map = new HashMap();
                        map.put("group", "1");
                        map.put("basketdesc", phdSelfcourseRegistration.getSelfcourse().equals('P') ? "Prescribed Course" : phdSelfcourseRegistration.getSelfcourse().equals('N') ? "Non-Prescribed Course" : "Self Study");
                        map.put("subjectcode", phdSelfcourseRegistration.getSubjectcode());
                        map.put("subjectdesc", phdSelfcourseRegistration.getSubjectdesc());
                        map.put("credits", phdSelfcourseRegistration.getCredits());
                        data.add(map);
                    }
                    path = request.getRealPath("/jrxml/PhdStudentSubjectChoiceReport.jrxml");
                } else {
                    List list1 = daoFactory.getUtilDAO().findSimpleData("findSubjectsFromPhdResearchRegistration", new Object[]{instituteid, registrationid, studentid});
                    data = new ArrayList();
                    for (int i = 0; i < list1.size(); i++) {
                        PhdResearchRegistration phdResearchRegistration = (PhdResearchRegistration) list1.get(0);
                        Map map = new HashMap();
                        map.put("internalguide1", phdResearchRegistration.getInternalguide1());
                        map.put("internalguide2", phdResearchRegistration.getInternalguide2());
                        map.put("externalguide", phdResearchRegistration.getExternalguide());
                        map.put("thesistopic", phdResearchRegistration.getThesistopic());
                        map.put("thesisabstract", phdResearchRegistration.getThesisabstract());
                        data.add(map);
                        if (phdResearchRegistration.getInternalguide1() != null) {
                            V_Staff staff1 = (V_Staff) daoFactory.getV_StaffDAO().findByPrimaryKey(phdResearchRegistration.getInternalguide1());
                            parameters.put("internalguide1", staff1.getEmployeename());
                        } else {
                            parameters.put("internalguide1", "");
                        }
                        if (phdResearchRegistration.getInternalguide2() != null) {
                            V_Staff staff2 = (V_Staff) daoFactory.getV_StaffDAO().findByPrimaryKey(phdResearchRegistration.getInternalguide2());
                            parameters.put("internalguide2", staff2.getEmployeename());
                        } else {
                            parameters.put("internalguide2", "");
                        }
                        parameters.put("externalguide", phdResearchRegistration.getExternalguide());
                        parameters.put("thesistopic", phdResearchRegistration.getThesistopic());
                        parameters.put("thesisabstract", phdResearchRegistration.getThesisabstract());
                    }
                    path = request.getRealPath("/jrxml/PhdStudentResearchChoiceReport.jrxml");
                }
            }

            if (level.equals("one")) {
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                System.out.println("InstituteMAster::" + ims);
                if (ims != null) {
                    parameters.put("institutename", ims.getInstitutename());
                    parameters.put("enrollmentno", enrollmentno);
                    parameters.put("branchcode", branchcode);
                    parameters.put("programcode", programcode);
                    parameters.put("branchdesc", branchdesc);
                    parameters.put("programdesc", programdesc);
                    parameters.put("stynumber", stynumber);
                    parameters.put("name", name);
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState());
                    parameters.put("city", ims.getCity());
                    parameters.put("pin", ims.getPin());
                    parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                } else {
                    parameters.put("institutename", " ");
                    parameters.put("enrollmentno", " ");
                    parameters.put("branchcode", " ");
                    parameters.put("programcode", " ");
                    parameters.put("branchdesc", " ");
                    parameters.put("programdesc", " ");
                    parameters.put("stynumber", " ");
                    parameters.put("name", " ");
                    parameters.put("registrationcode", " ");
                    parameters.put("address1", " ");
                    parameters.put("address2", " ");
                    parameters.put("address3", " ");
                    parameters.put("state", " ");
                    parameters.put("city", " ");
                    parameters.put("pin", " ");
                    parameters.put("image", " ");
                }
            }

            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "PhDCourseRegistrationReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "PhDCourseRegistrationReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "PhDCourseRegistrationReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "PhDCourseRegistrationReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jirpession.getJsessionInfo().removeSession();
        }
    }
}
