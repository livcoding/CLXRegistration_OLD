package com.jilit.irp.controller;

import com.jilit.irp.iservice.Sis_RegistrationActivityMasterIService;
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
@RequestMapping("/registrationActivityMaster")
public class sis_RegistrationActivityMasterController {

    @Autowired
    Sis_RegistrationActivityMasterIService service;

    @RequestMapping("/list")
    public String gridDatatRegistrationActivityMaster(ModelMap model) {
        service.getListSis_RegistrationActivityMaster(model);
        return "sis_RegistrationActivityMaster/sis_RegistrationActivityMasterList";
    }

    @RequestMapping("/add")
    public String addRegistrationActivityMaster(HttpServletRequest request, ModelMap model) {
        service.getFeeHeadList(request, model);
        return "sis_RegistrationActivityMaster/sis_RegistrationActivityMasterAdd";
    }

    @RequestMapping("/edit")
    public String editRegistrationActivityMaster(HttpServletRequest request, ModelMap model) {
        service.getFeeHeadList(request, model);
        service.getEditSis_RegistrationActivityMaster(request, model);
        return "sis_RegistrationActivityMaster/sis_RegistrationActivityMasterEdit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getSaveSis_RegistrationActivityMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getUpdateSis_RegistrationActivityMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return service.checkIfChildExist(request);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRegistrationActivityMaster(HttpServletRequest request) {
        List l = service.getDeleteSis_RegistrationActivityMaster(request);
        return l.get(0).toString();
    }
}
