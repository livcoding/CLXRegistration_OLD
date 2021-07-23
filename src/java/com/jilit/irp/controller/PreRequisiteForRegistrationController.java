package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.PreRequisiteForRegistrationIService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/preRequisiteForRegistration")
public class PreRequisiteForRegistrationController {

    @Autowired
    PreRequisiteForRegistrationIService service;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getListPreRequisiteForRegistration(ModelMap model) {
        commonService.getAcademicYearDeactive(model);
        return "preRequisiteForRegistration/preRequisiteForRegistrationList";
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridDataPreRequisiteForRegistration(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = new HashMap();
        try {
            list = service.getAllPreRequisiteForRegistration(request);
            map.put("gridData", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/add")
    public String getAddPreRequisiteForRegistration(ModelMap model) {
        commonService.getAcademicYearDeactive(model);
        commonService.getAllProgramMaster(model);
        service.getAllStyCode(model);
        return "preRequisiteForRegistration/preRequisiteForRegistrationAdd";
    }

    @RequestMapping("/edit")
    public String getEditPreRequisiteForRegistration(HttpServletRequest request, ModelMap model) {
        model = service.getEditPreRequisiteForRegistration(request, model);
        return "preRequisiteForRegistration/preRequisiteForRegistrationEdit";
    }

    @RequestMapping("/getBranchCode")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = new HashMap();
        try {
            list = service.getAllBranchCode(request);
            map.put("branchList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String savePreRequisiteForRegistration(HttpServletRequest request) {
        List l = service.getSavePreRequisiteForRegistration(request);
        return l.get(0).toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updatePreRequisiteForRegistration(HttpServletRequest request) {
        List l = service.getUpdatePreRequisiteForRegistration(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return service.checkIfChildExist(request);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deletePreRequisiteForRegistration(HttpServletRequest request) {
        List l = service.getDeletePreRequisiteForRegistration(request);
        return l.get(0).toString();
    }
}
