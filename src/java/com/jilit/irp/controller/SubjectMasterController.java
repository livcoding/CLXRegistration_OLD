/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectMasterIService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nazar.Mohammad
 */
@Controller
@RequestMapping("/subjectMaster")
public class SubjectMasterController {
    @Autowired 
    SubjectMasterIService subjectMasterIService;
    
    @RequestMapping("/list")
    public String getList(Model model) {
        subjectMasterIService.getList(model);
        return "subjectMaster/subjectMasterList";
    }
    
    @RequestMapping("/add")
    public String subjectMasterAdd(Model model) {
        subjectMasterIService.subjectMasterAdd(model);        
        return "subjectMaster/subjectMasterAdd";
    }
    
    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return subjectMasterIService.checkIfChildExist(request);
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSubjectMaster(HttpServletRequest request) {
        List l = subjectMasterIService.deleteSubjectMaster(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public String addSubjectMaster(HttpServletRequest request) {
        List l = subjectMasterIService.addSubjectMaster(request);
       
        return l.get(0).toString();
    }
    @RequestMapping("/update")
    @ResponseBody
    public String updateSubjectMaster(HttpServletRequest request) {
        List l = subjectMasterIService.updateSubjectMaster(request);       
        return l.get(0).toString();
    }
    
    @RequestMapping("/edit")
    public String subjectMasterEdit(ModelMap mm, HttpServletRequest request) {
        mm = subjectMasterIService.subjectMasterEdit(mm, request);
        return "subjectMaster/subjectMasterEdit";
    }
    
}
