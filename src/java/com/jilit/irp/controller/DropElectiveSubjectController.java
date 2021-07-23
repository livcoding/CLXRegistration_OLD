/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.DropElectiveSubjectIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/dropElectiveSubject")

public class DropElectiveSubjectController {

    @Autowired
    DropElectiveSubjectIservice dropElective;

    @RequestMapping("/list")
    public String getDropElectiveSubject(Model model) {
        dropElective.getFormData(model);
        return "dropElectiveSubject/dropElectiveSubjectForm";
    }

    @RequestMapping("/getSubjectType")
    @ResponseBody
    public Map getSubjectType(HttpServletRequest request) {
        List err = null;
        err = dropElective.getSubjectType(request);
        Map map = new HashMap();
        map.put("subjecttype", err);
        return map;
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest request) {
        List err = null;
        err = dropElective.getSubjectCode(request);
        Map map = new HashMap();
        map.put("subjectcode", err);
        return map;
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List err = null;
        err = dropElective.getGridData(request);
        Map map = new HashMap();
        map.put("listdata", err);
        return map;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public String getAllProcessForDropping(HttpServletRequest request) {
        List err = null;
        err = dropElective.getAllProcessForDropping(request);
        return err.get(0).toString();
    }

}
