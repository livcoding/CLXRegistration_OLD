package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.phdStudentSubjectRegistrationReportIService;
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
@RequestMapping("/phdStudentSubjectRegistrationReport")
public class phdStudentSubjectRegistrationReportController {

    @Autowired
    phdStudentSubjectRegistrationReportIService phdStudentSubjectRegistrationReportiService;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getAllRegistrationCodeForPHDStudents(ModelMap model) {
        commonService.getRegistrationCodeForReportWise(model);
        return "phdStudentSubjectRegistrationReport/phdStudentSubjectRegistrationReportList";
    }

    @RequestMapping("/getEnrollmentCode")
    @ResponseBody
    public Map getBranchCode(ModelMap model, HttpServletRequest request) {
        List list = phdStudentSubjectRegistrationReportiService.getAllPhdStudents(model, request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("enroll_code", list);
        } else {
            map.put("enroll_code", null);
        }

        return map;
    }

    @RequestMapping("/getBatchWiseStudentList")
    public void getBatchWiseStudentList(HttpServletRequest request, HttpServletResponse response) {
        phdStudentSubjectRegistrationReportiService.getReport(request, response);
    }

}
