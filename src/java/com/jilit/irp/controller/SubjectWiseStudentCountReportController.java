package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.SubjectWiseStudentCountReportIService;
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
@RequestMapping("/subjectWiseStudentCountReport")
public class SubjectWiseStudentCountReportController {

    @Autowired
    SubjectWiseStudentCountReportIService service;

    @Autowired
    CommonComboIservice commonComboService;

    @RequestMapping("/report")
    public String getViewSubjectWiseStudentCount(ModelMap model) {
        commonComboService.getAllRegsitrationCode(model);
        commonComboService.getAllProgramtype(model);
//        commonComboService.getAcademicYearDeactive(model);
        commonComboService.getAllProgramMaster(model);
        service.getComboSubjectWiseStudentCount(model);
        return "subjectWiseStudentCountReport/subjectWiseStudentCountReport";
    }

    @RequestMapping("/getProgramWithBranchCode")
    @ResponseBody
    public Map getProgramWithBranchCode(HttpServletRequest request) {
        Map map = new HashMap();
        try {
            List list = service.getProgramWithBranchCode(request);
            map.put("programCode", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/getStyNumber")
    @ResponseBody
    public Map getStyNumber(HttpServletRequest request) {
        Map map = new HashMap();
        try {
            List list = service.getStyNumber(request);
            map.put("styNumber", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("/getReport")
    public void getReportSubjectWiseStudentCount(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
    
}
