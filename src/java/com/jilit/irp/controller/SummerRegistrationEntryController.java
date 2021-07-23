/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SummerRegistrationEntryIService;
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
 * @author priyanka.tyagi
 */
@Controller
@RequestMapping("/summerRegistrationEntry")
public class SummerRegistrationEntryController {

    @Autowired
    SummerRegistrationEntryIService service;

    @RequestMapping("/list")
    public String getSummerRegistrationEntryList(ModelMap model) {
        service.getInstituteCode(model);
        return "summerRegistrationEntry/summerRegistrationEntryList";
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List l = service.getSemesterCode(request);
        Map map = new HashMap();
        map.put("semestercodelist", l);
        return map;
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
        service.getInstituteCode(model);
        return "summerRegistrationEntry/summerRegistrationEntryAdd";
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
    public String saveSummerSubjects(HttpServletRequest request) {
        List l = service.saveSummerSubjects(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSummerSubjects(HttpServletRequest request) {
        List l = service.deleteSummerSubjects(request);
        return l.get(0).toString();
    }
}
