/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.BulkSubjectRegistrationIService;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/bulkSubjectRegistration")
public class BulkSubjectRegistrationController {

    @Autowired
    BulkSubjectRegistrationIService service;

    @RequestMapping("/list")
    public String bulkSubjectRegistration(Model model) {
        service.getRegistrationCode(model);
        return "bulkSubjectRegistration/bulkSubjectRegistrationList";
    }

    @RequestMapping("/checkExcelData")
    @ResponseBody
    public Map checkExcelData(HttpServletRequest request, MultipartFile file) {
        List list = service.checkExcelData(request, file);
        Map map = new HashMap();
        if (list.get(0).toString().equalsIgnoreCase("Validation_Error_List:")){
            map.put("errorAlert", list);
        } else {
            map.put("excelColumndata", list);
        }
        return map;
    }

    @RequestMapping("/analyzeAndSaveExcelData")
    @ResponseBody
    public Map analyzeAndSaveExcelData(HttpServletRequest request) {
        List list = service.analyzeAndSaveExcelData(request);
        Map map = new HashMap();
        map.put("analyzeexceldata", list);
        return map;
    }

    @RequestMapping("/saveAndDeleteExcelData")
    @ResponseBody
    public Map saveAndDeleteExcelData(HttpServletRequest request) {
        List list = service.saveAndDeleteExcelData(request);
        Map map = new HashMap();
        map.put("savedata", list);
        return map;
    }

    @RequestMapping("/saveAndDeleteExcelData1")
    @ResponseBody
    public Map saveAndDeleteExcelData1(HttpServletRequest request) {
        List list = service.saveAndDeleteExcelData1(request);
        Map map = new HashMap();
        map.put("deleteData", list);
        return map;
    }

    @RequestMapping("/report")
    public void getBatchWiseStudentList(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
