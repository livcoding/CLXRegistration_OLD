/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Ashutosh1.kumar
 */
public interface AddDropSubjectIService {

    public void getInstituteCodeForAddDrop(ModelMap model);

    public List getSemesterCode(HttpServletRequest request);

    public List getStudentDetail(HttpServletRequest request);

    public Map getActivity(HttpServletRequest request);

    public Map getAddDropSubject(HttpServletRequest request);

    public List delete(HttpServletRequest request);

    public List AddCurrent_BackSubject(HttpServletRequest request);

    public Map getGIPSubject(HttpServletRequest request);

    public List addGipSubject(HttpServletRequest request);

    public List auditSubject(HttpServletRequest request);

    public List saveAuditSubject(HttpServletRequest request);

    public List getStudetnInfo(HttpServletRequest request);

    public List updateRegAllow(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

    public List getAcademicYear(HttpServletRequest request);

    public List getProgram(HttpServletRequest request);

    public List getParameterForAuditSubjectCredit(HttpServletRequest request);

    public List getElectiveSubjectForSwap(HttpServletRequest request);

    public List changeElectiveSubject(HttpServletRequest request);

    public String datacheckfordelete(HttpServletRequest req);
}
