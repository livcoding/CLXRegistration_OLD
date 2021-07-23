package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.PreRequisiteForRegistrationIService;
import com.jilit.irp.iservice.SubjectFacultyCumCoordinatorWiseStudentListIService;
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
@RequestMapping("/subjectFacultyCumCoordinatorWiseStudentList")
public class SubjectFacultyCumCoordinatorWiseStudentListController {

    @Autowired
    SubjectFacultyCumCoordinatorWiseStudentListIService service;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getListSubjectFacultyCumCoordinatorWiseStudentList(ModelMap model) {
        service.getRegistrationCombo(model);
        return "subjectFaculty-CoordinatorWiseStudentList/subjectFacultyCumCoordinatorWiseStudentList";
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getComboSubjectCode(HttpServletRequest request) {
        List list = new ArrayList();
        list = service.getSubjectCodeCombo(request);
        Map map = new HashMap();
        map.put("subCode", list);
        return map;
    }

    @RequestMapping("/getCoordinatotCode")
    @ResponseBody
    public Map getCoordinatorCodeCombo(HttpServletRequest request) {
        List cordCode = new ArrayList();
        cordCode = service.getCoordinatorCodeCombo(request);
        Map map = new HashMap();
        map.put("cordCode", cordCode);
        return map;
    }

    @RequestMapping("getReport")
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }

}
