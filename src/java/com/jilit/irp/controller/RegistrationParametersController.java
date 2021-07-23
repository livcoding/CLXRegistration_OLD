/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.RegistrationParametersIService;
import com.jilit.irp.data.common.CommonComboIservice;
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
 * @author Malkeet Singh
 */
@Controller
@RequestMapping("/registrationParameters")
public class RegistrationParametersController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    RegistrationParametersIService service;

    @RequestMapping("/list")
    public String getRegistrationParametersList(ModelMap model, HttpServletRequest req) {
        return "registrationParameters/registrationParametersList";
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getRegistrationParametersGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/edit")
    public String getRegistrationParametersEditData(ModelMap model, HttpServletRequest req) {
        service.getRegistrationParametersEditData(model, req);
        return "registrationParameters/registrationParametersEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationParameters(HttpServletRequest request) {
        List l = service.updateRegistrationParameters(request);
        return l.get(0).toString();
    }
}
