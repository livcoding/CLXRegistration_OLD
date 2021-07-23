package com.jilit.irp.controller;

import com.jilit.irp.iservice.PreRequisiteForRegistrationReportIService;
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
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/preRequisiteForPromotionReport")
public class PreRequisiteForRegistrationReportController {

    @Autowired
    PreRequisiteForRegistrationReportIService service;

    @RequestMapping("/list")
    public String getListPreRequisiteForRegistrationReport(ModelMap model) {
        service.getCombo(model);
        return "preRequisiteForRegistrationReport/preRequisiteForRegistrationReport";
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest request) {
        List list = new ArrayList();
        list = service.getBranchCode(request);
        Map map = new HashMap();
        map.put("branchCode", list);
        return map;
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getGridData(HttpServletRequest request) {
        List list = new ArrayList();
        list = service.getAllPreRequisiteForRegistrationReportData(request);
        Map map = new HashMap();
        map.put("gridData", list);
        return map;
    }
}
