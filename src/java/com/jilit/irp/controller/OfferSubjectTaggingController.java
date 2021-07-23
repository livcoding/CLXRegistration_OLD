package com.jilit.irp.controller;

import com.jilit.irp.data.common.CommonComboService;
import com.jilit.irp.iservice.OfferSubjectTaggingIService;
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
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/offereSubjectTagging")
public class OfferSubjectTaggingController {

    @Autowired
    OfferSubjectTaggingIService service;

    @Autowired
    CommonComboService commonService;

    @RequestMapping("/list")
    public String listOfferSubjectTagging(ModelMap model) {
        commonService.getRegistrationCodeLov(model);
        return "offereSubjectTagging/offereSubjectTaggingList";
    }

    @RequestMapping("/getGridData")
    @ResponseBody
    public Map gridData(HttpServletRequest request) {
        List gridList = null;
        gridList = service.getOfferSubjectTaggingListData(request);
        Map map = new HashMap();
        map.put("gridData", gridList);
        return map;
    }

    @RequestMapping("/add")
    public String offerSubjectTaggingAdd(ModelMap model) {
        service.addOfferSubjectTagging(model);
        return "offereSubjectTagging/offereSubjectTaggingAdd";
    }

    @RequestMapping("/getSubjectCode")
    @ResponseBody
    public Map getOldSubjectCode(HttpServletRequest request, ModelMap mm) {
        List l = service.getOldSubjectCode(request);
        Map map = new HashMap();
        map.put("oldSubject", l);
        return map;
    }

    @RequestMapping("/getSubjectComponent")
    @ResponseBody
    public Map getSubjectComponent(HttpServletRequest request) {
        List l = service.getSubjectComponent(request);
        Map map = new HashMap();
        map.put("ComponentList", l);
        return map;
    }
    @RequestMapping("/getFaculty")
    @ResponseBody
    public Map getFaculty(HttpServletRequest request) {
        List l = service.getFacultyList(request);
        Map map = new HashMap();
        map.put("facultyList", l);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String addNewOfferSubjectTagging(HttpServletRequest request) {
        List l = service.addNewOfferSubjectTagging(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String offerSubjectTaggingEdit(ModelMap mm, HttpServletRequest request) {
        mm.put("subComp", service.getAllSubjectComponent());
        mm = service.getAllOfferSubjectTaggingData(mm, request);
        return "offereSubjectTagging/offereSubjectTaggingEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateOfferSubjectTagging(HttpServletRequest request) {
        List l = service.updateOfferSubjectTagging(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteOfferSubjectTagging(HttpServletRequest request) {
        List l = service.deleteOfferSubjectTagging(request);
        return l.get(0).toString();
    }
}
