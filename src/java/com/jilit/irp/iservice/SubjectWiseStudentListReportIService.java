/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ankit.kumar
 */
public interface SubjectWiseStudentListReportIService {

    public void getRegistraionCode(Model model, HttpServletRequest req);

    public List getDepartmentCode(HttpServletRequest req);

    public List getEmployeeCode(HttpServletRequest req);

    public List getAllSubjectWiseStudentList(HttpServletRequest req);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

}
