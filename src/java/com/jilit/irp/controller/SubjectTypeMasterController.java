package com.jilit.irp.controller;

import com.jilit.irp.iservice.SubjectTypeMasterIService;
import com.jilit.irp.util.JIRPSession;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ankur.goyal
 */
@Controller
@RequestMapping("/subjectTypeMaster")
public class SubjectTypeMasterController {

    @Autowired
    SubjectTypeMasterIService subjectTypeMasterService;

    @Autowired
    JIRPSession jirpsession;

    @RequestMapping("/list")
    public String ListSubjectTypeMaster(Model model) {
        subjectTypeMasterService.getSubjectTypeMasterList(model);
        return "subjectTypeMaster/subjectTypeMasterList";
    }
    
    @RequestMapping("/add")
    public String AddSubjectTypeMaster(){
        return "subjectTypeMaster/subjectTypeMasterAdd";
    }
    
    @RequestMapping("/edit")
    public String EditSubjectTypeMaster(HttpServletRequest request, ModelMap mm){
        mm = subjectTypeMasterService.getSubjectTypeMasterEdit(request, mm);
        return "subjectTypeMaster/subjectTypeMasterEdit";
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public String SaveSubjectTypeMaster(HttpServletRequest request){
        List l = subjectTypeMasterService.getSubjectTypeMasterSave(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String UpdateSubjectTypeMaster(HttpServletRequest request){
        List l = subjectTypeMasterService.getSubjectTypeMasterUpdate(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public String DeleteubjectTypeMaster(HttpServletRequest request) {
        List l = subjectTypeMasterService.getSubjectTypeMasterDelete(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return subjectTypeMasterService.checkIfChildExist(request);
    }
}
