/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.RegistrationMasterIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/registrationMaster")
public class RegistrationMasterController {

    @Autowired
    RegistrationMasterIService regMasterIservice;

    @RequestMapping("/list")
    public String getRegistrationMaster(Model model) {
        //regMasterIservice.getRegistrationMaster(model);
        regMasterIservice.getRegistrationMasterList(model);
        return "registrationMaster/registrationMasterList";
    }

    @RequestMapping("/add")
    public String addRegistrationMaster(Model model) {
        regMasterIservice.getAcademicyearProgramList(model);
        return "registrationMaster/registrationMasterAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveRegistrationMaster(HttpServletRequest request) {
        List err = null;
        err = regMasterIservice.addRegistrationMaster(request);
        return err.get(0).toString();
    }

    @RequestMapping("/edit")
     
    public String editRegistrationMaster(Model model, HttpServletRequest request) {
        regMasterIservice.editRegistrationMaster(model, request);
        
        
        return "registrationMaster/registrationMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationMaster(HttpServletRequest request) {
        List l = regMasterIservice.updatRegistrationMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return regMasterIservice.checkIfChildExist(request);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRegistrationMaster(HttpServletRequest request) {
        List l = regMasterIservice.deleteRegistrationMaster(request);
        return l.get(0).toString();
    }

//    @RequestMapping("/getRegId")
//    public String getRegistrationID(Model model) {
//        regMasterIservice.getgetRegistrationID(model);
//        return "registrationMaster/registrationMasterList";
//    }

    @RequestMapping("/getRegId")
    @ResponseBody
    public Map getRegistrationID(HttpServletRequest req, Model model) {
        List list = regMasterIservice.getgetRegistrationID(model);
        Map map = new HashMap();
        map.put("sec_list", list);
        return map;
    }
}
