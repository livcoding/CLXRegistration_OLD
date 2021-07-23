/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.BackPaperFeesService;
import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.RegistrationSubjectGroupIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author campus.trainee
 */
@Controller
@RequestMapping("/registrationSubjectGroup")
public class RegistrationSubjectGroupController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    RegistrationSubjectGroupIService service;

    @RequestMapping("/list")
    public String getRegistrationSubjectGroupList(ModelMap model, HttpServletRequest req) {
        service.getProgramCode(model);
        return "registrationSubjectGroup/registrationSubjectGroupList";
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getRegistrationSubjectGroupGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/add")
    public String getRegistrationSubjectGroupAdd(ModelMap model, HttpServletRequest req) {
        service.getProgramCode(model);
        return "registrationSubjectGroup/registrationSubjectGroupAdd";
    }

    @RequestMapping("/getGroupId")
    @ResponseBody
    public Map getGroupId(HttpServletRequest req) {
        List list = service.getGroupId(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("groupid", list);
        } else {
            map.put("groupid", null);
        }
        return map;
    }

    @RequestMapping("/getSubjects")
    @ResponseBody
    public Map getSectionCode(HttpServletRequest req) {
        List list = service.getSubjects(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("subjects", list);
        } else {
            map.put("subjects", null);
        }
        return map;
    }

    @RequestMapping("/getGroupedSubjects")
    @ResponseBody
    public Map getGroupedSubjects(HttpServletRequest req) {
        List list = service.getGroupedSubjects(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("groupedSubjects", list);
        } else {
            map.put("groupedSubjects", null);
        }
        return map;
    }
 
    @RequestMapping("/save")
    @ResponseBody
    public String saveRegistrationSubjectGroup(HttpServletRequest request) {
        List l = service.saveRegistrationSubjectGroup(request);
        return l.get(0).toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationSubjectGroup(HttpServletRequest request) {
        List l = service.updateRegistrationSubjectGroup(request);
        return l.get(0).toString();
    }
    
     @RequestMapping("/delete")
    @ResponseBody
    public String deleteRegistrationGroupedSubject(HttpServletRequest request) {
        List l = service.deleteRegistrationGroupedSubject(request);
        return l.get(0).toString();
    }
//
//    @RequestMapping("/edit")
//    public String getRegistrationSubjectGroupEditData(ModelMap model, HttpServletRequest req) {
//        service.getRegistrationSubjectGroupEditData(model, req);
//        return "creditWiseBackPaperFees/creditWiseBackPaperFeesEdit";
//    }
//    

//    @RequestMapping("/edit")
//    public String editsaveSubjectWiseBackPaperFees(ModelMap model, HttpServletRequest req) {
//        service.getSemesterCode(model, req); 
//        service.getDepartmentData(model, req);
//        service.getSubjectCode(model, req);
//        service.saveOrUpdateSubjectWise( model,req);
//        return "subjectWiseBackPaperFees/subjectWiseBackPaperFeesEdit";
//    }
}
