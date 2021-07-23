/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.TeachingSchemeReportRegistrationWiseIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/teachingSchemeReportRegistrationWise")
public class TeachingSchemeReportRegistrationWiseController {

    @Autowired
    TeachingSchemeReportRegistrationWiseIService iservice;

    @RequestMapping("/list")
    public String getSubjectWiseList(Model model) {
        iservice.getAcadmicYearData(model);
        iservice.getSemesterdata(model);
        return "teachingSchemeReportRegistrationWise/teachingSchemeReportRegistrationWiseList";
    }

    @RequestMapping("/getProgramData")
    @ResponseBody
    public Map getProgramData(HttpServletRequest request) {
        List l = iservice.getProgramData(request);
        Map map = new HashMap();
        map.put("prog_data", l);
        return map;
    }
    @RequestMapping("/getSemData")
    @ResponseBody
    public Map getSemData(HttpServletRequest request) {
        List l = iservice.getSemData(request);
        Map map = new HashMap();
        map.put("sem_data", l);
        return map;
    }
    @RequestMapping("/getSectionData")
    @ResponseBody
    public Map getSectionData(HttpServletRequest request) {
        List l = iservice.getSectionData(request);
        Map map = new HashMap();
        map.put("sec_data", l);
        return map;
    }

      @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        iservice.getReport(request, response);
    }
    
}
