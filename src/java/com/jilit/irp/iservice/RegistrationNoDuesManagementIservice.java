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
public interface RegistrationNoDuesManagementIservice {

    public void getFormData(Model model);

    public List getAcademicYear(HttpServletRequest request);

    public List getAllProgramCode(HttpServletRequest request);

    public List getEnrollmentNo(HttpServletRequest request);

    public List getStudentWiseNoDuesGridData(HttpServletRequest request);

    public List saveStudentWise(HttpServletRequest request);

    public List deleteNoDuesActivity(HttpServletRequest request);

    public List saveAcadWiseRecord(HttpServletRequest request);

}
