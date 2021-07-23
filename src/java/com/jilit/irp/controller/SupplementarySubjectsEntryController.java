/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SupplementarySubjectEntryIService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author malkeet.singh
 */
@Controller
@RequestMapping("/supplementarySubjectEntry")
public class SupplementarySubjectsEntryController {

    @Autowired
    SupplementarySubjectEntryIService service;

    @RequestMapping("/list")
    public String getSupplementarySubjectsEntryList(ModelMap model) {
        service.getSemesterCode(model);
        return "supplementarySubjectEntry/supplementarySubjectEntryList";
    }

    @RequestMapping("/getStudetnInfo")
    @ResponseBody
    public Map getStudetnInfo(HttpServletRequest request) {
        List l = service.getStudetnInfo(request);
        Map map = new HashMap();
        map.put("studentInfo", l);
        return map;
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List list = service.getGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/add")
    public String getSupplementarySubjectsEntryAdd(ModelMap model) {
        service.getSemesterCode(model);
        return "supplementarySubjectEntry/supplementarySubjectEntryAdd";
    }

    @RequestMapping("/getEnrollmentno")
    @ResponseBody
    public Map getEnrollmentno(HttpServletRequest request) {
        List list = service.getStudents(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("enrollmentno", list);
        } else {
            map.put("enrollmentno", null);
        }
        return map;
    }

    @RequestMapping("/getSubjects")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest request) { 
        List list = service.getSubjectCode(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("subjectCode", list);
        } else {
            map.put("subjectCode", null);
        }
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveSupplementarySubjects(HttpServletRequest request) {
        List l = service.saveSupplementarySubjects(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSupplementarySubjects(HttpServletRequest request) {
        List l = service.deleteSupplementarySubjects(request);
        return l.get(0).toString();
    }
}
