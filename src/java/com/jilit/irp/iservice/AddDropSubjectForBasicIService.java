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
 * @author ashutosh1.kumar
 */
public interface AddDropSubjectForBasicIService {

    public void getInstituteCodeForAddDrop(ModelMap model);

    public List getSemesterCode(HttpServletRequest request);

    public List getStudetnInfo(HttpServletRequest request);

    public List getStudentDetail(HttpServletRequest request);

    public List assignData(HttpServletRequest request);

    public Map getActivity(HttpServletRequest request);

    public Map getAddDropSubject(HttpServletRequest request);

    public List delete(HttpServletRequest request);

    public List AddCurrent_BackSubject(HttpServletRequest request);

    public List updateRegAllow(HttpServletRequest request);

    public List getAcademicYear(HttpServletRequest request);

    public List getProgram(HttpServletRequest request);

    public List getFacultyList(HttpServletRequest request);

    public List saveLoadDistribution(HttpServletRequest request);
}
