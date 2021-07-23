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
 * @author malkeet.singh
 */
public interface SupplementarySubjectEntryIService {

    public void getSemesterCode(ModelMap model);

    public List getStudetnInfo(HttpServletRequest request);

    public List getGridData(HttpServletRequest request);

    public List getStudents(HttpServletRequest request);

    public List getSubjectCode(HttpServletRequest request);

    public List saveSupplementarySubjects(HttpServletRequest request);

    public List deleteSupplementarySubjects(HttpServletRequest request);
}
