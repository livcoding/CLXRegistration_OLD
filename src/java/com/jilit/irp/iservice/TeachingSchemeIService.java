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
 * @author mohit1.kumar
 */
public interface TeachingSchemeIService {

    public void getTeachingSchemeData(ModelMap model);

    public List getBranch(HttpServletRequest req);

    public List getSemester(HttpServletRequest req);

    public List getSection(HttpServletRequest req);

    public List getBasket(HttpServletRequest req);

    public List getBasketData(HttpServletRequest req);

    public List getAllBasketMaster(HttpServletRequest req);

    public List saveCopiedData(HttpServletRequest request);

    public List getGridData(HttpServletRequest req);

    public List getAllSubjectType();

    public List getAllSubjectMaster();

    public List getAllElecticeCode();

    public List getAllSubjectComponent();

    public List getDepartmentMasterData(HttpServletRequest req);

    public List addProgramSchemeAcademicyearWise(HttpServletRequest request);

    public void getTeachingSchemeEditData(ModelMap model, HttpServletRequest req);

    public List updateProgramSchemeAcademicyearWise(HttpServletRequest request);

    public List deleteProgramScheme(HttpServletRequest request);

    public List getMarks(HttpServletRequest req);
}
