/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface FacultySubjectTaggingIService {

    public void getRegistraionCode(ModelMap model, HttpServletRequest req);

    public void getDepartmentCode(ModelMap model, HttpServletRequest req);

    public List getAcadmicyear(HttpServletRequest request);

    public List getSubject_FacultyCode(HttpServletRequest req);

    public List getComponentCode(HttpServletRequest req);

    public List getProgramCode(HttpServletRequest request);

    public List getSectionCode(HttpServletRequest request);

    public List getSemesterCode(HttpServletRequest request);

    public List getFstList(HttpServletRequest request);

    public List saveFacultySubjectTagging(HttpServletRequest request);

    public List getSaveGridata(HttpServletRequest request);

}
