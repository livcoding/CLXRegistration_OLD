package com.jilit.irp.controller;

import com.jilit.irp.iservice.OldvsNewSubjectIService;
import java.util.List;
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
@RequestMapping("/oldVsNewSubject")
public class OldvsNewSubjectController {

    @Autowired
    OldvsNewSubjectIService service;

    @RequestMapping("/list")
    public String listOldvsNewSubject(ModelMap model) {
        service.getAllOldvsNewSubject(model);
        return "oldvsNewSubject/oldvsNewSubjectList";
    }

    @RequestMapping("/add")
    public String addOldvsNewSubject(ModelMap model) {
        service.getAllCombo(model);
        return "oldvsNewSubject/oldvsNewSubjectAdd";
    }

    @RequestMapping("/edit")
    public String editOldvsNewSubject(HttpServletRequest request, ModelMap model) {
        service.getEditOldvsNewSubject(request, model);
        return "oldvsNewSubject/oldvsNewSubjectEdit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveOldvsNewSubject(HttpServletRequest request) {
        List l = service.getSaveOldvsNewSubject(request);
        return l.get(0).toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateOldvsNewSubject(HttpServletRequest request) {
        List l = service.getUpdateOldvsNewSubject(request);
        return l.get(0).toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deletOldvsNewSubject(HttpServletRequest request) {
        List l = service.getDeleteOldvsNewSubject(request);
        return l.get(0).toString();
    }
}
