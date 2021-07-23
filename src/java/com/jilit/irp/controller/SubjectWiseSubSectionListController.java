/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectWiseSubSectionListIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/subjectWiseSubSectionList")
public class SubjectWiseSubSectionListController {

    @Autowired
    SubjectWiseSubSectionListIService iservice;

    @RequestMapping("/list")
    public String getSubjectWiseList(Model model) {
        iservice.getSemesterCode(model);
        return "subjectWiseSubSectionListReport/subjectWiseSubSectionListReport";
    }

    @RequestMapping("getDepCode")
    @ResponseBody
    public Map getDepCode(HttpServletRequest request) {
        List l = iservice.getDepCode(request);
        Map map = new HashMap();
        map.put("dep_list", l);
        return map;
    }

    @RequestMapping("getSubCode")
    @ResponseBody
    public Map getSubCode(HttpServletRequest request) {
        List l = iservice.getSubCode(request);
        Map map = new HashMap();
        map.put("sub_list", l);
        return map;
    }
    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        iservice.getReport(request, response);
    }

}
