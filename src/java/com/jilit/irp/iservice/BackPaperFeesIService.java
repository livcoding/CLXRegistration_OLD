/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet Singh
 */
public interface BackPaperFeesIService {

    public void getDepartmentData(ModelMap model, HttpServletRequest req);

    public List getSubjectCode(ModelMap model, HttpServletRequest req);

    public void getSemesterCode(ModelMap model, HttpServletRequest req);

    public List getSubjectWiseGridData(HttpServletRequest req);

    public List saveOrUpdateSubjectWise(HttpServletRequest request);

    public void styType(ModelMap model, HttpServletRequest req);

    public List getCreditWiseGridData(HttpServletRequest req);

    public List saveCreditWiseFee(HttpServletRequest request);

    public void getCreditWiseEditData(ModelMap mm, HttpServletRequest request);

    public List updateCreditWiseFee(HttpServletRequest request);
}
