package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.BackPaperReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.SubjectMaster;
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
 * @author deepak.gupta
 */
@Service
public class BackPaperReportService extends ReportManager implements BackPaperReportIService ,ServletContextAware{

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
    public void getProgramList(Model model) {
        List<ProgramMaster> progList = null;
        List<SubjectMaster> subList = null;
        List list1 = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            progList = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().findAll(instituteid);
            // subList = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().findAll(instituteid);
            List list = daoFactory.getStudentResultDAO().getAllStyNumber(instituteid);
            int stynumber = Integer.parseInt(list.get(0).toString());
            for (int i = 1; i <= stynumber; i++) {
                list1.add(i);
            }
            model.addAttribute("progList", progList);
            model.addAttribute("subList", subList);
            model.addAttribute("semester", list1);
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
    public List getSubject(HttpServletRequest req) {
        List subList = new ArrayList();
        try {
            String programId[] = req.getParameter("programId").split("~@~");
            String styno = req.getParameter("semester");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<SubjectMaster> list = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().getSubPerProgSty(instituteid, programId[0], styno);
            if (list != null && list.size() > 0) {
                subList.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subList;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programId[] = request.getParameter("programId").split("~@~");
            String branchid[] = request.getParameter("branch").split("~@~");
            String stynumber = request.getParameter("semester");
            String subjectId[] = request.getParameter("subCode").split("~@~");
            String sem = request.getParameter("semester");
            String exportto = request.getParameter("radioValue");
            String orderby = request.getParameter("orderBy");
            String sortedby = request.getParameter("sortBy");
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map map = null;
            map = getBackPaperReportData(instituteid, programId[0], branchid[0], stynumber, subjectId[0], orderby, sortedby);
            data = (List) map.get("list");
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            path = request.getRealPath("/jrxml/BackPaperReport.jrxml");
            parameters = new HashMap();
            if (ims != null || data != null) {
                parameters.put("institutename", ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", (ims.getState() == null) ? "" : ims.getState());
                parameters.put("city", (ims.getCity() == null) ? "" : ims.getCity());
                parameters.put("pin", (ims.getPin() == null) ? "" : ims.getPin());
                parameters.put("programcode", programId[1] == null ? "" : programId[1]);
                parameters.put("branchcode", branchid[1] == null ? "" : branchid[1]);
                parameters.put("sem", sem == null ? "" : sem);
                parameters.put("subjectcode", subjectId[1] == null ? "" : subjectId[1]);
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
                parameters.put("programcode", "");
                parameters.put("branchcode", "");
                parameters.put("sem", "");
                parameters.put("subjectcode", "");
            }

            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "Back Paper Report");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "Back Paper Report");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "Back Paper Report");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "Back Paper Report");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getBackPaperReportData(String instituteid, String programid, String branchid, String stynumber, String subjectid, String orderby, String sortedby) {
        List retList = new ArrayList();
        Map map1 = new HashMap();
        try {
            if (orderby.equals("E")) {
                orderby = "sm.enrollmentno " + sortedby;
            } else {
                orderby = "sm.name " + sortedby;
            }
            map1.put("instituteid", instituteid);
            map1.put("programid", programid);
            map1.put("branchid", branchid);
            map1.put("stynumber", stynumber);
            map1.put("subjectid", subjectid);
            map1.put("orderby", orderby);
//            List<Object[]> l = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("getBackPaperReportData_BPR", map1);
            List<Object[]> l = (List<Object[]>) daoFactory.getStudentMasterDAO().getBackPaperReportData_BPR(instituteid, programid, branchid, stynumber, subjectid, orderby);
            for (int i = 0; i < l.size(); i++) {
                Object[] obj = (Object[]) l.get(i);
                Map map = new HashMap();
                map.put("enrollmentno", obj[0] == null ? "" : obj[0]);
                map.put("studentname", obj[1] == null ? "" : obj[1]);
                map.put("programcode", obj[2] == null ? "" : obj[2]);
                map.put("branchcode", obj[3] == null ? "" : obj[3]);
                map.put("semester", obj[4] == null ? "" : obj[4]);
                map.put("subjectcode", obj[5] == null ? "" : obj[5]);
                map.put("subjectdesc", obj[6] == null ? "" : obj[6]);
                retList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map11 = new HashMap();
        map11.put("list", retList);
        return map11;
    }

}
