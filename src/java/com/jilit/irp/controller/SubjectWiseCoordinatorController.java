/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.SubjectWiseCoordinatorService;
import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.SubjectWiseCoordinatorIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Priyanka.tyagi
 */
@Controller
@RequestMapping("/subjectWiseCoordinator")
public class SubjectWiseCoordinatorController {

    @Autowired
    SubjectWiseCoordinatorIService subjectWiseCoordinatorService;

    @RequestMapping("/list")
    public String ListSubjectWiseCoordinator(Model model) {
        subjectWiseCoordinatorService.getSubjectWiseCoordinatorList(model);
        return "subjectWiseCoordinator/subjectWiseCoordinatorList";
    }

    @RequestMapping("/add")
    public String getAllDepartmentCode(ModelMap map) {
        subjectWiseCoordinatorService.getAllSubjectCode(map);
        return "subjectWiseCoordinator/subjectWiseCoordinatorAdd";
    }

    @RequestMapping("/getCoordinator")
    @ResponseBody
    public Map getCoordinator(HttpServletRequest request) {
        List list = null;
        list = subjectWiseCoordinatorService.getCoordinator(request);
        Map map = new HashMap();
        map.put("coordinatorsList", list);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveDepertmentSubject(HttpServletRequest request) {
        List err = null;
        err = subjectWiseCoordinatorService.saveSubjectCoordinator(request);
        return err.get(0).toString();
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String updateStatus(HttpServletRequest request) {
        List l = subjectWiseCoordinatorService.updateStatus(request);
        return l.get(0).toString();
    }
}
