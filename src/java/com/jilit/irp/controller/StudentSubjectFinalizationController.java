/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.StudentSubjectFinalizationIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/studentSubjectFinalization")
public class StudentSubjectFinalizationController {

    @Autowired
    StudentSubjectFinalizationIservice stuSubFinalization;

    @RequestMapping("/list")
    public String getRegistrationMaster(Model model) {
        stuSubFinalization.getFormData(model);
        return "studentSubjectFinalization/studentSubjectFinalizationForm";
    }

    @RequestMapping("/getAcadyear")
    @ResponseBody
    public Map getAcadmicyear(HttpServletRequest request) {
        List err = null;
        err = stuSubFinalization.getAcadmicyear(request);
        Map map=new HashMap();
        map.put("acadyear",err);
        return map;
    }
    
    @RequestMapping("/getProgram")
    @ResponseBody
    public Map getProgram(HttpServletRequest request) {
        List err = null;
        err = stuSubFinalization.getProgram(request);
        Map map=new HashMap();
        map.put("program",err);
        return map;
    }
    
    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest request) {
        List err = null;
        err = stuSubFinalization.getBranch(request);
        Map map=new HashMap();
        map.put("branch",err);
        return map;
    }
    
    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest request) {
        List err = null;
        err = stuSubFinalization.getSection(request);
        Map map=new HashMap();
        map.put("section",err);
        return map; 
    }
    
    @RequestMapping("/getStudentSubjectFinalizationData")
    @ResponseBody
    public Map getStudentSubjectFinalizationData(HttpServletRequest request) {        
        Map map=new HashMap();
        map = stuSubFinalization.getStudentSubjectFinalizationData(request);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveStudentSubjectFinalizationData(HttpServletRequest request) {
        String err="";
        err = stuSubFinalization.doSave(request);
        return err;
    }
}
