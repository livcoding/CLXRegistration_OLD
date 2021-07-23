/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.DepartmentSubjectTaggingIService;
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
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/DepartmentSubjectTagging")
public class DepartmentSubjectTaggingController {

    @Autowired
    DepartmentSubjectTaggingIService departmentSubjectTaggingIService;

    @RequestMapping("/list")
    public String getDepartmentSubjectList(ModelMap model) {
        departmentSubjectTaggingIService.getAllDepartmentCode(model);
        return "departmentSubjectTagging/departmentSubjectTaggingList";
    }

    @RequestMapping("/add")
    public String getAllDepartmentCode(ModelMap map) {
        departmentSubjectTaggingIService.getAllDepartmentCode(map);
        departmentSubjectTaggingIService.getReqSubjectForBranchChange(map);
        return "departmentSubjectTagging/departmentSubjectTaggingAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveDepertmentSubject(HttpServletRequest request) {
        List err = null;
        err = departmentSubjectTaggingIService.saveDepertmentSubject(request);
        return err.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteStudentProgrammaxsty(HttpServletRequest request) {
        List l = departmentSubjectTaggingIService.deleteDepartmentSubject(request);
        return l.get(0).toString();
    }

//    @RequestMapping("/getDepartmentwise")
//    @ResponseBody
//    public String getDepartmentwiseSubjectList(HttpServletRequest request, ModelMap map) {
//        departmentSubjectTaggingIService.getDepartmentwiseSubjectList(request, map);
//        return "departmentSubjectTagging/departmentSubjectTaggingList";
//    }

    @RequestMapping("/getDepartmentwise")
    @ResponseBody
    public Map getBasketMaster(HttpServletRequest req, ModelMap mm) {
        List list = departmentSubjectTaggingIService.getDepartmentwiseSubjectList(req, mm);
        Map map = new HashMap();
        map.put("taggedlist", list);
        return map;
    }

}
