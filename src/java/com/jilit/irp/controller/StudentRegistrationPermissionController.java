package com.jilit.irp.controller;

import com.jilit.irp.iservice.StudentRegistrationPermissionIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author mohit1.kumar
 */
@Controller
@RequestMapping("/stdRegPermsn")
public class StudentRegistrationPermissionController {

    @Autowired
    StudentRegistrationPermissionIService studentRegistrationPermissionService;

    @RequestMapping("/list")
    public String studentRegistrationPermission(ModelMap model) {
        studentRegistrationPermissionService.getSemesterCode(model);
        studentRegistrationPermissionService.getStyType(model);
        studentRegistrationPermissionService.getAcademicYearReg(model);
        return "studentRegisPermission/studentRegistrationPermission";
    }

    @RequestMapping("/getFromToDate")
    @ResponseBody
    public Map getFromToDate(HttpServletRequest request) {
        List l = studentRegistrationPermissionService.getFromToDate(request);
        Map map = new HashMap();
        map.put("fromToDate", l);
        return map;
    }

    @RequestMapping("/getProgramCodeReg")
    @ResponseBody
    public Map getProgramCodeReg(HttpServletRequest request) {
        List l = studentRegistrationPermissionService.getProgramCodeReg(request);
        Map map = new HashMap();
        map.put("programList", l);
        return map;
    }

    @RequestMapping("/getSecCodeReg")
    @ResponseBody
    public Map getSecCodeReg(HttpServletRequest request) {
        List l = studentRegistrationPermissionService.getSecCodeReg(request);
        Map map = new HashMap();
        map.put("section", l);
        return map;
    }

    @RequestMapping("/getRegNum")
    @ResponseBody
    public Map getRegNum(HttpServletRequest request) {
        List l = studentRegistrationPermissionService.getAllEnrollmentNo(request);
        Map map = new HashMap();
        map.put("regNo", l);
        return map;
    }

    @RequestMapping("/getStudetnInfo")
    @ResponseBody
    public Map getStudetnInfo(HttpServletRequest request) {
        List l = studentRegistrationPermissionService.getStudetnInfo(request);
        Map map = new HashMap();
        map.put("studentInfo", l);
        return map;
    }

    @RequestMapping("/loadData")
    @ResponseBody
    public Map loadData(HttpServletRequest request) {
        Map map = new HashMap();
        Map criteriaMap = new HashMap();
        try {
            criteriaMap.put("registrationid", request.getParameter("registrationid"));
            criteriaMap.put("acadYear", request.getParameter("acadYear"));
            criteriaMap.put("prgCode", request.getParameter("prgCode"));
            criteriaMap.put("branchCode", request.getParameter("branchCode"));
            criteriaMap.put("styType", request.getParameter("styType"));
            criteriaMap.put("indBulk", request.getParameter("flag"));
            criteriaMap.put("regSem", request.getParameter("regSem"));
            criteriaMap.put("newReg", request.getParameter("newReg" ));
            String flag = request.getParameter("flag");
            boolean regOnly = false;
            if (flag.equalsIgnoreCase("I")) {
                regOnly = true;
                criteriaMap.put("studentid", request.getParameter("studentId"));
            } else {
                criteriaMap.put("selectedAcadyearPrgBrnCriteria", "selectedAcadyearPrgBrnCriteria");
            }
            List l = studentRegistrationPermissionService.doSubmit(criteriaMap, false, regOnly);
            map.put("gridData", l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(HttpServletRequest request) {
        Map map = new HashMap();
        List l = studentRegistrationPermissionService.saveAndUpdate(request);
        return l.get(0).toString();
    }

    @RequestMapping("/deleteSSM")
    @ResponseBody
    public Map deleteSSM(HttpServletRequest request) {
        Map map = new HashMap();
        Map criteriaMap = new HashMap();
        try {
            criteriaMap.put("registrationid", request.getParameter("registrationid"));
            criteriaMap.put("acadYear", request.getParameter("acadYear"));
            criteriaMap.put("prgCode", request.getParameter("prgCode"));
            criteriaMap.put("branchCode", request.getParameter("branchCode"));
            criteriaMap.put("styType", request.getParameter("styType"));
            criteriaMap.put("indBulk", request.getParameter("flag"));
            criteriaMap.put("regSem", request.getParameter("regSem"));
            String flag = request.getParameter("flag");
            boolean regOnly = false;
            if (flag.equalsIgnoreCase("I")) {
                regOnly = true;
                criteriaMap.put("studentid", request.getParameter("studentId"));
            }
            List l = studentRegistrationPermissionService.deleteStudentSubjectChoiceMasterWithChild(criteriaMap, false, regOnly);
            map.put("gridData", l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/deleteSR")
    @ResponseBody
    public Map deleteSR(HttpServletRequest request) {
        Map map = new HashMap();
        Map criteriaMap = new HashMap();
        try {
            criteriaMap.put("registrationid", request.getParameter("registrationid"));
            criteriaMap.put("acadYear", request.getParameter("acadYear"));
            criteriaMap.put("prgCode", request.getParameter("prgCode"));
            criteriaMap.put("branchCode", request.getParameter("branchCode"));
            criteriaMap.put("styType", request.getParameter("styType"));
            criteriaMap.put("indBulk", request.getParameter("flag"));
            criteriaMap.put("regSem", request.getParameter("regSem"));
            String flag = request.getParameter("flag");
            boolean regOnly = false;
            if (flag.equalsIgnoreCase("I")) {
                regOnly = true;
                criteriaMap.put("studentid", request.getParameter("studentId"));
            }
            List l = studentRegistrationPermissionService.deleteStudentSubjectChoiceMasterWithChild(criteriaMap, false, regOnly);
            map.put("gridData", l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
