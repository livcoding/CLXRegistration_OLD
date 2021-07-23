/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.BackPaperFeesService;
import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.BatchWiseCoordinatorIService;
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
 * @author Malkeet Singh
 */
@Controller
@RequestMapping("/batchWiseCoordinator")
public class BatchWiseCoordinatorController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    BatchWiseCoordinatorIService service;

    @RequestMapping("/list")
    public String getRegistrationSubjectGroupList(ModelMap model, HttpServletRequest req) {
        service.getSemesterCode(model);
        return "batchWiseCoordinator/batchWiseCoordinatorList";
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getGroupId(HttpServletRequest req) {
        List list = service.getSubjectCode(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("subjectCode", list);
        } else {
            map.put("subjectCode", null);
        }
        return map;
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map; 
    }

    @RequestMapping("/add") 
    public String addBatchWiseCoordinator(ModelMap model, HttpServletRequest req) {
        service.getSemesterCode(model);
        service.getSubjectComponentCode(model);
        service.getCoordinatorType(model);
        return "batchWiseCoordinator/batchWiseCoordinatorAdd";
    }

    @RequestMapping("/getStaffCode")
    @ResponseBody
    public Map getStaffCode(HttpServletRequest req) {
        List list = service.getStaffCode(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("staffCode", list);
        } else {
            map.put("staffCode", null);
        }
        return map;
    }
    
    @RequestMapping("/getAddGridData")
    @ResponseBody
    public Map getAddGridData(HttpServletRequest req) {
        List list = service.getAddGridData(req);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("addGridData", list);
        } else {
            map.put("addGridData", null);
        }
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveBatchWiseCoordinator(HttpServletRequest request) {
        List l = service.saveBatchWiseCoordinator(request);
        return l.get(0).toString();
    }
    
     @RequestMapping("/delete")
    @ResponseBody
    public String deleteBatchWiseCoordinator(HttpServletRequest request) {
        List l = service.deleteBatchWiseCoordinator(request);
        return l.get(0).toString();
    }
   
}
