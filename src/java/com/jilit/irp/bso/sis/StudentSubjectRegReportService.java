/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.bso.xlsexport.ExcelExport;
import com.jilit.irp.Report.ReportManager;
import static com.jilit.irp.Report.ReportManager.EXCEL;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.StudentSubjectRegReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.util.JIRPSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @author deepak.gupta
 */
@Service
public class StudentSubjectRegReportService extends ReportManager implements StudentSubjectRegReportIService, ServletContextAware {

    @Autowired
    JIRPSession jirpsession;

    @Autowired
    DAOFactory daoFactory;

    private ServletContext context;
    private HttpServletRequest request;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void getRegProgramList(Model model) {
        List<RegistrationMaster> regList = null;
        List<ProgramMaster> progList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            progList = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().findAll(instituteid);
            model.addAttribute("regList", regList);
            model.addAttribute("progList", progList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getBranch(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String programId[] = req.getParameter("programId").split("~@~");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> ccList = daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programId[0]);
            if (ccList != null && ccList.size() > 0) {
                rtnlist.add(ccList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid[] = request.getParameter("regId").split("~@~");
            String programid[] = request.getParameter("programId").split("~@~");
            String branchid[] = request.getParameter("branch").split("~@~");
            String exportto = request.getParameter("radioValue");
            String orderby = request.getParameter("orderBy");
            String sortedby = request.getParameter("sortBy");
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map map = new HashMap();
            map = getStudentSubjectRegistrationDetailsReportData(instituteid, programid[0], branchid[0], registrationid[0], orderby, sortedby);
            data = (List) map.get("list");
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            parameters = new HashMap();
            if (ims != null || data != null) {
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", (ims.getState() == null) ? "" : ims.getState());
                parameters.put("registrationcode", registrationid[1]);
                parameters.put("branchcode", branchid[1]);
                parameters.put("programcode", programid[1]);
                parameters.put("city", (ims.getCity() == null) ? "" : ims.getCity());
                parameters.put("pin", (ims.getPin() == null) ? "" : ims.getPin());
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
                parameters.put("registrationcode", "");
                parameters.put("branchcode", "");
                parameters.put("programcode", "");
                parameters.put("state", "");
                parameters.put("city", "");
                parameters.put("pin", "");
                parameters.put("image", "");
            }
            path = request.getRealPath("/jrxml/StudentSubjectRegistrationDetailsReport.jrxml");
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "Student Subject Registration Details Report");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "Student Subject Registration Details Report");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "Student Subject Registration Details Report");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "Student Subject Registration Details Report");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getStudentSubjectRegistrationDetailsReportData(String instituteid, String programid, String branchid, String registrationid, String orderby, String sortedby) {
        List retList = new ArrayList();
        List newlist = new ArrayList();
        try {
            String course = "";
            String session1 = "";
            String session2 = "";
            List<Object[]> list = daoFactory.getStudentSubjectChoiceMasterDAO().getStudentSubjectRegistrationDetailsReportData(instituteid, programid, branchid, registrationid, orderby, sortedby);
            List indexlist1 = new ArrayList();
            indexlist1.add(5);
            newlist = daoFactory.getUtilDAO().dateConverterList(list, indexlist1, "MMMM");
            List indexlist2 = new ArrayList();
            indexlist2.add(6);
            newlist = daoFactory.getUtilDAO().dateConverterList(newlist, indexlist2, "dd yyyy");
            for (int i = 0; i < newlist.size(); i++) {
                Object[] obj = (Object[]) newlist.get(i);
                Map map = new HashMap();
                map.put("enrollmentno", obj[0] == null ? "" : obj[0]);
                map.put("studentname", obj[1] == null ? "" : obj[1]);
                course = (obj[2] == null ? "" : obj[2].toString()) + "-" + (obj[3] == null ? "" : obj[3].toString());
                map.put("course", course.equals("-") ? "" : course);
                map.put("subject", obj[4] == null ? "" : obj[4]);
                session1 = (obj[5] == null ? "" : obj[5].toString());
                session2 = (obj[6] == null ? "" : obj[6].toString());
                map.put("session", session1.equals(session2) ? session1 : (session1 + "-" + session2));
                map.put("subjectarea", obj[8] == null ? "" : obj[8].toString());
                retList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map retMap = new HashMap();
        retMap.put("list", retList);
        return retMap;
    }

}
