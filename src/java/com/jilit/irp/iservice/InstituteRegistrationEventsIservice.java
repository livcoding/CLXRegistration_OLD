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
public interface InstituteRegistrationEventsIservice {

    public void getInstituteRegistrationEventsList(Model model);

    public void getSemesterCode(Model model);

    public List addInstituteRegistrationEvents(HttpServletRequest request);

    public void editInstituteRegistrationEvents(Model mm, HttpServletRequest request);

    public List updateInstituteRegistrationEvents(HttpServletRequest request);

    public List deleteInstituteRegistrationEvents(HttpServletRequest request);
    
}
