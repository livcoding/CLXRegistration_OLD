/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectComponentwiseStudentListIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/subjectComponentwiseStudentListReport")
public class SubjectComponentwiseStudentListController {

    @Autowired
    SubjectComponentwiseStudentListIService service;

    
    @RequestMapping("/list")
    public String backPaperReportList(Model model) {
        service.getDataList(model);
        return "subjectComponentwiseStudentListReport/subjectComponentwiseStudentListReport";
    }

    @RequestMapping("/getSubSection")
    @ResponseBody
    public Map getSubSection(HttpServletRequest req) {
        List list = service.getSubSectionList(req);
        Map map = new HashMap();
        map.put("subSectionList", list);
        return map;
    }
    
    @RequestMapping("/subjectComponentwiseStudentListReport")
    public void getbackPaperReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
