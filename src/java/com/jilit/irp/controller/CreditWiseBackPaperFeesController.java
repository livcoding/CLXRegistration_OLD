/*
    Document   : CreditWiseBackPaperFees
    Created on : 11 JUL : 2019 
    Author     : Malkeet Singh
*/
package com.jilit.irp.controller;

import com.jilit.irp.bso.sis.BackPaperFeesService;
import com.jilit.irp.iservice.BackPaperFeesIService;
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
@RequestMapping("/creditWiseBackPaperFees")
public class CreditWiseBackPaperFeesController {

    @Autowired
    CommonComboIservice commonService;

    @Autowired
    BackPaperFeesIService service;

    @RequestMapping("/list")
    public String getCreditWiseBackPaperFeesList(ModelMap model, HttpServletRequest req) {
        service.styType(model, req);
        return "creditWiseBackPaperFees/creditWiseBackPaperFeesList";
    }

    @RequestMapping("/getGridDate")
    @ResponseBody
    public Map getGridDate(HttpServletRequest request) {
        List list = service.getCreditWiseGridData(request);
        Map map = new HashMap();
        if (list.size() > 0) {
            map.put("gridData", list);
        } else {
            map.put("gridData", null);
        }
        return map;
    }

    @RequestMapping("/add")
    public String getCreditWiseBackPaperFeeAdd(ModelMap model, HttpServletRequest req) {
        service.styType(model, req);
        return "creditWiseBackPaperFees/creditWiseBackPaperFeesAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveCreditWiseBackPaperFees(HttpServletRequest request) {
        List l = service.saveCreditWiseFee(request);
        return l.get(0).toString();
    }

    @RequestMapping("/edit")
    public String getCreditWiseBackPaperFeeEdit(ModelMap model, HttpServletRequest req) {
        service.styType(model, req);
        service.getCreditWiseEditData(model, req);
        return "creditWiseBackPaperFees/creditWiseBackPaperFeesEdit";
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String updateCreditWiseBackPaperFees(HttpServletRequest request) {
        List l = service.updateCreditWiseFee(request);
        return l.get(0).toString();
    }

}
