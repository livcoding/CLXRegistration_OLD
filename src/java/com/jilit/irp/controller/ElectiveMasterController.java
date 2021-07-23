package com.jilit.irp.controller;

import com.jilit.irp.iservice.ElectiveMasterIService;
import com.jilit.irp.util.JIRPSession;
import java.util.List;
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
@RequestMapping("/ElectiveMaster")
public class ElectiveMasterController {

    @Autowired
    JIRPSession jirpsession;
    @Autowired
    ElectiveMasterIService electiveMasterIService;

    @RequestMapping("/list")
    public String getElectiveMaster(ModelMap model) {
        electiveMasterIService.getElectiveMaster(model);
        return "electiveMaster/electiveMasterList";
    }

    @RequestMapping("/add")
    public String addDocumentMaster(ModelMap map) {
        return "electiveMaster/electiveMasterAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveElectiveMaster(HttpServletRequest request) {
        List err = null;
        err = electiveMasterIService.saveElectiveMaster(request);
        return err.get(0).toString();
    }

    @RequestMapping("/edit")
    public String editElectiveMaster(ModelMap mm, HttpServletRequest request) {
        electiveMasterIService.editElectiveMaster(mm, request);
        return "electiveMaster/electiveMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateElectiveMaster(HttpServletRequest request, ModelMap m) {
        List l = electiveMasterIService.updateElectiveMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return electiveMasterIService.checkIfChildExist(request);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteElectiveMaster(HttpServletRequest request) {
        List l = electiveMasterIService.deleteElectiveMaster(request);
        return l.get(0).toString();
    }
}
