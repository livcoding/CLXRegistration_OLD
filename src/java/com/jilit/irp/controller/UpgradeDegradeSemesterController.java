package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.UpgradeDegradeSemesterIService;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
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
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/upgardeDegradeSemester")
public class UpgradeDegradeSemesterController {
    
    @Autowired
    UpgradeDegradeSemesterIService service;
    
    @Autowired
    CommonComboIservice commonCombo;
    
    @Autowired
    JIRPSession jirpsession;
    
    @RequestMapping("/list")
    public String getViewUpgardeDegradeSemester(ModelMap model) {
        commonCombo.getAcademicYearDeactive(model);
        commonCombo.getAllProgramMaster(model);
        return "upgradeDegradeSemester/upgradeDegradeSemester";
    }
    
    @RequestMapping("/getBranch")
    @ResponseBody
    public Map getBranchCode(HttpServletRequest request) {
        List branchList = new ArrayList();
        branchList = service.getAllBranchCode(request);
        Map map = new HashMap();
        map.put("branchCode", branchList);
        return map;
    }
    
    @RequestMapping("/getCurrentStyNo")
    @ResponseBody
    public Map getCurrentStyCombo(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = new HashMap();
        try {
            list = service.getSemesterForAcadProgramBranch(request);
            map.put("styList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("/getGridData")
    @ResponseBody
    public Map getAllGridDataUpgradeDegradeSemester(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = null;
        try {
            map = new HashMap();
            list = service.getAllGridData(request);
            if (list.size() > 0) {
                String arr[] = list.get(0).toString().split(",");
                map.put("styNum", arr[6].trim());
            }
            map.put("gridData", list);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public Map saveGridData(HttpServletRequest request) {
        List list = new ArrayList();
        Map map = null;
        try {
            list = service.getSaveGridata(request);
            map = new HashMap();
            map.put("savaGridData", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
