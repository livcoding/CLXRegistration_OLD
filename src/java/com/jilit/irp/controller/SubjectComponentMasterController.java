/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectComponentMasterIService;
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
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/subjectComponentMaster")
public class SubjectComponentMasterController {

    @Autowired
    SubjectComponentMasterIService SubjectComponentMasterIService;

    @RequestMapping("/list")
    public String listStudentCategoryMaster(Model model) {
        model = SubjectComponentMasterIService.getSubjectComponentMasterList(model);
        return "subjectComponentMaster/subjectComponentMasterList";
    }

    @RequestMapping("/add")
    public String studentCategoryMasterAdd() {
        return "subjectComponentMaster/subjectComponentMasterAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String addNewStudentCategoryMaster(HttpServletRequest request) {
        List l = SubjectComponentMasterIService.addSubjectComponentMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String studentCategoryMasterEdit(ModelMap mm, HttpServletRequest request) {
        mm = SubjectComponentMasterIService.getAllSubjectComponentMasterData(mm, request);
        return "subjectComponentMaster/subjectComponentMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateStudentCategoryMaster(HttpServletRequest request) {
        List l = SubjectComponentMasterIService.updateSubjectComponentMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return SubjectComponentMasterIService.checkIfChildExist(request);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteStudentCategoryMaster(HttpServletRequest request) {
        List l = SubjectComponentMasterIService.deleteSubjectComponentMaster(request);
        return l.get(0).toString();
    }

}
