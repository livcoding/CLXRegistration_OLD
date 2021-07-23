/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface DepartmentSubjectTaggingIService {
    
    public void getDepartmentSubjectList(ModelMap map);
    
    public List getDepartmentwiseSubjectList(HttpServletRequest req,ModelMap map);
    
    public void getAllDepartmentCode(ModelMap map);
    
    public void getReqSubjectForBranchChange(ModelMap map);
    
    public List saveDepertmentSubject(HttpServletRequest req);
    
    public List deleteDepartmentSubject(HttpServletRequest req);
    
}
