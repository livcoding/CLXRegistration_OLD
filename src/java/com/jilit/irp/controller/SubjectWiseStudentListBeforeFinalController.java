/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectWiseStudentListBeforeFinalIService;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/subjectWiseStudentListBeforeFinal")
public class SubjectWiseStudentListBeforeFinalController {

    @Autowired
    JIRPSession jirpsession;

    @Autowired
    SubjectWiseStudentListBeforeFinalIService subjectWiseStudentListBeforeFinalIService;

    @RequestMapping("/list")
    public String subjectWiseStudentListBeforeFinal(Model model, HttpServletRequest req) {
        subjectWiseStudentListBeforeFinalIService.getSemesterCode(model);
        return "subjectWiseStudentListBeforeFinal/subjectWiseStudentListBeforeFinalList";
    }

 
    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSubjectCodeList(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getSubjectCode( req);
        Map map = new HashMap();
        map.put("subCode_list", list);
        return map;
    }
    @RequestMapping("/getAcademicYear")
    @ResponseBody
    public Map getAcademicYear(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getAcademicYear( req);
        Map map = new HashMap();
        map.put("acadyear_list", list);
        return map;
    }
    @RequestMapping("/getProgramCode")
    @ResponseBody
    public Map getProgramCode(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getProgramCode( req);
        Map map = new HashMap();
        map.put("prog_list", list);
        return map;
    }
    @RequestMapping("/getSemester")
    @ResponseBody
    public Map getSemester(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getSemester( req);
        Map map = new HashMap();
        map.put("sem_list", list);
        return map;
    }
    @RequestMapping("/getSectionMaster")
    @ResponseBody
    public Map getSectionMaster(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getSectionMaster( req);
        Map map = new HashMap();
        map.put("sec_list", list);
        return map;
    }
    @RequestMapping("/getsubsection")
    @ResponseBody
    public Map getsubsection(HttpServletRequest req) {
        List list = subjectWiseStudentListBeforeFinalIService.getsubsection( req);
        Map map = new HashMap();
        map.put("subSec_list", list);
        return map;
    }
    
     @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        subjectWiseStudentListBeforeFinalIService.getReport(request, response);
    }
    

}
