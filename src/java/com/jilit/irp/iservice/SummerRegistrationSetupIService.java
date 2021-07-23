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
public interface SummerRegistrationSetupIService {

    public void getProgramCode(ModelMap model);

    public List getSummerRegistrationSetupGridData(HttpServletRequest req);

    public List getStyNumber(HttpServletRequest req);

    public List saveSummerRegistrationSetup(HttpServletRequest request);

    public void getSummerRegistrationSetupEdit(ModelMap model, HttpServletRequest req);

    public List updateSummerRegistrationSetup(HttpServletRequest request);

    public List deleteSummerRegistrationSetup(HttpServletRequest request);
}
