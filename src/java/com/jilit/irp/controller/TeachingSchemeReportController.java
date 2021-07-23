/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;


import com.jilit.irp.iservice.TeachingSchemeReportIservice;
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
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/teachingSchemeReport")
public class TeachingSchemeReportController {

    @Autowired
    TeachingSchemeReportIservice teachingSchemeIservice;

    @RequestMapping("/list")
    public String getTeachingSchemeList(Model model) {
        teachingSchemeIservice.getFormData(model);
        return "teachingSchemeReport/teachingSchemeReportList";
    }

    @RequestMapping("/getStyNumber")
    @ResponseBody
    public Map getSemData(HttpServletRequest request) {
        List l = teachingSchemeIservice.getStyNumber(request);
        Map map = new HashMap();
        map.put("sem_data", l);
        return map;
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest request) {
        List l = teachingSchemeIservice.getBranch(request);
        Map map = new HashMap();
        map.put("branch", l);
        return map;
    }

    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest request) {
        List l = teachingSchemeIservice.getSection(request);
        Map map = new HashMap();
        map.put("section", l);
        return map;
    }

    @RequestMapping("/getReport")
    public void getTeachingSchemeReport(HttpServletRequest request, HttpServletResponse response) {
        teachingSchemeIservice.getReport(request, response);
    }

}
