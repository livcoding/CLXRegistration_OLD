package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.UpgradeDegradeStudentReportIService;
import org.springframework.web.context.ServletContextAware;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.StyDesc;
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

/**
 *
 * @author ankur.goyal
 */
@Service
public class UpgradeDegradeStudentReportService extends ReportManager implements UpgradeDegradeStudentReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getAcademicYearCombo(ModelMap model) {
        List acadList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearReg(instituteid);
            model.addAttribute("acadList", acadList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAllBranchCode(HttpServletRequest request) {
        List branchList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = request.getParameter("programCode");
            branchList = (List<Object[]>) daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }

    @Override
    public void getAllStyCode(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list =  daoFactory.getProgramSubjectTaggingDAO().getStyNumberByInstituteid(instituteid);
            model.put("styList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String academicyear = request.getParameter("acadYear");
            String programid = request.getParameter("program");
            String branchid = request.getParameter("Branch");
            String stynumber = request.getParameter("semester");
            String fromdate = request.getParameter("fromDate");
            String todate = request.getParameter("toDate");
            String status = request.getParameter("status");
            String exportto = request.getParameter("exportTo");
            String orderby = request.getParameter("orderby");
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map map = null;
            map = getStudentUpgradeDegradeReportData(programid, branchid, academicyear, instituteid, stynumber, status, fromdate, todate);
            data = (List) map.get("list");
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            parameters = new HashMap();
            if (ims != null || data != null) {
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
                parameters.put("orderby", "Order By: " + (orderby == null ? "" : orderby));
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
            path = request.getRealPath("/jrxml/StudentUpgradeDegradeReport.jrxml");
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "StudentUpgradeDegrade Report");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "StudentUpgradeDegrade Report");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "StudentUpgradeDegrade Report");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "StudentUpgradeDegrade Report");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map getStudentUpgradeDegradeReportData(String programid, String branchid, String academicyear, String instituteid, String stynumber, String status, String fromdate, String todate) {
        List retList = new ArrayList();
        Map object = null;
        int count = 0;
        try {
            List<Object[]> objList = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentUpgradeDegradeReportData(programid, branchid, academicyear, instituteid, stynumber, status, fromdate, todate);
            if (objList != null && objList.size() > 0) {
                for (int i = 0; i < objList.size(); i++) {
                    Object[] o = (Object[]) objList.get(i);
                    object = new HashMap();//name
                    object.put("slno", "" + (++count));
                    object.put("enrollmentno", o[0] == null ? "" : o[0].toString());
                    object.put("name", o[1] == null ? "" : o[1].toString());
                    object.put("prestynumber", o[2] == null ? "" : o[2].toString());
                    object.put("updatestynumber", o[3] == null ? "" : o[3].toString());
                    object.put("docmode", o[4] == null ? "" : o[4].toString());
                    retList.add(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("list", retList);
        return map;
    }
}
