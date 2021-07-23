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
public interface SubjectWiseStudentListBeforeFinalIService {

    public void getSemesterCode(Model model);


    public List getSubjectCode(HttpServletRequest req);

    public List getAcademicYear(HttpServletRequest req);

    public List getProgramCode(HttpServletRequest req);

    public List getSemester(HttpServletRequest req);

    public List getSectionMaster(HttpServletRequest req);

    public List getsubsection(HttpServletRequest req);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

}
