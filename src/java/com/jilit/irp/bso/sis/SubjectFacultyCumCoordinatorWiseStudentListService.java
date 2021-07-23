package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectFacultyCumCoordinatorWiseStudentListIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ankur.goyal
 */
@Service
public class SubjectFacultyCumCoordinatorWiseStudentListService extends ReportManager implements SubjectFacultyCumCoordinatorWiseStudentListIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getRegistrationCombo(ModelMap model) {
        List regList = null;
        List facultyList = null;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String companyid = jirpsession.getJsessionInfo().getSelectedcompanyid();
            regList = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteId);
            facultyList = (List<Object[]>) daoFactory.getUtilDAO().findSimpleData("findAllV_Staff", new Object[]{companyid});
            model.addAttribute("regCode", regList);
            model.addAttribute("facultyCode", facultyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getSubjectCodeCombo(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String sem = request.getParameter("regId");
            String sem_1[] = sem.split("~@~");
            String registrationid = sem_1[0];
            list = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSubjectCodeUsingFacultySubjectTagging(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getCoordinatorCodeCombo(HttpServletRequest request) {
        List coordinatorList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg = request.getParameter("registrationId");
            String reg_1[] = reg.split("~@~");
            String registrationid = reg_1[0];
            String sub = request.getParameter("subjectId");
            String sub_1[] = sub.split("~@~");
            String subjectid = sub_1[0];
            coordinatorList = (List<Object[]>) daoFactory.getUtilDAO().findSimpleData("findAllV_StaffAsSubjectCoOrdinator", new Object[]{instituteid, instituteid, registrationid, subjectid});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinatorList;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        String facultyid = "";
        String employeecode = "";
        String employeename = "";
        String coordinatorid = "";
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String level = "one";
            String username = jirpsession.getJsessionInfo().getUsername();
            String exportto = request.getParameter("exportTo");
            String regstration = request.getParameter("regId");
            String regstration_1[] = regstration.split("~@~");
            String registrationid = regstration_1[0];
            String registrationcode = regstration_1[1];
            String subject = request.getParameter("subject");
            String subject_1[] = subject.split("~@~");
            String subjectid = subject_1[0];
            String subjectcode = subject_1[1];
            String subjectdesc = subject_1[2];
            String coord = request.getParameter("coordinator");
            String faculty = request.getParameter("faculty");
            if (!faculty.equals("")) {
                String faculty_1[] = faculty.split("~@~");
                facultyid = faculty_1[0];
                employeecode = faculty_1[1];
                employeename = faculty_1[2];
            }else if(!coord.equals("")){
                String cordinator[] = coord.split("~@~");
                coordinatorid = cordinator[0];
                employeename = cordinator[1];
            }
            String path = null;
            HashMap parameters = null;
            List<Object[]> data = null;
            List newlist = new ArrayList();
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            if (level.equals("one")) {
                data = (List<Object[]>) daoFactory.getV_StudentComponentDetailDAO().getSubjectFacultyCumCoordinatorWiseStudentList(instituteid, registrationid, subjectid, facultyid, coordinatorid);
                for (int i = 0; i < data.size(); i++) {
                    Map map = new HashMap();
                    map.put("empname", data.get(i)[0] == null ? "" : data.get(i)[0].toString());
                    map.put("empcode", data.get(i)[1] == null ? "" : data.get(i)[1].toString());
                    map.put("progCode", data.get(i)[2] == null ? "" : data.get(i)[2].toString());
                    map.put("sectionCode", data.get(i)[3] == null ? "" : data.get(i)[3].toString());
                    map.put("subDesc", data.get(i)[4] == null ? "" : data.get(i)[4].toString());
                    map.put("name", data.get(i)[5] == null ? "" : data.get(i)[5].toString());
                    map.put("enrollNo", data.get(i)[6] == null ? "" : data.get(i)[6].toString());
                    map.put("subSecCode", data.get(i)[7] == null ? "" : data.get(i)[7].toString());
                    newlist.add(map);

                }
                path = request.getRealPath("/jrxml/SubjectFacultyCumCoordinatorWiseStudentList.jrxml");
                parameters = new HashMap();
                parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                parameters.put("registrationcode", registrationcode);
                parameters.put("subjectcode", subjectcode);
                parameters.put("employeename", employeename);
                parameters.put("subjectdesc", subjectdesc);
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", (ims.getState() == null) ? "" : ims.getState());
                parameters.put("city", (ims.getCity() == null) ? "" : ims.getCity());
                parameters.put("pin", (ims.getPin()) == null ? "" : ims.getPin());
                parameters.put("image", context.getRealPath(ims.getLogofilename()));
                parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
            }
            if (exportto.contains("P")) {
                renderReport(PDF, path, newlist, response, parameters, "SubjectFacultyCumCoordinatorWiseStudentlistReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, newlist, response, parameters, "SubjectFacultyCumCoordinatorWiseStudentlistReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, newlist, response, parameters, "SubjectFacultyCumCoordinatorWiseStudentlistReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, newlist, response, parameters, "SubjectFacultyCumCoordinatorWiseStudentlistReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
