package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import static com.jilit.irp.Report.ReportManager.PDF;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectCoordinatorReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.persistence.dto.V_Staff;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
@Service
public class SubjectCoordinatorReportService extends ReportManager implements SubjectCoordinatorReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getCoordinate(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getSubjectCoordinatorDAO().getCoordinate(instituteid);
            model.addAttribute("coordinate_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAcademicYear(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getSubjectCoordinatorDAO().getAcademicYear(instituteid);
            model.addAttribute("acadyear", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getSubCode(HttpServletRequest request) {
        List<SubjectMaster> list = null;
        String st[] = request.getParameter("co_ordinate").split(",");
        String staff_type = st[1];
        String reg_arr[] = request.getParameter("regId").split(",");
        String registrationid = reg_arr[0];
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().getCoordinate(instituteid, registrationid, staff_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getEmp_Type(HttpServletRequest request) {
        List<V_Staff> list = null;
        String employeetype = request.getParameter("staff");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = (List<V_Staff>) daoFactory.getV_StaffDAO().getEmp_Type(instituteid, employeetype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String staffid = "";
            String stafftype = "";
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String level = "one"; //P
            String cor_arr[] = null;
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg_arr[] = request.getParameter("regId").split(",");
            if (request.getParameter("co_or_code") != null && !request.getParameter("co_or_code").equals("")) {
                cor_arr = request.getParameter("co_or_code").split(",");
            }
            String registraitonid = reg_arr[0]; //P
            String semestercode = reg_arr[1];  //p
            String subjectid = request.getParameter("sub_code"); //P
            String acadyear = request.getParameter("acadyear"); //P
            String co_ordinate_arr[] = request.getParameter("co_ordinate").split(",");
            String co_ordinate_id = co_ordinate_arr[0];
            String co_ordinate_code = co_ordinate_arr[1];
            String qry = "";
            if (co_ordinate_code.equals("A")) {
                qry = "withOutSubject";
            } else {
                qry = "withSubject";
            }
            String status = request.getParameter("co_ord");
            if (status.equals("A")) {
                staffid = "ALL";
                stafftype = "ALL";
            } else {
                staffid = cor_arr[0];
                stafftype = cor_arr[1];
            }

            String exportto = request.getParameter("radioValue"); //P
            String orderby = request.getParameter("orderVal"); //P
            String sortedby = request.getParameter("sortedVal"); //P
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map aSObject = null;
            aSObject = getSubjectCoordinatorReportData(co_ordinate_id, registraitonid, instituteid, subjectid, stafftype, staffid, orderby, sortedby, acadyear, qry);
            data = (List) aSObject.get("list");
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            parameters = new HashMap();
            if (ims != null || data != null) {
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("semestercode", semestercode == null ? "" : semestercode);
                parameters.put("state", ims.getState() == null ? "" : ims.getState());
                parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                parameters.put("image", context.getRealPath(ims.getLogofilename()));
                parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
            } else {
                parameters.put("institutename", "");
                parameters.put("sessioncode", "");
                parameters.put("academicyear", "");
                parameters.put("address1", "");
                parameters.put("address2", "");
                parameters.put("address3", "");
                parameters.put("state", "");
                parameters.put("city", "");
                parameters.put("pin", "");
                parameters.put("image", "");
            }
            if (co_ordinate_code.equals("A")) {
                path = request.getRealPath("/jrxml/SubjectCoordinatorReportWithoutSubject.jrxml");
            } else {
                path = request.getRealPath("/jrxml/SubjectCoordinatorReport.jrxml");
            }
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "Subject Coordinator Report");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "Subject Coordinator Report");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "Subject Coordinator Report");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "Subject Couordinator Report");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getSubjectCoordinatorReportData(String co_ordinate_id, String registrationid, String instituteid, String subjectid, String stafftype, String staffid, String orderby, String sortedby, String acadyear, String qry) {
        List retList = new ArrayList();
        Map map1 = new HashMap();
        List<Object[]> l = null;
        try {
            if (orderby.equals("C")) {
                orderby = "vs.employeecode " + sortedby;
            } else {
                orderby = "s.subjectcode " + sortedby;
            }
            map1.put("instituteid", instituteid);
            map1.put("coordinatortypeid", co_ordinate_id);
            map1.put("registrationid", registrationid);
            map1.put("subjectid", subjectid);
            map1.put("stafftype", stafftype);
            map1.put("staffid", staffid);
            map1.put("orderby", orderby);
            if (qry.equals("withSubject")) {
                l = (List<Object[]>) daoFactory.getSubjectCoordinatorDAO().getCoordinateWithSubjectCode(instituteid, subjectid, staffid);
                for (int i = 0; i < l.size(); i++) {
                    Object[] obj = (Object[]) l.get(i);
                    Map object = new HashMap();
                    String empcode = obj[2] == null ? "" : obj[2].toString();
                    String empname = obj[3] == null ? "" : obj[3].toString();
                    object.put("srno", i + 1 + "");
                    object.put("employeecodename", empcode + "-" + empname);
                    object.put("", obj[2] == null ? "" : obj[2]);
                    object.put("subjectcode", obj[0] == null ? "" : obj[0]);
                    object.put("subjectdesc", obj[1] == null ? "" : obj[1]);
                    object.put("department", obj[5] == null ? "" : obj[5]);
                    object.put("designation", obj[4] == null ? "" : obj[4]);

                    retList.add(object);
                }
            } else {
                l = (List<Object[]>) daoFactory.getSubjectCoordinatorDAO().getCoordinateWithoutSubjectCode(instituteid,staffid,acadyear);
                for (int i = 0; i < l.size(); i++) {
                    Object[] obj = (Object[]) l.get(i);
                    Map object = new HashMap();
                    String empcode = obj[0] == null ? "" : obj[0].toString();
                    String empname = obj[1] == null ? "" : obj[1].toString();
                    object.put("employeecodename", empcode + "-" + empname);
                    object.put("enrollmentno", obj[2] == null ? "" : obj[2]);
                    object.put("name", obj[3] == null ? "" : obj[3]);
                    object.put("gender", obj[4] == null ? "" : obj[4]);
                    object.put("stynumber", obj[5] == null ? 0 : obj[5].toString());
                    retList.add(object);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map aSObject = new HashMap();
        aSObject.put("list", retList);
        return aSObject;
    }

}
