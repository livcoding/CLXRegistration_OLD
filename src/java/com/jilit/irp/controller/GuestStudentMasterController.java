/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboIservice;
import com.jilit.irp.iservice.GuestStudentMasterIService;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author priya.sharma
 */
@Controller
@RequestMapping("/guestStudentMaster")
public class GuestStudentMasterController {

    @Autowired
    JIRPSession jirpession;

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    GuestStudentMasterIService guestStudentMasterService;

    @RequestMapping("/list")
    public String getList(ModelMap model) {
        //guestStudentMasterService.getInstituteName(model);
        guestStudentMasterService.getGuestInstituteName(model);
        return "guestStudentMaster/guestStudentMasterList";
    }

    @RequestMapping("/getAllStudentMaster")
    @ResponseBody
    public Map getAllStudentMaster(HttpServletRequest req) {
        List list = new ArrayList();
        list = guestStudentMasterService.getAllStudentMaster(req);
        Map map = new HashMap();
        map.put("studentData", list);
        return map;
    }

    @RequestMapping("/personalInfoSave")
    @ResponseBody
    public Map personalInfoSave(HttpServletRequest req, ModelMap m) {
        guestStudentMasterService.personalInfoSave(req, m);
        Map map = new HashMap();
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/addressDetailSave")
    @ResponseBody
    public Map addressDetailSave(HttpServletRequest req, ModelMap m) {
        guestStudentMasterService.addressDetailSave(req, m);
        Map map = new HashMap();
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/studentPhotoSignatureSave")
    @ResponseBody
    public Map studentPhotoSignatureSave(HttpServletRequest req, ModelMap m, final @RequestParam(value = "file", required = false) CommonsMultipartFile[] attachment,
            final @RequestParam(value = "sign", required = false) CommonsMultipartFile[] sign) {
        guestStudentMasterService.studentPhotoSignatureSave(req, m, attachment, sign);
        Map map = new HashMap();
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/add")
    public String guestStudentMasterAdd(ModelMap model) {
        // guestStudentMasterService.getInstituteName(model);
        guestStudentMasterService.getAcademicYears(model);
        List country = commonService.countryCode_TSM();
        model.put("country", country);
        return "guestStudentMaster/guestStudentMasterAdd";
    }

    @RequestMapping("/edit")
    public String guestStudentMasterEdit(ModelMap model, HttpServletRequest req) {
        // guestStudentMasterService.getInstituteName(model);
        guestStudentMasterService.getAcademicYears(model);
//        List program = commonService.getProgramMaster();
//        model.put("program", program);
        List country = commonService.countryCode_TSM();
        model.put("country", country);
        guestStudentMasterService.editStudentMaster(model, req);
        return "guestStudentMaster/guestStudentMasterEdit";
    }

    @RequestMapping("/personalInfoUpdate")
    @ResponseBody
    public Map personalInfoUpdate(HttpServletRequest req, ModelMap m) {
        guestStudentMasterService.personalInfoUpdate(req, m);
        Map map = new HashMap();    
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/addressDetailUpdate")
    @ResponseBody
    public Map addressDetailUpdate(HttpServletRequest req, ModelMap m) {
        guestStudentMasterService.addressDetailUpdate(req, m);
        Map map = new HashMap();
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/studentPhotoSignatureUpdate")
    @ResponseBody
    public Map studentPhotoSignatureUpdate(HttpServletRequest req, ModelMap m, final @RequestParam(value = "file", required = false) CommonsMultipartFile[] attachment,
            final @RequestParam(value = "sign", required = false) CommonsMultipartFile[] sign) {
        guestStudentMasterService.studentPhotoSignatureUpdate(req, m, attachment, sign);
        Map map = new HashMap();
        map.put("status", m.get("result"));
        map.put("guestStudentId", m.get("guestStudentId"));
        return map;
    }

    @RequestMapping("/getStateOfCountry")
    @ResponseBody
    public Map getStateOfCountry(HttpServletRequest req) {
        String countryId = req.getParameter("cntryId");
        List list = commonService.stateCode_TSM(countryId);
        Map map = new HashMap();
        map.put("state", list);
        return map;
    }

    @RequestMapping("/getCityOfState")
    @ResponseBody
    public Map getCityOfState(HttpServletRequest req) {
        String countryId = req.getParameter("cntryId");
        String stateId = req.getParameter("stateId");
        List list = commonService.cityCode_TSM(countryId, stateId);
        Map map = new HashMap();
        map.put("city", list);
        return map;
    }

}
