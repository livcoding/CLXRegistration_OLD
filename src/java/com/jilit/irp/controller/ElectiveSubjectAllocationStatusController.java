/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.ElectiveSubjectAllocationStatusIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author deepak.gupta
 */

@Controller
@RequestMapping("/electiveSubjectAllocationStatus")
public class ElectiveSubjectAllocationStatusController {
    
    @Autowired
    ElectiveSubjectAllocationStatusIService service;
    
    @RequestMapping("/list")
    public String electiveSubjectAllocationStatusList(Model model) {
        service.getRegistrationList(model);
        return "electiveSubjectAllocationStatus/electiveSubjectAllocationStatus";
    }
    
    @RequestMapping("/getSubjectType")
    @ResponseBody
    public Map getBranch(HttpServletRequest req) {
        List list = service.getSubjectType(req);
        Map map = new HashMap();
        map.put("subjectTypeList", list);
        return map;
    }

    @RequestMapping("/electiveSubjectAllocationStatus")
    public void getElectiveSubjectAllocationStatusReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
    
}
