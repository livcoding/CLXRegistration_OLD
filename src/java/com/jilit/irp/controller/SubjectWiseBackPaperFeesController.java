/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.BackPaperFeesService;
import com.jilit.irp.data.common.CommonComboIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Malkeet Singh
 */
@Controller
@RequestMapping("/subjectWiseBackPaperFees")
public class SubjectWiseBackPaperFeesController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    BackPaperFeesService service;

    @RequestMapping("/list")
    public String getSubjectWiseBackPaperFeesList(ModelMap model, HttpServletRequest req) {
        service.getSemesterCode(model, req);
        return "subjectWiseBackPaperFees/subjectWiseBackPaperFeesList";
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getSubjectWiseGridData(request); 
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSectionCode(HttpServletRequest req, ModelMap model) {
        List list = service.getSubjectCode(model, req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("subjectCode", list);
        } else {
            map.put("subjectCode", null);
        }
        return map;
    }

    @RequestMapping("/add")
    public String getSubjectWiseBackPaperFeeAdd(ModelMap model, HttpServletRequest req) {
        service.getSemesterCode(model, req);
        service.getDepartmentData(model, req);
        return "subjectWiseBackPaperFees/subjectWiseBackPaperFeesAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveSubjectWiseBackPaperFees(ModelMap model, HttpServletRequest request) {
        List l = service.saveOrUpdateSubjectWise(request);
        return l.get(0).toString();
    }

}
