/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.BasketMasterIService;
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
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/basketMasterController")
public class BasketMasterController {

    @Autowired
    BasketMasterIService iservice;

    @RequestMapping("/list")
    public String basketMaster(Model model) {
        iservice.getProgramCode(model);
       // iservice.getSectionCode(model);
        return "basketMaster/basketMasterList";
    }
    
    @RequestMapping("/getSectionCodeForList")
    @ResponseBody
    public Map getSectionCode(HttpServletRequest req, Model model) { 
        List list = iservice.getSectionCode(req, model);
        Map map = new HashMap();
        map.put("secList", list);
        return map;
    }

    
    @RequestMapping("/add")
    public String addbasketMaster(Model model) {
        iservice.getProgramCode(model);
        iservice.getSubjectType(model);
        return "basketMaster/basketMasterAdd";
    }

    @RequestMapping("/getBasketMaster")
    @ResponseBody
    public Map getBasketMaster(HttpServletRequest req, Model model) {
        List list = iservice.getBasketMaster(req, model);
        Map map = new HashMap();
        map.put("basket_list", list);
        return map;
    }

    @RequestMapping("/getSemester")
    @ResponseBody
    public Map getSemester(HttpServletRequest req, Model model) {
        List list = iservice.getSemester(req, model);
        Map map = new HashMap();
        map.put("sem_list", list);
        return map;
    }

    @RequestMapping("/getSectionCode")
    @ResponseBody
    public Map getSectionCode_NotSave(HttpServletRequest req, Model model) { 
        List list = iservice.getSectionCode_NotSave(req, model);
        Map map = new HashMap();
        map.put("sec_list", list);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveBasketMaster(HttpServletRequest request) {
        List err = null;
        err = iservice.saveBasketMaster(request);
        return err.get(0).toString();
    }

    @RequestMapping("/edit")
    public String editBasketMaster(Model model,ModelMap mm, HttpServletRequest request) {
        iservice.editBasketMaster(mm, request);
        iservice.getSubjectType(model);
        return "basketMaster/basketMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateBasketMaster(HttpServletRequest request, ModelMap m) {
        List l = iservice.updateBasketMaster(request);
        return l.get(0).toString();
    }

}
