/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SummerSubjectRagistrationApprovalIservice;
import java.util.HashMap;
import org.springframework.ui.ModelMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

/**
 *
 * @author malkeet.singh
 */
@Controller
@RequestMapping("/summerSubjectRagistrationApproval")
public class SummerSubjectRagistrationApprovalController {

    @Autowired
    SummerSubjectRagistrationApprovalIservice service;

    @RequestMapping("/list")
    public String getFormData(ModelMap model) {
        service.getInstituteCode(model);
        return "summerSubRegApproval/summerSubRegApprovalForm";
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List l = service.getSemesterCode(request);
        Map map = new HashMap();
        map.put("semestercodelist", l);
        return map;
    }

    @RequestMapping("/getPendingForApprovalData")
    @ResponseBody
    public Map getPendingForApprovalData(HttpServletRequest request) {
        Map map = null;
        map = (Map) service.getPendingForApprovalData(request);
        return map;
    }

    @RequestMapping("/getApprovedData")
    @ResponseBody
    public Map getApprovedData(HttpServletRequest request) {
        List err = null;
        err = service.getApprovedData(request);
        Map map = new HashMap();
        map.put("approvedData", err);
        return map;
    }

    @RequestMapping("/getCanceledData")
    @ResponseBody
    public Map getCanceledData(HttpServletRequest request) {
        List err = null;
        err = service.getCanclledData(request);
        Map map = new HashMap();
        map.put("canceledData", err);
        return map;
    }

    @RequestMapping("/getReportData")
    @ResponseBody
    public Map getReportData(HttpServletRequest request) {
        List err = null;
        err = service.getSubjectWiseReportData(request);
        Map map = new HashMap();
        map.put("reportData", err);
        return map;
    }

    @RequestMapping("/approve")
    @ResponseBody
    public Map approvePendingData(HttpServletRequest request) {
        String status = "A";
        List err = null;
        err = service.approveCancleData(status, request);
        Map map = new HashMap();
        map.put("status", err.get(0).toString());
        return map;
    }

    @RequestMapping("/cancel")
    @ResponseBody
    public Map cancelApproveData(HttpServletRequest request) {
        String status = "C";
        List err = null;
        err = service.approveCancleData(status, request);
        Map map = new HashMap();
        map.put("status", err.get(0).toString());
        return map;
    }
}
