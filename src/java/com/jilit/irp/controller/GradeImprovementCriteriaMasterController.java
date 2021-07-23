/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.GradeImprovementCriteriaMasterIServices;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author campus.trainee
 */
@Controller
@RequestMapping("/gradeImprovementCriteriaMaster")
public class GradeImprovementCriteriaMasterController {

    @Autowired
    GradeImprovementCriteriaMasterIServices service;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getGradeImprovementCriteriaMaster(ModelMap model) {
        service.getListGradeImprovementCriteriaMaster(model);
        return "gradeImprovementCriteriaMaster/gradeImprovementCriteriaMasterList";
    }

    @RequestMapping("/add")
    public String addGradeImprovementCriteriaMaster(ModelMap model) {
        service.getAllProgramMaster(model);
        return "gradeImprovementCriteriaMaster/gradeImprovementCriteriaMasterAdd";
    }

    @RequestMapping("/edit")
    public String editGradeImprovementCriteriaMaster(ModelMap mm, HttpServletRequest request) {
        service.editGradeImprovementCriteriaMaster(request, mm);
        return "gradeImprovementCriteriaMaster/gradeImprovementCriteriaMasterEdit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveGradeImprovementCriteriaMaster(HttpServletRequest request) {
        List l = service.saveGradeImprovementCriteriaMaster(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteGradeImprovementCriteriaMaster(HttpServletRequest request){
      List l = service.deleteGradeImprovementCriteriaMaster(request);
      return l.get(0).toString();
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String updateGradeImprovementCriteriaMaster(HttpServletRequest request){
    List l = service.updateGradeImprovementCriteriaMaster(request);
      return l.get(0).toString();
    }
}