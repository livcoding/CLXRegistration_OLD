package com.jilit.irp.controller;

import com.jilit.irp.iservice.EligibleStudentsForUpcomingSemesterIService;
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
@RequestMapping("/eligibleStudentsForUpcomingSemesterReport")
public class EligibleStudentsForUpcomingSemesterController {

    @Autowired
    EligibleStudentsForUpcomingSemesterIService service;

    @RequestMapping("/list")
    public String eligibleStudentsForUpcomingSemesterList(Model model) {
        service.getRegAndProgramList(model);
        return "eligibleStudentsForUpcomingSemesterReport/eligibleStudentsForUpcomingSemesterReport";
    }

    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranchList(HttpServletRequest req) {
        List list = service.getBranch(req);
        Map map = new HashMap();
        map.put("branch", list);
        return map;
    }

    @RequestMapping("/eligibleStudentsForUpcomingSemesterReport")
    public void eligibleStudentsForUpcomingSemester(HttpServletRequest request, HttpServletResponse response) {
        service.getReport(request, response);
    }

}
