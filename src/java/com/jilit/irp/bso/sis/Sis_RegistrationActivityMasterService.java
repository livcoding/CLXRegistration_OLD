package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.Sis_RegistrationActivityMasterIService;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMaster;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.Date;
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
public class Sis_RegistrationActivityMasterService implements Sis_RegistrationActivityMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    /**
     * @description This method is used for get Grid data from
     * Sis_registrationactivitymaster table.
     * @param model
     */
    public void getListSis_RegistrationActivityMaster(ModelMap model) {
        List list = null;
        try {
            list = new ArrayList();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getSis_RegistrationActivityMasterDAO().getAllDataSis_RegistrationActivityMaster(instituteid);
            model.addAttribute("regList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getFeeHeadList(HttpServletRequest request, ModelMap model) { 
        List list = null;
        try {
            list = new ArrayList();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getSis_RegistrationActivityMasterDAO().getFeeHeadList(instituteid);
            model.addAttribute("feeHead", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for edit record from the
     * Sis_registrationactivitymaster table.
     * @param request
     * @param model
     * @return model
     */
    public ModelMap getEditSis_RegistrationActivityMaster(HttpServletRequest request, ModelMap model) {
        Sis_RegistrationActivityMaster dto = null;
        Sis_RegistrationActivityMasterId id = null;
        try {
            dto = new Sis_RegistrationActivityMaster();
            id = new Sis_RegistrationActivityMasterId();
            String pk = request.getParameter("pk");
            id.setActivityid(pk);
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            dto = (Sis_RegistrationActivityMaster) daoFactory.getSis_RegistrationActivityMasterDAO().findByPrimaryKey(id);
            model.addAttribute("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * @description This method is used for add record in
     * Sis_registrationactivitymaster table.
     * @param request
     * @return success or Error message.
     */
    public List getSaveSis_RegistrationActivityMaster(HttpServletRequest request) {
        List list = null;
        Sis_RegistrationActivityMaster dto = null;
        Sis_RegistrationActivityMasterId id = null;
        BusinessService businessService = null;
        try {
            list = new ArrayList<String>();
            dto = new Sis_RegistrationActivityMaster();
            id = new Sis_RegistrationActivityMasterId();
            businessService = new BusinessService(daoFactory);
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            dto.setId(id);
            dto.setFeeheadid(request.getParameter("feeHead"));
            dto.setActivityname(request.getParameter("activityName") == null ? "" : request.getParameter("activityName").toString().trim());
            dto.setActivityentryby(jirpsession.getJsessionInfo().getUsername());
            dto.setActivityentrydate(new Date());
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = daoFactory.getSis_RegistrationActivityMasterDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                businessService.rollback();
                 list.add("Error");
                return list;
            }
            id.setActivityid(businessService.generateId("ActivityID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            dto.setId(id);
            businessService.insertInIdGenerationControl(dto);
            list.add("Success");
        } catch (Exception e) {
             businessService.rollback();
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        finally
        {
             businessService.closeSession();
        }
        return list;
    }

    /**
     * @description This method is used for update record
     * Sis_registrationactivitymaster table.
     * @param request
     * @return Success or Error message.
     */
    public List getUpdateSis_RegistrationActivityMaster(HttpServletRequest request) {
        List list = null;
        Sis_RegistrationActivityMaster dto = null;
        Sis_RegistrationActivityMasterId id = null;
         BusinessService businessService = null;
        try {
            businessService = new BusinessService(daoFactory);
            dto = new Sis_RegistrationActivityMaster();
            id = new Sis_RegistrationActivityMasterId();
            list = new ArrayList<String>();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setActivityid(request.getParameter("activityId"));
            dto = (Sis_RegistrationActivityMaster) daoFactory.getSis_RegistrationActivityMasterDAO().findByPrimaryKey(id);
            dto.setFeeheadid(request.getParameter("feeHead"));
            if (dto != null) {
                dto.setActivityname(request.getParameter("activityName") == null ? "" : request.getParameter("activityName").toString().trim());
                dto.setActivityupdateby(jirpsession.getJsessionInfo().getUsername());
                dto.setActivityupdatedate(new Date());
                dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
                 list = daoFactory.getSis_RegistrationActivityMasterDAO().doValidate(dto, "update");
                if (list.size() > 0) {
                businessService.rollback();
                list.add("Error");
                return list;
            }
                daoFactory.getSis_RegistrationActivityMasterDAO().update(dto);
            }
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        finally
        {
             businessService.closeSession();
        }
        return list;
    }

    /**
     * @description This method is used for delete record from
     * Sis_registrationactivitymaster table.
     * @param request
     * @return Success or Error message.
     */
    public List getDeleteSis_RegistrationActivityMaster(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String instId = jirpsession.getJsessionInfo().getSelectedinstituteid();
                String id = pks[i];
                daoFactory.getSis_RegistrationActivityMasterDAO().delete(daoFactory.getSis_RegistrationActivityMasterDAO().findByPrimaryKey(new Sis_RegistrationActivityMasterId(instId, id)));
            }
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
     * @description This method is used for check child exist data in
     * Sis_registrationactivitymaster table.
     * @param request
     * @return True or False.
     */
    public String checkIfChildExist(HttpServletRequest request) {
        String pk = request.getParameter("pk");
        Sis_RegistrationActivityMasterId id = new Sis_RegistrationActivityMasterId();
        id.setActivityid(pk);
        id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
        String status = (daoFactory.getSis_RegistrationActivityMasterDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        return status;
    }
}
