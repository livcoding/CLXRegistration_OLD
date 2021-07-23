/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectComponentwiseStudentListIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.SectionMaster;
import com.jilit.irp.persistence.dto.SubjectComponent;
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
public class SubjectComponentwiseStudentListService extends ReportManager implements SubjectComponentwiseStudentListIService, ServletContextAware {

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
    public void getDataList(Model model) {
        List<RegistrationMaster> regList = null;
        List<SubjectMaster> subList = null;
        List<SubjectComponent> subCompList = null;
        List<SectionMaster> secList = null;

        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            subList = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().findAll(instituteid);
            subCompList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
            secList = (List<SectionMaster>) daoFactory.getSectionMasterDAO().findAll(instituteid);
            model.addAttribute("registList", regList);
            model.addAttribute("subjectList", subList);
            model.addAttribute("sectionList", secList);
            model.addAttribute("subComponentList", subCompList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getSubSectionList(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String sectionId = req.getParameter("sectionId").split("~@~")[0];
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = req.getParameter("regId").split("~@~")[0];
            List<Object[]> ccList = daoFactory.getSectionMasterDAO().getSubSectionListForSectionCode(instituteid, registrationid, sectionId);
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
            String exportto = request.getParameter("radioValue");
            String registrationid[] = request.getParameter("regId").split("~@~");
            String subjectid[] = request.getParameter("subject").split("~@~");
            String subjectcomponentid[] = request.getParameter("subjectcomponent").split("~@~");
            String sectionid[] = request.getParameter("section").split("~@~");
            String subsectionid[] = request.getParameter("subsection").split("~@~");
            String path = null;
            HashMap parameters = null;
            List<Object[]> data = null;
            List dataList = new ArrayList();
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            data = (List<Object[]>) daoFactory.getV_StudentComponentDetailDAO().getClassWiseStudentList(registrationid[0], sectionid[0], subjectcomponentid[1], subjectid[0], subsectionid[0], instituteid);
            for (int i = 0; i < data.size(); i++) {
                Map map = new HashMap();
                map.put("enrollmentno", data.get(i)[1]);
                map.put("name", data.get(i)[0]);
                if(!data.get(i)[7].toString().equals(instituteid))
                map.put("programcode", data.get(i)[9]+" ( "+data.get(i)[3]+ " )" + " * ");
                else{
                map.put("programcode", data.get(i)[9]);
                }
                dataList.add(map);
            }
            if (ims != null || data != null) {
                parameters = new HashMap();
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", ims.getState() == null ? "" : ims.getState());
                parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                parameters.put("registrationcode", registrationid[1]);
                parameters.put("sectioncode", sectionid[1]);
                parameters.put("subjectcode", subjectid[1]);
                parameters.put("subsectioncode", subsectionid[1]);
                if (subjectcomponentid[1].equals("L")) {
                    parameters.put("subjectcomponentcode", "Lecture");
                } else if (subjectcomponentid[1].equals("T")) {
                    parameters.put("subjectcomponentcode", "Tutorial");
                } else {
                    parameters.put("subjectcomponentcode", "Practical");
                }
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                parameters.put("image", context.getRealPath(ims.getLogofilename()));
                parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
            } else {
                parameters = new HashMap();
                parameters.put("registrationcode", "");
                parameters.put("sectioncode", "");
                parameters.put("subjectcomponentcode", "");
                parameters.put("image", "");
                parameters.put("subjectcode", "");
                parameters.put("subsectioncode", "");
                parameters.put("institutename", "");
            }
            path = request.getRealPath("/jrxml/ClassWiseStudentList.jrxml");
            if (exportto.contains("P")) {
                renderReport(PDF, path, dataList, response, parameters, "ClassWiseStudentListReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, dataList, response, parameters, "ClassWiseStudentListReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, dataList, response, parameters, "ClassWiseStudentListReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, dataList, response, parameters, "ClassWiseStudentListReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
