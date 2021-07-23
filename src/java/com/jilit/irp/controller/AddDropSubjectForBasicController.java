/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.AddDropSubjectForBasicIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/addDropBasicSubject")
public class AddDropSubjectForBasicController {

    @Autowired
    CommonComboIservice commonComboIservice;
    @Autowired
    AddDropSubjectForBasicIService addDropBasicSubjectIService;

    @RequestMapping("/list")
    public String getRegistrationCode(ModelMap model) {
        addDropBasicSubjectIService.getInstituteCodeForAddDrop(model);
        return "addDropSubjects/addDropSubjectsForBasic";
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getSemesterCode(request);
        Map map = new HashMap();
        map.put("semestercodelist", l);
        return map;
    }

    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getAcademicYear(request);
        Map map = new HashMap();
        map.put("acadyearList", l);
        return map;
    }

    @RequestMapping("/getProgram")
    @ResponseBody
    public Map getProgram(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getProgram(request);
        Map map = new HashMap();
        map.put("programList", l);
        return map;
    }

    @RequestMapping("/getStudentDetail")
    @ResponseBody
    public Map getStudentDetail(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getStudentDetail(request);
        Map map = new HashMap();
        map.put("studentList", l);
        return map;
    }

    @RequestMapping("/getStudetnInfo")
    @ResponseBody
    public Map getStudetnInfo(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getStudetnInfo(request);
        Map map = new HashMap();
        map.put("studentInfo", l);
        return map;
    }

    @RequestMapping("/assignData")
    @ResponseBody
    public Map assignData(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.assignData(request);
        Map map = new HashMap();
        map.put("assignedData", l);
        return map;
    }

    @RequestMapping("/getFaculty")
    @ResponseBody
    public Map getFaculty(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.getFacultyList(request);
        Map map = new HashMap();
        map.put("facultyList", l.get(0));
        map.put("ComponentList", l.get(1));
        return map;
    }

    @RequestMapping("/getActivity")
    @ResponseBody
    public Map getActivity(HttpServletRequest request) {
        Map l = addDropBasicSubjectIService.getActivity(request);
        return l;
    }

    @RequestMapping("/getAddDropSubject")
    @ResponseBody
    public Map getAddDropSubject(HttpServletRequest request) {
        Map l = addDropBasicSubjectIService.getAddDropSubject(request);
        return l;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.delete(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/saveAdd_BackSubject")
    @ResponseBody
    public Map saveAdd_BackSubject(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.AddCurrent_BackSubject(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/regConfermation")
    @ResponseBody
    public Map updateRegAllow(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.updateRegAllow(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/saveFacultyLoad")
    @ResponseBody
    public Map saveFacultyLoad(HttpServletRequest request) {
        List l = addDropBasicSubjectIService.saveLoadDistribution(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }
}
