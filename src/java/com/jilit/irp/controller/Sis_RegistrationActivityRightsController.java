package com.jilit.irp.controller;

import com.jilit.irp.iservice.Sis_RegistrationActivityRightsIService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/registrationActivityGrants")
public class Sis_RegistrationActivityRightsController {

    @Autowired
    Sis_RegistrationActivityRightsIService service;

    @RequestMapping("/list")
    public String listSis_RegistrationActivityRights(ModelMap model) {
        service.getListSis_RegistrationActivityRights(model);
        return "registrationActivityGrants/registrationActivityGrantsList";
    }

    @RequestMapping("/add")
    public String addSis_RegistrationActivityRights(ModelMap model) {
        service.getAllActivityCombo(model);
        return "registrationActivityGrants/registrationActivityGrantsAdd";
    }

    @RequestMapping("edit")
    public String addSis_RegistrationActivityRights(HttpServletRequest request, ModelMap model) {
        service.getEditis_RegistrationActivityRights(request, model);
        return "registrationActivityGrants/registrationActivityGrantsEdit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getSaveSis_RegistrationActivityRights(request);
        return l.get(0).toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getUpdateSis_RegistrationActivityRights(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getDeleteSis_RegistrationActivityRights(request);
        return l.get(0).toString();
    }
}
