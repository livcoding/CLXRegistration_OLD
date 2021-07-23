package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.PreRequisiteForRegistrationIService;
import com.jilit.irp.iservice.SubjectChoiceNotFilledByStudentReportIService;
import java.util.ArrayList;
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
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/subjectChoiceNotFilledByStudentReport")
public class SubjectChoiceNotFilledByStudentReportController {
    
    @Autowired
    SubjectChoiceNotFilledByStudentReportIService service;
    
    @RequestMapping("/list")
    public String getListSubjectChoiceNotFilledByStudentReport(ModelMap model) {
        service.getRegistrationCombo(model);
        return "subjectChoiceNotFilledByStudentReport/subjectChoiceNotFilledByStudentReport";
    }
    
    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest request) {
        List acadList = new ArrayList();
        acadList = service.getAllAcademicYear(request);
        Map map = new HashMap();
        map.put("acadList", acadList);
        return map;
    }
    
    @RequestMapping("/getProgramCode")
    @ResponseBody
    public Map getProgramCode(HttpServletRequest request) {
        List progList = new ArrayList();
        progList = service.getAllProgramCode(request);
        Map map = new HashMap();
        map.put("progList", progList);
        return map;
    }
    
    @RequestMapping("/getSectionCode")
    @ResponseBody
    public Map getSectionCode(HttpServletRequest request) {
        List secList = new ArrayList();
        secList = service.getAllSectionCode(request);
        Map map = new HashMap();
        map.put("secList", secList);
        return map;
    }
    
    @RequestMapping("/getBasketCode")
    @ResponseBody
    public Map getBasketCode(HttpServletRequest request) {
        List basketList = new ArrayList();
        basketList = service.getAllBasketCode(request);
        Map map = new HashMap();
        map.put("basketList", basketList);
        return map;
    }
    
    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest request) {
        List subjectList = new ArrayList();
        subjectList = service.getAllSubjectCode(request);
        Map map = new HashMap();
        map.put("subjectList", subjectList);
        return map;
    }
    
    @RequestMapping("/getSubSectionCode")
    @ResponseBody
    public Map getSubSectionCode(HttpServletRequest request) {
        List subSectList = new ArrayList();
        subSectList = service.getAllSubSectionCode(request);
        Map map = new HashMap();
        map.put("subSectList", subSectList);
        return map;
    }
    
    @RequestMapping("getSemester")
    @ResponseBody
    public Map getSemester(HttpServletRequest request) {
        List semList = new ArrayList();
        semList = service.getAllSemester(request);
        Map map = new HashMap();
        map.put("semList", semList);
        return map;
    }
    
    @RequestMapping("/getReport")
    public void getSubjectChoiceNotFilledByStudentReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
