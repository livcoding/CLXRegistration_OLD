/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;
import com.jilit.irp.iservice.InstituteRegistrationEventsIservice;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Controller
@RequestMapping("/instituteRegistrationEvents")
public class InstituteRegistrationEventsController {

    @Autowired
    InstituteRegistrationEventsIservice insRegIservice;

    @RequestMapping("/list")
    public String getRegistrationMaster(Model model) {
        insRegIservice.getInstituteRegistrationEventsList(model);
        return "instituteRegistrationEvents/instituteRegistrationEventsList";
    }

    @RequestMapping("/add")
    public String addRegistrationMaster(Model model) {
        insRegIservice.getSemesterCode(model);
        return "instituteRegistrationEvents/instituteRegistrationEventsAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveRegistrationMaster(HttpServletRequest request) {
        List err = null;
        err = insRegIservice.addInstituteRegistrationEvents(request);
        return err.get(0).toString();
    }

    @RequestMapping("/edit")
    public String editRegistrationMaster(Model mm, HttpServletRequest request) {
        insRegIservice.editInstituteRegistrationEvents(mm, request);
        return "instituteRegistrationEvents/instituteRegistrationEventsEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateRegistrationMaster(HttpServletRequest request) {
        List l = insRegIservice.updateInstituteRegistrationEvents(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRegistrationMaster(HttpServletRequest request) {
        List l = insRegIservice.deleteInstituteRegistrationEvents(request);
        return l.get(0).toString();
    }

}
