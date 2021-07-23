/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.TeachingSchemeIService;
import java.util.ArrayList;
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
 * @author mohit1.kumar
 */
@Controller
@RequestMapping("/teachingSch")
public class TeachingSchemeController {

    @Autowired
    private TeachingSchemeIService teachingSchemeIService;

    @RequestMapping("/list")
    public String teachSchemeLiat(ModelMap model) {
        teachingSchemeIService.getTeachingSchemeData(model);
        return "teachingScheme/teachingScheme";
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest req) {
        List list = teachingSchemeIService.getBranch(req);
        Map map = new HashMap();
        map.put("branch", list);
        return map;
    }

    @RequestMapping("/getSemester")
    @ResponseBody
    public Map getSemester(HttpServletRequest req) {
        List list = teachingSchemeIService.getSemester(req);
        Map map = new HashMap();
        map.put("semester", list);
        return map;
    }

    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest req) {
        List list = teachingSchemeIService.getSection(req);
        Map map = new HashMap();
        map.put("section", list);
        return map;
    }

    @RequestMapping("/getBasket")
    @ResponseBody
    public Map getBasket(HttpServletRequest req) {
        List list = teachingSchemeIService.getBasket(req);
        Map map = new HashMap();
        map.put("basket", list);
        return map;
    }

    @RequestMapping("/getBasketData")
    @ResponseBody
    public Map getBasketData(HttpServletRequest req) {
        List list = teachingSchemeIService.getBasketData(req);
        Map map = new HashMap();
        map.put("basket", list);
        return map;
    }
    @RequestMapping("/getMarks")
    @ResponseBody
    public Map getMarsk(HttpServletRequest req) {
        List list = teachingSchemeIService.getMarks(req);
        Map map = new HashMap();
        map.put("marks", list);
        return map;
    }

    @RequestMapping("/copy")
    @ResponseBody
    public String saveCopiedData(HttpServletRequest request) {
        List l = teachingSchemeIService.saveCopiedData(request);
        return l.get(0).toString();
    }

    @RequestMapping("/loadTeachSchList")
    @ResponseBody
    public Map loadTeachSchData(HttpServletRequest req) {
        List list = new ArrayList();
        list = teachingSchemeIService.getGridData(req);
        Map map = new HashMap();
        map.put("loadTeachSchData", list);
        return map;
    }

    @RequestMapping("/add")
    public String teachingSchemeAdd(ModelMap model, HttpServletRequest req) {
        String data[] = req.getParameter("data").split("~@~");
        model.put("acadYear", data[0]);
        model.put("program", data[1]);
        model.put("section", data[2]);
        model.put("semester", data[3]);
        model.put("subType", teachingSchemeIService.getAllSubjectType());
        model.put("subCode", teachingSchemeIService.getAllSubjectMaster());
        model.put("elecType", teachingSchemeIService.getAllElecticeCode());
        model.put("subComp", teachingSchemeIService.getAllSubjectComponent());
        return "teachingScheme/teachingSchemeAdd";
    }

    @RequestMapping("/getAllBasket")
    @ResponseBody
    public Map getAllBasket(HttpServletRequest req) {
        List list = teachingSchemeIService.getAllBasketMaster(req);
        Map map = new HashMap();
        map.put("basket", list);
        return map;
    }

    @RequestMapping("/getDepartment")
    @ResponseBody
    public Map getDepartment(HttpServletRequest req) {
        List list = teachingSchemeIService.getDepartmentMasterData(req);
        Map map = new HashMap();
        map.put("dep", list);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String addProgramSchemeAcademicyearWise(HttpServletRequest request) {
        List l = teachingSchemeIService.addProgramSchemeAcademicyearWise(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String teachingSchemeEdit(ModelMap model, HttpServletRequest req) {
        model.put("subType", teachingSchemeIService.getAllSubjectType());
        model.put("subCode", teachingSchemeIService.getAllSubjectMaster());
        model.put("elecType", teachingSchemeIService.getAllElecticeCode());
        model.put("subComp", teachingSchemeIService.getAllSubjectComponent());
        teachingSchemeIService.getTeachingSchemeEditData(model, req);
        return "teachingScheme/teachingSchemeEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateProgramSchemeAcademicyearWise(HttpServletRequest request) {
        List l = teachingSchemeIService.updateProgramSchemeAcademicyearWise(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteProgramSubjectTagging(HttpServletRequest request) {
        List l = teachingSchemeIService.deleteProgramScheme(request);
        return l.get(0).toString();
    }
}
