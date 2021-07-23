/*
    Document   : ExamPatternMasterController
    Created on : 8 AUG : 2019 
    Author     : Malkeet Singh
 */
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.ExamPatternMasterService;
import com.jilit.irp.iservice.ExamPatternMasterIService;
import com.jilit.irp.data.common.CommonComboIservice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Malkeet Singh
 */
@Controller
@RequestMapping("/examPatternMaster")
public class ExamPatternMasterController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    ExamPatternMasterIService examPatternService;

    @RequestMapping("/list")
    public String getExamPatternMasterList(Model model) {
        examPatternService.getExamPatternMasterList(model); 
        return "examPatternMaster/examPatternMasterList";
    }

    @RequestMapping("/add")
    public String getExamPatternMasterAdd(ModelMap model, HttpServletRequest req) {
        examPatternService.getComponentCode(model,req); 
        return "examPatternMaster/examPatternMasterAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveExamPatternMaster(HttpServletRequest request) {
        List l = examPatternService.saveExamPatternMaster(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String getExamPatternMasterEdit(ModelMap model, HttpServletRequest req) {
        model = examPatternService.getExamPatternMasterEdit(req,model);
        return "examPatternMaster/examPatternMasterEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateExamPatternMaster(HttpServletRequest request) {
        List l = examPatternService.updateExamPatternMaster(request);
        return l.get(0).toString();
    }

}
