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
 * @author deepak.gupta
 */
public interface ElectiveSubjectAllocationStatusIService {
    
    public void getRegistrationList(Model model);
    
    public List getSubjectType(HttpServletRequest req);

    void getReport(HttpServletRequest request, HttpServletResponse response);
    
}
