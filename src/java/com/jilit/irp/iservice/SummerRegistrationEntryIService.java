/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import org.springframework.ui.ModelMap;
import javax.servlet.http.*;
import java.util.List;

/**
 *
 * @author priyanka.tyagi
 */
public interface SummerRegistrationEntryIService {

    public void getInstituteCode(ModelMap model);

    public List getSemesterCode(HttpServletRequest request);

    public List getStudetnInfo(HttpServletRequest request);

    public List getGridData(HttpServletRequest request);

    public List getStudents(HttpServletRequest request);

    public List getSubjectCode(HttpServletRequest request);

    public List saveSummerSubjects(HttpServletRequest request);

    public List deleteSummerSubjects(HttpServletRequest request);
}
