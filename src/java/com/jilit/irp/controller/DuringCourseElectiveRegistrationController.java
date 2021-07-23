/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.DuringCourseElectiveRegistrationIServices;
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
@RequestMapping("/duringCourseElectiveRegistration")
public class DuringCourseElectiveRegistrationController {

    @Autowired
    CommonComboIservice commonService; 

    @Autowired
    DuringCourseElectiveRegistrationIServices service;

    @RequestMapping("/list")
    public String getDuringCourseElectiveRegistrationList(ModelMap model, HttpServletRequest req) {
        service.getProgramCode(model);
        return "duringCourseElectiveRegistration/duringCourseElectiveRegistrationList";
    }

    @RequestMapping("/gerGridData")
    @ResponseBody
    public Map gerGridData(ModelMap model, HttpServletRequest req) {
        service.getGridData(model, req);
        Map m = new HashMap();
        m.put("gridData", model.get("gridData"));
        return m;
    }

    @RequestMapping("/add")
    public String getDuringCourseRegistrationAdd(ModelMap model) {
        service.creditSubjectType(model);
        service.getProgramCode(model);
        return "duringCourseElectiveRegistration/duringCourseElectiveRegistrationAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveDuringCourseRegistration(ModelMap model, HttpServletRequest req) {
        List l = service.saveDuringCourseElectiveRegistration(req);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String editDuringCourseRegistration(ModelMap model, HttpServletRequest req) {
        service.creditSubjectType(model);
        service.getProgramCode(model);
        service.editDuringCourseElectiveRegistration(req, model);
        return "duringCourseElectiveRegistration/duringCourseElectiveRegistrationEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateDuringCourseRegistration(ModelMap model, HttpServletRequest req) {
        List l = service.updateDuringCourseElectiveRegistration(req);
        return l.get(0).toString();
    }
}
