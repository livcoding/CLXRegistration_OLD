package com.jilit.irp.controller;

import com.jilit.irp.iservice.BackPaperReportIService;
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
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/backPaperReport")
public class BackPaperReportController {

    @Autowired
    BackPaperReportIService service;

    @RequestMapping("/list")
    public String backPaperReportList(Model model) {
        service.getProgramList(model);
        return "backPaperReport/backPaperReport";
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranchList(HttpServletRequest req) {
        List branchList = service.getBranch(req);
        Map map = new HashMap();
        map.put("branch", branchList);
        return map;
    }
  
    @RequestMapping("/getSubject")
    @ResponseBody
    public Map getSubjectList(HttpServletRequest req){
    List subList = service.getSubject(req);
     Map map = new HashMap();
        map.put("subject", subList);
        return map;
    }
    
    @RequestMapping("/backPaperReport")
    public void getbackPaperReport(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }
}
