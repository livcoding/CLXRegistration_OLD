package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.SummerRegistrationReportIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/summerRegistrationReport")
public class SummerRegistrationReportController {

    @Autowired
    SummerRegistrationReportIService summerRegistrationReportService;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/report")
    public String getComboSummerRegistrationReport(ModelMap model) {
        commonService.getRegistrationCodeForReportWise(model);
        return "summerRegistrationReport/summerRegistrationReport";
    }

    @RequestMapping("/getEnrollmentCode")
    @ResponseBody
    public Map getEnrollmentCode(HttpServletRequest request) {
        Map map = new HashMap();
        List list = summerRegistrationReportService.getEnrollmentNumber(request);
        map.put("enrollmentCode", list);
        return map;
    }

    @RequestMapping("/getReport")
    public void getSummerRegistrationReport(HttpServletRequest request, HttpServletResponse response) {
        summerRegistrationReportService.getReport(request, response);;
    }
}
