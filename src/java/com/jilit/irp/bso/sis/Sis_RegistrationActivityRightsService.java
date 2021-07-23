package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.Sis_RegistrationActivityRightsIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityRights;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityRightsId;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Service
public class Sis_RegistrationActivityRightsService implements Sis_RegistrationActivityRightsIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    /**
     * @description This method is used for get Grid dara from
     * SIS_REGISTRATIONACTIVITYRIGHTS table.
     * @param model
     */
    public void getListSis_RegistrationActivityRights(ModelMap model) {
        List sraList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            sraList = (List<Object[]>) daoFactory.getSis_RegistrationActivityRightsDAO().getAllSis_RegistrationActivityRights(instituteid);
            model.addAttribute("sraList", sraList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllActivityCombo(ModelMap model) {
        List activityList = null;
        List staffList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            activityList = (List<Object[]>) daoFactory.getSis_RegistrationActivityRightsDAO().getRegistrationActivityData(instituteid); // For Activity Code Combo
            staffList = (List<Object[]>) daoFactory.getSis_RegistrationActivityRightsDAO().getAllStaffCode(instituteid); // For Activity By Combo
            model.addAttribute("activityList", activityList);
            model.addAttribute("staffList", staffList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for the get edit grid record from
     * SIS_REGISTRATIONACTIVITYRIGHTS table.
     * @param request
     * @param model
     */
    public void getEditis_RegistrationActivityRights(HttpServletRequest request, ModelMap model) {
        List list = null;
        try {
            String ids[] = request.getParameter("pk").toString().split("~@~");
            String instituteid = ids[0];
            String activityid = ids[1];
            String staffid = ids[2];
            String stafftype = ids[3];
            list = (List<Object[]>) daoFactory.getSis_RegistrationActivityRightsDAO().getEditDataSis_RegistrationActivityRights(instituteid, activityid, staffid, stafftype);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for the save data into
     * SIS_REGISTRATIONACTIVITYRIGHTS table.
     * @param request
     * @return Success or Error message.
     */
    public List getSaveSis_RegistrationActivityRights(HttpServletRequest request) {
        List list = null;
        Sis_RegistrationActivityRights dto = null;
        Sis_RegistrationActivityRightsId id = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dto = new Sis_RegistrationActivityRights();
            id = new Sis_RegistrationActivityRightsId();
            String active[] = request.getParameter("activityBy").toString().split("~@~");
            id.setActivityid(request.getParameter("activityName"));
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setStaffid(active[0].toString());
            id.setStafftype(active[1].toString());
            dto.setId(id);
            dto.setFromdate(sdf.parse(request.getParameter("fromDate")));
            if (!"".equals(request.getParameter("toDate"))) {
                dto.setTilldate(sdf.parse(request.getParameter("toDate")));
            } else {
                dto.setTilldate(null);
            }
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = new ArrayList<String>();
            list = daoFactory.getSis_RegistrationActivityRightsDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getSis_RegistrationActivityRightsDAO().add(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This method is used for the update record in
     * SIS_REGISTRATIONACTIVITYRIGHTS table.
     * @param request
     * @return Success or Error message.
     */
    public List getUpdateSis_RegistrationActivityRights(HttpServletRequest request) {
        List list = null;
        Sis_RegistrationActivityRights dto = null;
        Sis_RegistrationActivityRightsId id = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dto = new Sis_RegistrationActivityRights();
            id = new Sis_RegistrationActivityRightsId();
            id.setInstituteid(request.getParameter("instituteId"));
            id.setActivityid(request.getParameter("activityId"));
            id.setStaffid(request.getParameter("staffId"));
            id.setStafftype(request.getParameter("staffType"));
            dto = (Sis_RegistrationActivityRights) daoFactory.getSis_RegistrationActivityRightsDAO().findByPrimaryKey(id);
            dto.setFromdate(sdf.parse(request.getParameter("fromDate")));
            if (!"".equals(request.getParameter("toDate"))) {
                dto.setTilldate(sdf.parse(request.getParameter("toDate")));
            } else {
                dto.setTilldate(null);
            }
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            daoFactory.getSis_RegistrationActivityRightsDAO().update(dto);
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This method is used for the delete record from
     * SIS_REGISTRATIONACTIVITYRIGHTS table.
     * @param request
     * @return Success or Error message.
     */
    public List getDeleteSis_RegistrationActivityRights(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String pks[] = request.getParameter("ids").toString().split(",");
            for (i = 0; i < pks.length; i++) {
                String[] ids = pks[i].split("~@~");
                String instId = ids[0];
                String actId = ids[1];
                String staffId = ids[2];
                String StaffType = ids[3];
                daoFactory.getSis_RegistrationActivityRightsDAO().delete(daoFactory.getSis_RegistrationActivityRightsDAO().findByPrimaryKey(new Sis_RegistrationActivityRightsId(instId, actId, staffId, StaffType)));
                list = new ArrayList<String>();
                list.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }
}
