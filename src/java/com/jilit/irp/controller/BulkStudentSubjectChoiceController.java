package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.BulkStudentSubjectChoiceIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nazar.Mohammad
 */
@Controller
@RequestMapping("/bulkStudentSubjectChoice")
public class BulkStudentSubjectChoiceController {

    @Autowired
    BulkStudentSubjectChoiceIService bulkStudentSubjectChoiceIService;

    @Autowired
    CommonComboIservice commonComboIservice;

    @RequestMapping("/list")
    public String getRegistrationCode(ModelMap model) {
        commonComboIservice.getRegistrationCodeLov(model);
        return "bulkStudentSubjectChoice/bulkStudentSubjectChoice";
    }

    @RequestMapping("/getQueryCriteriaData")
    @ResponseBody
    public Map getQueryCriteriaData(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getQueryCriteriaData(request);
        Map map = new HashMap();
        map.put("acadYearList", l);
        return map;
    }

    @RequestMapping("/getProgram")
    @ResponseBody
    public Map getProgram(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getProgramCode(request);
        Map map = new HashMap();
        map.put("programList", l);
        return map;
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranch(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getBranchCode(request);
        List l1 = bulkStudentSubjectChoiceIService.getStynumber(request);
        Map map = new HashMap();
        map.put("branchList", l);
        map.put("styList", l1);
        return map;
    }

    @RequestMapping("/getSection")
    @ResponseBody
    public Map getSection(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getSectionCode(request);
        Map map = new HashMap();
        map.put("sectionList", l);
        return map;
    }

    @RequestMapping("/getCoreSubject")
    @ResponseBody
    public Map getCoreSubject(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getSubjectData(request);
        Map map = new HashMap();
        map.put("coreSubjectList", l);
        return map;
    }

    @RequestMapping("/electiveSubjectData")
    @ResponseBody
    public Map electiveSubjectData(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.getSubjectData(request);
        Map map = new HashMap();
        map.put("electiveSubjectList", l);
        return map;
    }

    @RequestMapping("/getStudentsList")
    @ResponseBody
    public Map getStudentsList(HttpServletRequest request) {
        Map l = bulkStudentSubjectChoiceIService.getStudentsList(request);
        return l;
    }

    @RequestMapping("/doSaveBulkStudents")
    @ResponseBody
    public Map doSaveBulkStudents(HttpServletRequest request) {
        List l = bulkStudentSubjectChoiceIService.doSaveBulkStudents(request);
        Map map = new HashMap();
        map.put("griddata", l);
        return map;
    }

}
