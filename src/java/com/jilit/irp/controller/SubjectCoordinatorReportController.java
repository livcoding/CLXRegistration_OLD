package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.SubjectCoordinatorReportIService;
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
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/subjectCoordinatorReport")
public class SubjectCoordinatorReportController {

    @Autowired
    SubjectCoordinatorReportIService subjectCoordinatorReportService;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getSubjectCoorinateReport(ModelMap model) {
        commonService.getRegistrationCodeForReportWise(model);;
        subjectCoordinatorReportService.getCoordinate(model);
        subjectCoordinatorReportService.getAcademicYear(model);
        return "subjectCoordinatorReport/subjectCoordinatorReportList";
    }

    @RequestMapping("getSubCode")
    @ResponseBody
    public Map getSubjectCode(HttpServletRequest request) {
        List l = subjectCoordinatorReportService.getSubCode(request);
        Map map = new HashMap();
        map.put("getSubjectCode", l);
        return map;
    }

    @RequestMapping("getEmp_Type")
    @ResponseBody
    public Map getEmp_Type(HttpServletRequest request) {
        List l = subjectCoordinatorReportService.getEmp_Type(request);
        Map map = new HashMap();
        map.put("emp_list", l);
        return map;
    }

    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        subjectCoordinatorReportService.getReport(request, response);
    }

}
