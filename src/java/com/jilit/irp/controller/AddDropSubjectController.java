/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.AddDropSubjectIService;
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
 * @author Ashutosh1.kumar
 */
@Controller
@RequestMapping("/addDropSubject")
public class AddDropSubjectController {

    @Autowired
    CommonComboIservice commonComboIservice;
    @Autowired
    AddDropSubjectIService addDropSubjectIService;

    @RequestMapping("/list")
    public String getRegistrationCode(ModelMap model) {
        addDropSubjectIService.getInstituteCodeForAddDrop(model);
        return "addDropSubjects/addDropSubjects";
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List l = addDropSubjectIService.getSemesterCode(request);
        Map map = new HashMap();
        map.put("semestercodelist", l);
        return map;
    }

    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List l = addDropSubjectIService.getAcademicYear(request);
        Map map = new HashMap();
        map.put("acadyearList", l);
        return map;
    }

    @RequestMapping("/getProgram")
    @ResponseBody
    public Map getProgram(HttpServletRequest request) {
        List l = addDropSubjectIService.getProgram(request);
        Map map = new HashMap();
        map.put("programList", l);
        return map;
    }

    @RequestMapping("/getStudentDetail")
    @ResponseBody
    public Map getStudentDetail(HttpServletRequest request) {
        List l = addDropSubjectIService.getStudentDetail(request);
        Map map = new HashMap();
        map.put("studentList", l);
        return map;
    }

    @RequestMapping("/getStudetnInfo")
    @ResponseBody
    public Map getStudetnInfo(HttpServletRequest request) {
        List l = addDropSubjectIService.getStudetnInfo(request);
        Map map = new HashMap();
        map.put("studentInfo", l);
        return map;
    }

    @RequestMapping("/getActivity")
    @ResponseBody
    public Map getActivity(HttpServletRequest request) {
        Map l = addDropSubjectIService.getActivity(request);
        return l;
    }

    @RequestMapping("/getAddDropSubject")
    @ResponseBody
    public Map getAddDropSubject(HttpServletRequest request) {
        Map l = addDropSubjectIService.getAddDropSubject(request);
        return l;
    }

    @RequestMapping("/getGIPSubject")
    @ResponseBody
    public Map getGIPSubject(HttpServletRequest request) {
        Map l = addDropSubjectIService.getGIPSubject(request);
        return l;
    }

    @RequestMapping("/datacheckfordelete")
    @ResponseBody
    public Map datacheckfordelete(HttpServletRequest request) {
        String str = addDropSubjectIService.datacheckfordelete(request);
        Map map = new HashMap();
        map.put("status", str);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        List l = addDropSubjectIService.delete(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/saveAdd_BackSubject")
    @ResponseBody
    public Map saveAdd_BackSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.AddCurrent_BackSubject(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/addGipSubject")
    @ResponseBody
    public Map addGipSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.addGipSubject(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("auditSubject")
    @ResponseBody
    public Map auditSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.auditSubject(request);
        Map map = new HashMap();
        map.put("auditSubject", l);
        return map;
    }

    @RequestMapping("/addAuditSubject")
    @ResponseBody
    public Map saveAuditSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.saveAuditSubject(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/getParameterForAuditSubjectCredit")
    @ResponseBody
    public List getParameterForAuditSubjectCredit(HttpServletRequest request) {
        List data = addDropSubjectIService.getParameterForAuditSubjectCredit(request);
        return data;
    }

    @RequestMapping("/regConfermation")
    @ResponseBody
    public Map updateRegAllow(HttpServletRequest request) {
        List l = addDropSubjectIService.updateRegAllow(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/getElectiveSubject")
    @ResponseBody
    public Map getElectiveSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.getElectiveSubjectForSwap(request);
        Map map = new HashMap();
        map.put("electiveSubjectList", l);
        return map;
    }

    @RequestMapping("/changeElectiveSubject")
    @ResponseBody
    public Map changeElectiveSubject(HttpServletRequest request) {
        List l = addDropSubjectIService.changeElectiveSubject(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    @RequestMapping("/addDropSubjectReport")
    public void getbackPaperReport(HttpServletRequest request, HttpServletResponse response) {
        addDropSubjectIService.getReport(request, response);
    }
}
