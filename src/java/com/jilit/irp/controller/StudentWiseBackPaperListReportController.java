package com.jilit.irp.controller;

import com.jilit.irp.iservice.StudentWiseBackPaperListReportIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/studentWiseBackPaperList")
public class StudentWiseBackPaperListReportController {

    @Autowired
    StudentWiseBackPaperListReportIService service;

    @RequestMapping("/report")
    public String getViewStudentWiseBackPaperListReport(Model model) {
        service.getComboStudentWiseBackPaperList(model);
        return "studentWiseBackPaperListReport/studentWiseBackPaperListReport";
    }

    @RequestMapping("/getBranchCode")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest request) {
        Map map = new HashMap();
        try {
            List list = service.getBranch(request);
            map.put("branchCode", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("/getStyNo")
    @ResponseBody
    public Map getStyNo(HttpServletRequest request) {
        List styno = null;
        styno = service.getStyNo(request);
        Map map = new HashMap();
        map.put("styno", styno);
        return map;
    }

    @RequestMapping("/getReport")
    public void getReportStudentWiseBackPaperListReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
