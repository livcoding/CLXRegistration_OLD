/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.ElectiveSubjectAllocationStatusIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
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
public class ElectiveSubjectAllocationStatusService extends ReportManager implements ElectiveSubjectAllocationStatusIService, ServletContextAware {

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
        List list = new ArrayList();
        try {
            String registrationid[] = req.getParameter("regId").split("~@~");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = daoFactory.getV_ProgramSubjectTaggingDAO().getV_ProgramSubjectTypeData(instituteid, registrationid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap parameters = null;
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String exportto = request.getParameter("radioValue");
            String regId[] = request.getParameter("regId").split("~@~");
            String subjectType[] = request.getParameter("subjectType").split("~@~");
            List<Object[]> list = (List<Object[]>) daoFactory.getV_ProgramSubjectTaggingDAO().getSubjectTypeReport(regId[0], subjectType[0], instituteid);
            List data = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                map.put("Sub_Code", list.get(i)[0].toString());
                map.put("Sub_Decs", list.get(i)[1]);
                map.put("Seats", new BigDecimal(Integer.parseInt(list.get(i)[2].toString())));
                map.put("noofstudent", list.get(i)[3] == null ? new BigDecimal(0) : new BigDecimal(Integer.parseInt(list.get(i)[3].toString())));
                map.put("Sub_Type_Desc", list.get(i)[4]);
                data.add(map);
            }
            String path = request.getRealPath("/jrxml/ElectiveAllocationStatus.jrxml");
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            parameters = new HashMap();
            if (ims != null) {
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", ims.getState() == null ? "" : ims.getState());
                parameters.put("regcode", regId[1] == null ? "" : regId[1]);
                parameters.put("subjecttype", subjectType[1] == null ? "" : subjectType[1]);
                parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                // Dont Delete This line image is currupt.
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
                renderReport(PDF, path, data, response, parameters, "ElectiveAllocationStatusService");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "ElectiveAllocationStatusService");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "ElectiveAllocationStatusService");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "ElectiveAllocationStatusService");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
