/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.RegistrationNoDuesStatusReportIservice;
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
@RequestMapping("/registrationNoDuesStatusReport")
public class RegistrationNoDuesStatusReportController {
           
    @Autowired
    RegistrationNoDuesStatusReportIservice regNoDuesReportIservice;
    
    @RequestMapping("/list")
    public String getregistrationNoDuesManagementList(Model model) {
        regNoDuesReportIservice.getFormData(model);
        return "registrationNoDuesStatusReport/registrationNoDuesStatusReportList";
    }
    
    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List err = null;
        List program = null;
        err = regNoDuesReportIservice.getAcademicYear(request);
        program = regNoDuesReportIservice.getProgram(request);
        Map map = new HashMap();
        map.put("acadyear", err);
        map.put("program", program);
        return map;
    }
    
    @RequestMapping("/getProgram")
    @ResponseBody
    public Map getProgram(HttpServletRequest request) {
        List program = null;
        program = regNoDuesReportIservice.getProgram(request);
        Map map = new HashMap();
        map.put("program", program);
        return map;
    }
    
    @RequestMapping("/getStyNo")
    @ResponseBody
    public Map getStyNo(HttpServletRequest request) {
        List styno = null;
        styno = regNoDuesReportIservice.getStyNo(request);
        Map map = new HashMap();
        map.put("styno", styno);
        return map;
    }
        
    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        regNoDuesReportIservice.getReport(request, response);
    }
    
}
