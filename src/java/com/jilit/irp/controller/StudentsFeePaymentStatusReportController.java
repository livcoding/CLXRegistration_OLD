/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.AddDropSubjectIService;
import com.jilit.irp.iservice.StudentsFeePaymentStatusReportIService;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author priyanka.tyagi
 */
@Controller
@RequestMapping("/studentsFeePaymentStatusReport")
public class StudentsFeePaymentStatusReportController {

    @Autowired
    StudentsFeePaymentStatusReportIService studentsFeePaymentStatusReportIService;
    
    @Autowired
    AddDropSubjectIService addDropSubjectIService;

    @RequestMapping("/list")
    public String getViewstudentsFeePaymentStatus(ModelMap model) {
        addDropSubjectIService.getInstituteCodeForAddDrop(model);
        return "studentsFeePaymentStatusReport/studentsFeePaymentStatusReport";
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List l = studentsFeePaymentStatusReportIService.getSemesterCode(request);
        Map map = new HashMap();
        map.put("semCode", l);
        return map;
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List l = studentsFeePaymentStatusReportIService.getGridData(request);
        Map map = new HashMap();
        map.put("gridData", l.get(0));
        map.put("subCode", l.get(1));
        map.put("regSub", l.get(2));
        return map;
    }
}
