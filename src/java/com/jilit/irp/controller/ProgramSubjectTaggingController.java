/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.ProgramSubjectTaggingIService;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
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
 * @author deepak.gupta
 */
@Controller
@RequestMapping("programSubjectTagging")
public class ProgramSubjectTaggingController {

    @Autowired
    ProgramSubjectTaggingIService service;

    @Autowired
    JIRPSession jirpession;

    @RequestMapping("/list")
    public String listProgramSubjectTagging(Model model) {
        service.getProgramSubjectTaggingList(model);
        return "programSubjectTagging/programSubjectTaggingList";
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest req) {
        List list = service.getBranch(req);
        Map map = new HashMap();
        map.put("branch", list);
        return map;
    }

    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List l = service.getAcademicYear(request);
        Map map = new HashMap();
        map.put("acadYearList", l);
        return map;
    }

    @RequestMapping("/getSemester")
    @ResponseBody
    public Map getSemester(HttpServletRequest req) {
        List list = service.getSemester(req);
        Map map = new HashMap();
        map.put("semester", list);
        return map;
    }

    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest req) {
        List list = service.getSection(req);
        Map map = new HashMap();
        map.put("section", list);
        return map;
    }

    @RequestMapping("/getBasket")
    @ResponseBody
    public Map getBasket(HttpServletRequest req) {
        List list = service.getBasket(req);
        Map map = new HashMap();
        map.put("basket", list);
        return map;
    }

    @RequestMapping("/loadProgramTaggingList")
    @ResponseBody
    public Map loadProgramTaggingList(HttpServletRequest req) {
        List list = new ArrayList();
        list = service.loadProgramTaggingList(req);
        Map map = new HashMap();
        map.put("programTaggingList", list);
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map programSubjectTaggingAdd(Model model, HttpServletRequest req) {
        List list = new ArrayList();
        list = service.addProgramSubjectTagging(req);
        Map map = new HashMap();
        map.put("programTaggingChildList", list);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String addNewProgramSubjectTagging(HttpServletRequest request) {
        List l = service.addNewProgramSubjectTagging(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String programSubjectTaggingEdit(ModelMap mm, HttpServletRequest request) {
        mm = service.getAllProgramSubjectTaggingData(mm, request);
        return "programSubjectTagging/programSubjectTaggingEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateProgramSubjectTagging(HttpServletRequest request) {
        List l = service.updateProgramSubjectTagging(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return service.checkIfChildExist(request);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteProgramSubjectTagging(HttpServletRequest request) {
        List l = service.deleteProgramSubjectTagging(request);
        return l.get(0).toString();
    }

    @RequestMapping("/copy")
    @ResponseBody
    public String copyProgramSubjectTagging(HttpServletRequest request) {
        List l = service.copyProgramSubjectTagging(request);
        return l.get(0).toString();
    }
}
