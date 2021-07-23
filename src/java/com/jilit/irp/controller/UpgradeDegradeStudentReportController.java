package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.UpgradeDegradeStudentReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/upgradeDegradeStudentsReports")
public class UpgradeDegradeStudentReportController {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    CommonComboIservice commonCombo;

    @Autowired
    UpgradeDegradeStudentReportIService service;

    @RequestMapping("/list")
    public String getViewUpgradeDegradeStudentReport(ModelMap model) {
        service.getAcademicYearCombo(model);
        commonCombo.getAllProgramMaster(model);
        service.getAllStyCode(model);
        return "upgradeDegradeStudentReport/upgradeDegradeStudentReport";
    }

    @RequestMapping("/getBranchCode")
    @ResponseBody
    public Map getBranchMaster(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = null;
        try {
            list = service.getAllBranchCode(request);
            map = new HashMap();
            map.put("branch", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
