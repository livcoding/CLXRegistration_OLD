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
public interface DropElectiveSubjectIservice {

    public void getFormData(Model model);

    public List getSubjectType(HttpServletRequest request);

    public List getSubjectCode(HttpServletRequest request);

    public List getGridData(HttpServletRequest request);

    public List getAllProcessForDropping(HttpServletRequest request);
    
}
