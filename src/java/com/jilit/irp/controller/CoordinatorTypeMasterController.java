/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.iservice.CoordinatorTypeMasterIService;
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
 * @author priyanka.tyagi
 */
@Controller
@RequestMapping("coordinatorTypeMaster")
public class CoordinatorTypeMasterController {

    @Autowired
    CoordinatorTypeMasterIService CoordinatorTypeMasterIService;

    @RequestMapping("/list")
    public String listCoordintorTypeMaster(Model model) {
        model = CoordinatorTypeMasterIService.getCoordinatorTypeMasterList(model);
        return "coordinatorTypeMaster/coordinatorTypeMasterList";
    }

    @RequestMapping("/add")
    public String coordinatorMasterAdd() {
        return "coordinatorTypeMaster/coordinatorTypeMasterAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String addNewCoordinatorTypeMaster(HttpServletRequest request) {
        List l = CoordinatorTypeMasterIService.addCoordinatorTypeMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String coordinatorTypeMasterEdit(ModelMap mm, HttpServletRequest request) {
        mm = CoordinatorTypeMasterIService.getAllCoordinatorTypeMasterData(mm, request);
        return "coordinatorTypeMaster/coordinatorTypeMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateCoordinatorTypeMaster(HttpServletRequest request) {
        List l = CoordinatorTypeMasterIService.updateCoordinatorTypeMaster(request);
        return l.get(0).toString();
    }
}
