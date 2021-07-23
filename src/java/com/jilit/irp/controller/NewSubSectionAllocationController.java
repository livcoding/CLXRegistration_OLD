/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.NewSubSectionAllocationIService;
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
@RequestMapping("/newSubSectionAllocation")
public class NewSubSectionAllocationController {

    @Autowired
    NewSubSectionAllocationIService service;

    @RequestMapping("/list")
    public String getStudentMaster(Model model) {
        service.getStudentMasterTabData(model);
        return "newSubSectionAllocation/newSubSectionAllocationList";
    }

    @RequestMapping("/getBranchAndStyNumber")
    @ResponseBody
    public List getBranchAndStyNumber(HttpServletRequest req, ModelMap mm) {
        List list = service.getBranchAndStyNumber(req, mm);
        return list;
    }

    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest req) {
        List list = service.getSection(req);
        Map map = new HashMap();
        map.put("section", list);
        return map;
    }

    @RequestMapping("/getSubSectionStudentMas")
    @ResponseBody
    public Map getSubSectionStudentMas(HttpServletRequest req) {
        List list = service.getSubSectionStudentMas(req);
        Map map = new HashMap();
        map.put("subSection", list);
        return map;
    }

    @RequestMapping("/getSubsectionForCombo2")
    @ResponseBody
    public Map getSubsectionForCombo2(HttpServletRequest req) {
        List list = service.getSubsectionForCombo2(req);
        Map map = new HashMap();
        map.put("SubsectionForCombo2", list);
        return map;
    }

    @RequestMapping("/loadStudentMasterData")
    @ResponseBody
    public Map loadStudentMasterList(HttpServletRequest req) {
        List list = new ArrayList();
        list = service.loadStudentMasterList(req);
        Map map = new HashMap();
        map.put("loadStudentMasterList", list);
        return map;
    }

    @RequestMapping("/saveStudentMas")
    @ResponseBody
    public Map saveSubSectionAllocationStudentMaster(HttpServletRequest request) {
        List l = service.saveSubSectionAllocationStudentMaster(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }
    //------------Reg--------------//

    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest req) {
        List list = service.getAcademicYear(req);
        Map map = new HashMap();
        map.put("acadYearRegList", list);
        return map;
    }

    @RequestMapping("/getStyNumber")
    @ResponseBody
    public Map getStyNumber(HttpServletRequest req) {
        List list = service.getStyNumber(req);
        Map map = new HashMap();
        map.put("styNumberRegList", list);
        return map;
    }

    @RequestMapping("/getBranchForStuReg")
    @ResponseBody
    public Map getBranchForStuReg(HttpServletRequest req) {
        List list = service.getBranchForStuReg(req);
        Map map = new HashMap();
        map.put("branchRegList", list);
        return map;
    }

    @RequestMapping("/getSectionForStuReg")
    @ResponseBody
    public Map getSectionForStuReg(HttpServletRequest req) {
        List list = service.getSectionForStuReg(req);
        Map map = new HashMap();
        map.put("secRegList", list);
        return map;
    }

    @RequestMapping("/getSectionListForReg")
    @ResponseBody
    public Map getSectionListForReg(HttpServletRequest req) {
        List list = service.getSectionListForReg(req);
        Map map = new HashMap();
        map.put("sectionRegList", list);
        return map;
    }

    @RequestMapping("/getSubSectionListForReg")
    @ResponseBody
    public Map getSubSectionListForReg(HttpServletRequest req) {
        List list = service.getSubSectionListForReg(req);
        Map map = new HashMap();
        map.put("getSubSectionList", list);
        return map;
    }

    @RequestMapping("/loadStudentRegData")
    @ResponseBody
    public Map loadStudentRegData(HttpServletRequest req) {
        List list = new ArrayList();
        list = service.loadStudentRegData(req);
        Map map = new HashMap();
        map.put("loadStudentRegData", list);
        return map;
    }

    @RequestMapping("/saveStudentReg")
    @ResponseBody
    public Map saveSubSectionAllocationStudentReg(HttpServletRequest request) {
        List l = service.saveSubSectionAllocationStudentReg(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }

    //------------Choice Detail--------------//
    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest req) {
        List list = service.getSubjectCode(req);
        Map map = new HashMap();
        map.put("subjectCode", list);
        return map;
    }

    @RequestMapping("/getDepartmentCode")
    @ResponseBody
    public Map getDepartmentCode(HttpServletRequest req) {
        List list = service.getDepartmentCode(req);
        Map map = new HashMap();
        map.put("deptCode", list);
        return map;
    }

    @RequestMapping("/getSubjectCompCode")
    @ResponseBody
    public Map getSubjectCompCode(HttpServletRequest req) {
        List list = service.getSubjectCompCode(req);
        Map map = new HashMap();
        map.put("subComp", list);
        return map;
    }

    @RequestMapping("/loadStudentChoiceData")
    @ResponseBody
    public Map loadStudentChoiceData(HttpServletRequest req) {
        List list = new ArrayList();
        list = service.loadStudentChoiceData(req);
        Map map = new HashMap();
        map.put("loadStudentChoiceData", list);
        return map;
    }

    @RequestMapping("/getSectionForChoice")
    @ResponseBody
    public Map getSectionForChoice(HttpServletRequest req) {
        List list = service.getSectionForChoice(req);
        Map map = new HashMap();
        map.put("sectionChoice", list);
        return map;
    }

    @RequestMapping("/getSubSectionChoice")
    @ResponseBody
    public Map getSubSectionChoice(HttpServletRequest req) {
        List list = service.getSubSectionChoice(req);
        Map map = new HashMap();
        map.put("subSectionChoice", list);
        return map;
    }

    @RequestMapping("/getAcademicYearForChoice")
    @ResponseBody

    public Map getAcademicYearForChoice(HttpServletRequest req) {
        List list = service.getAcademicYearForChoice(req);
        Map map = new HashMap();
        map.put("acadYearChoice", list);
        return map;
    }

    @RequestMapping("/getProgramForChoice")
    @ResponseBody
    public Map getProgramForChoice(HttpServletRequest req) {
        List list = service.getProgramForChoice(req);
        Map map = new HashMap();
        map.put("progChoice", list);
        return map;
    }

    @RequestMapping("/getBranchForChoice")
    @ResponseBody
    public Map getBranchForChoice(HttpServletRequest req) {
        List list = service.getBranchForChoice(req);
        Map map = new HashMap();
        map.put("branchChoice", list);
        return map;
    }

    @RequestMapping("/getStyTypeChoice")
    @ResponseBody
    public Map getStyTypeChoice(HttpServletRequest req) {
        List list = service.getStyTypeChoice(req);
        Map map = new HashMap();
        map.put("styTypeChoice", list);
        return map;
    }

    @RequestMapping("/getStyNumberChoice")
    @ResponseBody
    public Map getStyNumberChoice(HttpServletRequest req) {
        List list = service.getStyNumberChoice(req);
        Map map = new HashMap();
        map.put("styNumChoice", list);
        return map;
    }

    @RequestMapping("/loadStudentChoiceFilterData")
    @ResponseBody
    public Map loadStudentChoiceFilterData(HttpServletRequest req) {
        List list = new ArrayList();
        list = service.loadStudentChoiceFilterData(req);
        Map map = new HashMap();
        map.put("loadStudentChoiceFilterData", list);
        return map;
    }

    @RequestMapping("/saveStudentChoice")
    @ResponseBody
    public Map saveSubSectionAllocationStudentChoiceDetail(HttpServletRequest request) {
        List l = service.saveSubSectionAllocationStudentChoiceDetail(request);
        Map map = new HashMap();
        map.put("status", l.get(0).toString());
        return map;
    }
}
