/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.RegistrationNoDuesManagementIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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
@RequestMapping("/registrationNoDuesManagement")
public class RegistrationNoDuesManagementController {

    @Autowired
    RegistrationNoDuesManagementIservice regNoDuesIservice;

    @RequestMapping("/list")
    public String getregistrationNoDuesManagementList(Model model) {
        regNoDuesIservice.getFormData(model);
        return "registrationNoDuesManagement/registrationNoDuesManagementList";
    }

    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.getAcademicYear(request);
        Map map = new HashMap();
        map.put("acadyear", err);
        return map;
    }

    @RequestMapping("/getProgramCode")
    @ResponseBody
    public Map getProgramCode(HttpServletRequest request) {
        List progList = new ArrayList();
        progList = regNoDuesIservice.getAllProgramCode(request);
        Map map = new HashMap();
        map.put("progList", progList);
        return map;
    }

    @RequestMapping("/getEnrollmentNo")
    @ResponseBody
    public Map getEnrollmentNo(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.getEnrollmentNo(request);
        Map map = new HashMap();
        map.put("studetail", err);
        return map;
    }

    @RequestMapping("/getStudentWiseNoDuesGridData")
    @ResponseBody
    public Map getStudentWiseNoDuesGridData(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.getStudentWiseNoDuesGridData(request);
        Map map = new HashMap();
        map.put("studentWiseData", err);
        return map;
    }

    @RequestMapping("/getAcademicYearWiseGridData")
    @ResponseBody
    public Map getAcademicYearWiseGridData(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.getEnrollmentNo(request);
        Map map = new HashMap();
        map.put("acadWiseData", err);
        return map;
    }

    @RequestMapping("/saveStudentWise")
    @ResponseBody
    public Map saveStudentWise(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.saveStudentWise(request);
        Map map = new HashMap();
        map.put("status", err.get(0).toString());
        return map;
    }

    @RequestMapping("/saveAcadWiseRecord")
    @ResponseBody
    public Map saveAcadWiseRecord(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.saveAcadWiseRecord(request);
        Map map = new HashMap();
        map.put("status", err.get(0).toString());
        return map;
    }

    @RequestMapping("/deleteNoDuesActivity")
    @ResponseBody
    public Map deleteNoDuesActivity(HttpServletRequest request) {
        List err = null;
        err = regNoDuesIservice.deleteNoDuesActivity(request);
        Map map = new HashMap();
        map.put("status", err.get(0).toString());
        return map;
    }

}
