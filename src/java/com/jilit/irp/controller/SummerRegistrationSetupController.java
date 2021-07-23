/*
    Document   : SummerRegsitartionSetup
    Created on : 1 AUG : 2019 
    Author     : Malkeet Singh
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.SummerRegistrationSetupService;
import com.jilit.irp.iservice.SummerRegistrationSetupIService;
import com.jilit.irp.data.common.CommonComboIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Malkeet Singh
 */
@Controller
@RequestMapping("/summerRegistrationSetup")
public class SummerRegistrationSetupController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    SummerRegistrationSetupIService service;

    @RequestMapping("/list")
    public String getSummerRegistrationSetupList(ModelMap model, HttpServletRequest req) {
        service.getProgramCode(model);
        return "summerRegistrationSetup/summerRegistrationSetupList";
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getSummerRegistrationSetupGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/add")
    public String getSummerRegistrationSetupAdd(ModelMap model, HttpServletRequest req) {
        service.getProgramCode(model);
        return "summerRegistrationSetup/summerRegistrationSetupAdd";
    }

    @RequestMapping("/getStyNumber")
    @ResponseBody
    public Map getStyNumber(HttpServletRequest request) {
        List list = service.getStyNumber(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("stynumber", list);
        } else {
            map.put("stynumber", null);
        }
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveSummerRegistrationSetup(HttpServletRequest request) {
        List l = service.saveSummerRegistrationSetup(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String getSummerRegistrationSetupEdit(ModelMap model, HttpServletRequest req) {
        service.getSummerRegistrationSetupEdit(model, req);
        return "summerRegistrationSetup/summerRegistrationSetupEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateSummerRegistrationSetup(HttpServletRequest request) {
        List l = service.updateSummerRegistrationSetup(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSummerRegistrationSetup(HttpServletRequest request) {
        List l = service.deleteSummerRegistrationSetup(request);
        return l.get(0).toString();
    }
}
