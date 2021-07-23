/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author mohit1.kumar
 */
public interface StudentRegistrationPermissionIService {

    public void getSemesterCode(ModelMap model);

    public List getStudetnInfo(HttpServletRequest request);

    public void getStyType(ModelMap model);

    public void getAcademicYearReg(ModelMap model);

    public List getProgramCodeReg(HttpServletRequest request);

    public List getSecCodeReg(HttpServletRequest request);

    public List getFromToDate(HttpServletRequest request);

    public List doSubmit(Map criteriaMap, boolean fetchStudentsWithNullPrgBrn, boolean fetchOnlyRegStudents);

    public List saveAndUpdate(HttpServletRequest request);

    public List getAllEnrollmentNo(HttpServletRequest request);

    public List deleteStudentSubjectChoiceMasterWithChild(Map criteriaMap, boolean b, boolean regOnly);
}
