package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.ForcefullyRegistrationIService;
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
@RequestMapping("/forcefullyRegistration")
public class ForcefullyRegistrationController {

    @Autowired
    ForcefullyRegistrationIService iservice;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String basketMaster(ModelMap model) {
        commonService.getRegistrationCodeLov(model);
        iservice.getProgramCode(model);
        return "forcefullyRegistration/forcefullyRegistration";
    }

    @RequestMapping("/getBranchCode")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest req, Model model) {
        List list = iservice.getBranchCode(req, model);
        Map map = new HashMap();
        map.put("branch_list", list);
        return map;
    }

    @RequestMapping("/getRegistrationNumber")
    @ResponseBody
    public Map getRegistrationNumber(HttpServletRequest req, Model model) {
        List list = iservice.getRegistrationNumber(req, model);
        Map map = new HashMap();
        map.put("reg_list", list);
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateForcefullyRegistration(HttpServletRequest request, ModelMap m) {
        List l = iservice.updateForcefullyRegistration(request);
        return l.get(0).toString();
    }

}
