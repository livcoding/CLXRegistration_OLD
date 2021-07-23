package com.jilit.irp.controller;

import com.jilit.irp.iservice.FacultySubjectTaggingIService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/facultySubjectTagging")
public class FacultySubjectTaggingController extends HttpServlet {

    @Autowired
    FacultySubjectTaggingIService facultySubjectTaggingIService;

    @Autowired
    HttpServletRequest reqquest;
    @Autowired
    HttpServletRequest reqquest1;

    @RequestMapping("/list")
    public String facultySubjectTagging(ModelMap model, HttpServletRequest req) {
        facultySubjectTaggingIService.getRegistraionCode(model, req);
        facultySubjectTaggingIService.getDepartmentCode(model, req);
        return "facultySubjectTagging/facultySubjectTaggingList";
    }

    @RequestMapping("/getAcadyear")
    @ResponseBody
    public Map getAcadmicyear(HttpServletRequest request) {
        List err = null;
        err = facultySubjectTaggingIService.getAcadmicyear(request);
        String regid_arr[] = request.getParameter("regId").split(",");
        Map map = new HashMap();
        map.put("acadyear", err);
        return map;
    }

    @RequestMapping("/getSubject_FacultyCode")
    @ResponseBody
    public Map getSubject_FacultyCode(HttpServletRequest request) {
        List list = facultySubjectTaggingIService.getSubject_FacultyCode(request);
        Map map = new HashMap();
        map.put("subjectCode", list.get(0));
        map.put("facultyCode", list.get(1));
        return map;
    }

    @RequestMapping("/getComponentCode")
    @ResponseBody
    public Map getComponentCode(HttpServletRequest request) {
        List list = null;
        list = facultySubjectTaggingIService.getComponentCode(request);
        Map map = new HashMap();
        map.put("componentCode", list);
        return map;
    }

    @RequestMapping("/getProgramCode")
    @ResponseBody
    public Map getProgramCode(HttpServletRequest request) {
        List err = null;
        err = facultySubjectTaggingIService.getProgramCode(request);
        Map map = new HashMap();
        map.put("pr_code", err);
        return map;
    }

    @RequestMapping("/getSectionCode")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest request) {
        List err = null;
        err = facultySubjectTaggingIService.getSectionCode(request);
        Map map = new HashMap();
        map.put("br_code", err);
        return map;
    }

    @RequestMapping("/getSemesterCode")
    @ResponseBody
    public Map getSemesterCode(HttpServletRequest request) {
        List err = null;
        err = facultySubjectTaggingIService.getSemesterCode(request);
        Map map = new HashMap();
        map.put("sem_code", err);
        return map;
    }

    @RequestMapping("/getFstList")
    @ResponseBody
    public Map getFstList(HttpServletRequest request) {
        List err = null;
        err = facultySubjectTaggingIService.getFstList(request);
        Map map = new HashMap();
        map.put("fst_list", err);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map saveGridData(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = null;
        try {
            String radioValue = request.getParameter("radioValue");
            if (radioValue != null && radioValue.equals("B")) {
                list = facultySubjectTaggingIService.getSaveGridata(request);
            } else {
                list = facultySubjectTaggingIService.saveFacultySubjectTagging(request);
            }
            map = new HashMap();
            map.put("savaGridData", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
