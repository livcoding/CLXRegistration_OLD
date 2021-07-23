package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.SubjectWiseStudentListReportIService;
import com.jilit.irp.util.JIRPSession;
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
@RequestMapping("/subjectWiseStudentListReport")
public class SubjectWiseStudentListReportController {

    @Autowired
    JIRPSession jirpsession;

    @Autowired
    SubjectWiseStudentListReportIService subjectWiseStudentListReportIService;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String subjectWiseStudentListReport(ModelMap model) {
        commonService.getRegistrationCodeForReportWise(model);
        return "subjectWiseStudentListReport/subjectWiseStudentListReport";
    }

    @RequestMapping("/getDepartmentCode")
    @ResponseBody
    public Map getDepartmentCode(HttpServletRequest req) {
        List list = subjectWiseStudentListReportIService.getDepartmentCode(req);
        Map map = new HashMap();
        map.put("dep_list", list);
        return map;
    }

    @RequestMapping("/getEmployeeCode")
    @ResponseBody
    public Map getEmployeeCode(HttpServletRequest req) {
        List list = subjectWiseStudentListReportIService.getEmployeeCode(req);
        Map map = new HashMap();
        map.put("emp_list", list);
        return map;
    }

    @RequestMapping("/getAllSubjectWiseStudentList")
    @ResponseBody
    public Map getAllSubjectWiseStudentList(HttpServletRequest req) {
        List list = subjectWiseStudentListReportIService.getAllSubjectWiseStudentList(req);
        Map map = new HashMap();
        map.put("sub_list", list);
        return map;
    }

    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        subjectWiseStudentListReportIService.getReport(request, response);
    }

}
