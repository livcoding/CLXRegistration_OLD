package com.jilit.irp.controller;

import com.jilit.irp.iservice.SemesterTypeMasterIService;
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
@RequestMapping("/semesterTypeMaster")
public class SemesterTypeMasterController {

    @Autowired
    SemesterTypeMasterIService semesterTypeMasterService;

    @Autowired
    JIRPSession jirpsession;

    @RequestMapping("/list")
    public String ListSemesterTypeMaster(Model model) {
        semesterTypeMasterService.getSemesterTypeMasterList(model);
        return "semesterTypeMaster/semesterTypeMasterList";
    }
    
    @RequestMapping("/add")
    public String AddSemesterTypeMaster(){
        return "semesterTypeMaster/semesterTypeMasterAdd";
    }
    
    @RequestMapping("/edit")
    public String EditSemesterTypeMaster(HttpServletRequest request, ModelMap mm){
        mm = semesterTypeMasterService.getSemesterTypeMasterEdit(request, mm);
        return "semesterTypeMaster/semesterTypeMasterEdit";
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public String SaveSemesterTypeMaster(HttpServletRequest request){
        List l = semesterTypeMasterService.getSemesterTypeMasterSave(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String UpdateSemesterTypeMaster(HttpServletRequest request){
        List l = semesterTypeMasterService.getSemesterTypeMasterUpdate(request);
        return l.get(0).toString();
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public String DeletSemesterTypeMaster(HttpServletRequest request) {
        List l = semesterTypeMasterService.getSemesterTypeMasterDelete(request);
        return l.get(0).toString();
    }

    @RequestMapping("/checkIfChildExist")
    @ResponseBody
    public String checkIfChildExist(HttpServletRequest request) {
        return semesterTypeMasterService.checkIfChildExist(request);
    }
}
