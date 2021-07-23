/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.TimeTableSlotMasterIService;
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
 * @author Nazar.Mohammad
 */
@Controller
@RequestMapping("/timeTableSlotMaster")
public class TimeTableSlotMasterController {
    @Autowired
    CommonComboIservice commonComboIservice;
    @Autowired
    TimeTableSlotMasterIService timeTableSlotMasterIService;
    
    @RequestMapping("/list")
    public String getRegistrationCode(ModelMap model) {
        timeTableSlotMasterIService.getAllRegsitrationCode(model);
        return "timeTableSlotMaster/timeTableSlotMasterList";
    }
    
    @RequestMapping("/add")
    public String addTimeTableSlotMaster(ModelMap model) {
        timeTableSlotMasterIService.getAllRegsitrationCode(model);
        return "timeTableSlotMaster/timeTableSlotMaster";
    }
    
    @RequestMapping("/registrationCode")  
    @ResponseBody
    public Map registrationCode(HttpServletRequest request, ModelMap mm) {
        List l = timeTableSlotMasterIService.registrationCode(request);
        Map map = new HashMap();
        map.put("regList", l);
        return map;
    }
    
    @RequestMapping("/gridData")  
    @ResponseBody
    public Map gridData(HttpServletRequest request, ModelMap mm) {
        Map l = timeTableSlotMasterIService.getGridData(request);
//        Map map = new HashMap();
//        map.put("timeSloatList", l);
        return l;
    }
    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return timeTableSlotMasterIService.checkIfChildExist(request);

    }
    
    @RequestMapping("/copyPreviousData")
    @ResponseBody
    public Map doSaveBulkStudents(HttpServletRequest request, ModelMap mm) {
        List l = timeTableSlotMasterIService.copyPreviousData(request);
        Map map = new HashMap();
        map.put("griddata", l);
        return map;        
    }
}
