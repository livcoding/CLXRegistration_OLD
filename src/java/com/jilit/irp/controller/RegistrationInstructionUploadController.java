/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import org.springframework.stereotype.Controller;
import com.jilit.irp.iservice.RegistrationInstructionUploadIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author priyanka.tyagi
 */
@Controller
@RequestMapping("/registrationInstructionUpload")
public class RegistrationInstructionUploadController {

    @Autowired
    RegistrationInstructionUploadIService service;

    @RequestMapping("/list")
    public String getRegistratioInstructionList(ModelMap model) {
        service.getRegistratioInstructionList(model);
        return "registrationInstructionUpload/registrationInstructionUploadList";
    }

    @RequestMapping("/check")
    @ResponseBody
    public Map getgriddata(HttpServletRequest request) {
        List list = service.getgriddata(request);
        Map map = new HashMap();
        map.put("griddata", list);
        return map;
    }
//    @RequestMapping("/save")
//    @ResponseBody
//    public String RegistrationInstructionUpload(HttpServletRequest request) {
//        List l = service.saveRegistrationInstructionUpload(request);
//        return l.get(0).toString();
//    }
}
