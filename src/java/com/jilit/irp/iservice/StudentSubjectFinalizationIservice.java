/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
public interface StudentSubjectFinalizationIservice {

    public void getFormData(Model model);

    public List getAcadmicyear(HttpServletRequest request);

    public List getProgram(HttpServletRequest request);

    public List getBranch(HttpServletRequest request);

    public List getSection(HttpServletRequest request);

    public Map getStudentSubjectFinalizationData(HttpServletRequest request);

    public String doSave(HttpServletRequest request);
    
}
