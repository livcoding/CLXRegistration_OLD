/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
public interface BatchWiseRegistrationProcessDateIservice {

    public void getFormData(Model model);

    public List getAcademicYear(HttpServletRequest request);

    public List getProgram(HttpServletRequest request);

    public List getBranch(HttpServletRequest request);

    public List getGridData(HttpServletRequest request);

    public List saveAcadWiseRegistration(HttpServletRequest request);
    
}
