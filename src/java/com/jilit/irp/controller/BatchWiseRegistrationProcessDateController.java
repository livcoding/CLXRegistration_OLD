/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.BatchWiseRegistrationProcessDateIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/batchWiseRegistrationProcessDate")
public class BatchWiseRegistrationProcessDateController {
          
    @Autowired
    BatchWiseRegistrationProcessDateIservice batchwiseRegProcess;
    
    @RequestMapping("/list")
    public String getregistrationNoDuesManagementList(Model model) {
        batchwiseRegProcess.getFormData(model);
        return "batchWiseRegistrationProcessDate/batchWiseRegistrationProcessDateList";
    }
    
    @RequestMapping("/getPrAndAcadYear")
    @ResponseBody
    public Map getPrAndAcadYear(HttpServletRequest request) {
        List err = null;
        List program = null;
        err = batchwiseRegProcess.getAcademicYear(request);
        program = batchwiseRegProcess.getProgram(request);
        Map map = new HashMap();
        map.put("acadyear", err);
        map.put("program", program);
        return map;
    }
    
    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest request) {
        List err = null;
        err = batchwiseRegProcess.getBranch(request);
        Map map = new HashMap();
        map.put("branch", err);
        return map;
    }
    
    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List err = null;
        err = batchwiseRegProcess.getGridData(request);
        Map map = new HashMap();
        map.put("gridData", err);
        return map;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public String saveAcadWiseRegistration(HttpServletRequest request) {
        List err = null;
        err = batchwiseRegProcess.saveAcadWiseRegistration(request);
        return err.get(0).toString();
    }
    
}
