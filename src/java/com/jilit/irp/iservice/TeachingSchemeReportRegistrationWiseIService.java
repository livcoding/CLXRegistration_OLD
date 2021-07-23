/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

/**
 *
 * @author ankit.kumar
 */
public interface TeachingSchemeReportRegistrationWiseIService {
    
    public void getAcadmicYearData(Model model);
    
    public void getSemesterdata(Model model);
    
    public List getProgramData(HttpServletRequest model);
    
    public List getSemData(HttpServletRequest model);
    
    public List getSectionData(HttpServletRequest model);
    
   public void getReport(HttpServletRequest request, HttpServletResponse response);
    
}
