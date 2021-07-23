package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectRunningListReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.persistence.dto.SubjectTypeMaster;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
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
public class SubjectRunningListReportService extends ReportManager implements SubjectRunningListReportIService, ServletContextAware {

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
    public void getRegistrationList(Model model) {
        List<RegistrationMaster> regList = null;
        List<SubjectTypeMaster> subjectTypeList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            model.addAttribute("regList", regList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getSubjectType(HttpServletRequest req) {
        List subjecttypelist = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = req.getParameter("registrationid").split("~@~")[0];
            subjecttypelist = daoFactory.getProgramSubjectTaggingDAO().getSubjectType(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjecttypelist;
    }

    @Override
    public List getStyNumber(HttpServletRequest req) {
        List stynumberlist = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = req.getParameter("registrationid").split("~@~")[0];
            String subjecttypeid = req.getParameter("subjecttypeid").split("~@~")[0];
            stynumberlist = daoFactory.getProgramSubjectTaggingDAO().getStyNumber(instituteid, registrationid, subjecttypeid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stynumberlist;
    }

    @Override
    public List getDepartment(HttpServletRequest req) {
        List semester = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = req.getParameter("registrationid").split("~@~")[0];
            String subjecttypeid = req.getParameter("subjecttypeid").split("~@~")[0];
            String stynumber = req.getParameter("stynumber");
            semester = daoFactory.getProgramSubjectTaggingDAO().getDepartment(instituteid, registrationid, subjecttypeid, stynumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semester;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        BusinessService bs = new BusinessService(context, daoFactory);
        String username = jirpsession.getJsessionInfo().getUsername();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String exportto = request.getParameter("radioValue");
        String regId[] = request.getParameter("regId").split("~@~");
        String subjectType[] = request.getParameter("subjectType").split("~@~");
        Short stynumber = Short.decode(request.getParameter("semester"));
        String department[] = request.getParameter("department").split("~@~");
        List retList = new ArrayList();
        String path = null;
        HashMap parameters = null;
        Map map = null;
        String subjectid = "";
        String subjectid1 = "";
        try {
            List<Object[]> listView = (List<Object[]>) daoFactory.getSubjectMasterDAO().getDataForLTPPassingMarks(regId[0], instituteid, subjectType[0], department[0], stynumber);
            for (int ii = 0; ii < listView.size(); ii++) {
                Object[] obj = listView.get(ii);
                subjectid = (String) obj[0];
                if (!subjectid.equals(subjectid1)) {
                    map = new HashMap();
                    map.put("subjectcode", obj[2]);
                    map.put("subjectdesc", obj[3]);
                    retList.add(map);
                    subjectid1 = (String) obj[0];
                }
                if (obj[4].equals("L")) {
                    map.put("Lpassingmarks", obj[6]);
                    map.put("Tpassingmarks", BigDecimal.ZERO);
                    map.put("Ppassingmarks", BigDecimal.ZERO);
                } else if (obj[4].equals("T")) {
                    map.put("Tpassingmarks", obj[6]);
                    map.put("Lpassingmarks",BigDecimal.ZERO);
                    map.put("Ppassingmarks", BigDecimal.ZERO);
                } else {
                    map.put("Ppassingmarks", obj[6]);
                    map.put("Lpassingmarks",BigDecimal.ZERO);
                    map.put("Tpassingmarks", BigDecimal.ZERO);
                }
            }
            path = request.getRealPath("/jrxml/SubjectRunningList.jrxml");
            parameters = new HashMap();
            parameters.put("registrationcode", regId[1]);
            parameters.put("subjecttypedesc", subjectType[1]);
            String semestercode = "";
            if (stynumber == 1) {
                semestercode = " First";
            } else if (stynumber == 2) {
                semestercode = " Second";
            }else if (stynumber == 3) {
                semestercode = " Third";
            }else if (stynumber == 4) {
                semestercode = " Fourth";
            }else if (stynumber == 5) {
                semestercode = " Fifth";
            }else if (stynumber == 6) {
                semestercode = " Sixth";
            }else if (stynumber == 7) {
                semestercode = " Seventh";
            }else if (stynumber == 8) {
                semestercode = " Eighth";
            }
            parameters.put("stydesc", semestercode);
            parameters.put("stytypenumber", stynumber);
            parameters.put("departmentcode", department[1]);
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

            if (exportto.contains("P")) {
                renderReport(PDF, path, retList, response, parameters, "SubjectRunningListReportService");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, retList, response, parameters, "SubjectRunningListReportService");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, retList, response, parameters, "SubjectRunningListReportService");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, retList, response, parameters, "SubjectRunningListReportService");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
