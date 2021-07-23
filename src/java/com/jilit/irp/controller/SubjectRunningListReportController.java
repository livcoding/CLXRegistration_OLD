/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectRunningListReportIService;
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
@RequestMapping("/subjectRunningListReport")
public class SubjectRunningListReportController {

    @Autowired
    SubjectRunningListReportIService service;

    @RequestMapping("/list")
    public String listsubjectRunningList(Model model) {
        service.getRegistrationList(model);
        return "subjectRunningListReport/subjectRunningListReport";
    }

    @RequestMapping("/subjectRunningListReport")
    public void getsubjectRunningListReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }

    @RequestMapping("/getSubjectType")
    @ResponseBody
    public Map getSubjectType(HttpServletRequest req) {
        List list = service.getSubjectType(req);
        Map map = new HashMap();
        map.put("subjecttype", list);
        return map;
    }

    @RequestMapping("/getStyNumber")
    @ResponseBody
    public Map getStyNumber(HttpServletRequest req) {
        List list = service.getStyNumber(req);
        Map map = new HashMap();
        map.put("stynumber", list);
        return map;
    }

    @RequestMapping("/getDepartment")
    @ResponseBody
    public Map getDepartment(HttpServletRequest req) {
        List list = service.getDepartment(req);
        Map map = new HashMap();
        map.put("department", list);
        return map;
    }

}
