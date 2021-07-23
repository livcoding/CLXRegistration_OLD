/*
    Document   : StudentNRSubjectsController
    Created on : Sep 21 : 2019 
    Author     : Malkeet Singh
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.StudentNRSubjectsIService;
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
@RequestMapping("/studentNRSubjects")
public class StudentNRSubjectsController {

    @Autowired
    StudentNRSubjectsIService service;

    @RequestMapping("/list")
    public String studentRegistrationPermission(ModelMap model) {
        return "studentNRSubjects/studentNRSubjectsList";
    }

    @RequestMapping("/getStudetnInfo")
    @ResponseBody
    public Map getStudetnInfo(HttpServletRequest request) {
        List l = service.getStudetnInfo(request);
        Map map = new HashMap();
        map.put("studentInfo", l);
        return map;
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List l = service.getGridData(request);
        Map map = new HashMap();
        map.put("gridData", l);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String editNRSubjects(HttpServletRequest request) {
        List l = service.saveNRSubjects(request);
        return l.get(0).toString();
    }

    @RequestMapping("/add")
    public String getAddStudentNRSubjects(ModelMap model, HttpServletRequest request) {
        String data[] = request.getParameter("data").split("~@~");
        model.put("studentid", data[0]);
        model.put("studentname", data[1]);
        service.getSemesterCode(model, data[0]);
        return "studentNRSubjects/studentNRSubjectsAdd";
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest request) {
        List l = service.getSubjectCode(request);
        Map map = new HashMap();
        map.put("subCode", l);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveNRSubjects(HttpServletRequest request) {
        List l = service.saveNRSubjects(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteNRSubjects(HttpServletRequest request) {
        List l = service.deleteNRSubjects(request);
        return l.get(0).toString();
    }

}
