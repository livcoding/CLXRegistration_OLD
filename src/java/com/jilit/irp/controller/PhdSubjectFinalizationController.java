package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.PhdSubjectFinalizationIService;
import com.jilit.irp.util.JIRPSession;
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
 * @author ankit.kumar
 */
@Controller
@RequestMapping("/phdSubjectFinalization")
public class PhdSubjectFinalizationController {

    @Autowired
    JIRPSession jirpsession;

    @Autowired
    PhdSubjectFinalizationIService phdSubjectFinalizationIService;

    @Autowired
    CommonComboIservice commonService;

    @RequestMapping("/list")
    public String getRegistrationCode(ModelMap model) {
        commonService.getRegistrationCodeLov(model);
        return "phdSubjectFinalization/phdSubjectFinalizationList";
    }

    @RequestMapping("/getPendingData")
    @ResponseBody
    public Map getPendingForApprovalData(ModelMap model, HttpServletRequest request) {
        List list = phdSubjectFinalizationIService.getPendingForApprovalData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("pendingData", list);
        } else {
            map.put("pendingData", null);
        }

        return map;
    }

    @RequestMapping("/getApproveData")
    @ResponseBody
    public Map getApproveData(ModelMap model, HttpServletRequest request) {
        List list = phdSubjectFinalizationIService.getApprovedData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("getApproveData", list);
        } else {
            map.put("getApproveData", null);
        }

        return map;
    }

    @RequestMapping("/getCancelledData")
    @ResponseBody
    public Map getCancelledData(ModelMap model, HttpServletRequest request) {
        List list = phdSubjectFinalizationIService.getCanclledData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("getCancelledData", list);
        } else {
            map.put("getCancelledData", null);
        }
        return map;
    }

    @RequestMapping("/getReportData")
    @ResponseBody
    public Map getReportData(ModelMap model, HttpServletRequest request) {
        List list = phdSubjectFinalizationIService.getSubjectReportData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("getReportData", list);
        } else {
            map.put("getReportData", null);
        }

        return map;
    }

    @RequestMapping("/savePending")
    @ResponseBody
    public Map savePendingForApprove(HttpServletRequest request) {
        List err = null;
        err = phdSubjectFinalizationIService.savePendingForApprove_Approve(request);
        Map m = new HashMap();
        String status = null;
        if (err.size() > 1) {
            status = err.get(0).toString() + "~@~" + err.get(1).toString();
        } else {
            status = err.get(0).toString();
        }
        m.put("status", status);
        return m;
    }

    @RequestMapping("/saveCancelledData")
    @ResponseBody
    public Map saveCancelledData(HttpServletRequest request) {
        List err = null;
        err = phdSubjectFinalizationIService.saveCancelledData(request);
        Map m = new HashMap();
        String status = err.get(0).toString();
        m.put("status", status);
        return m;
    }

}
