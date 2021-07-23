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
public interface RegistrationParametersIService {

    public List getRegistrationParametersGridData(HttpServletRequest req);

    public void getRegistrationParametersEditData(ModelMap mm, HttpServletRequest request);

    public List updateRegistrationParameters(HttpServletRequest request);
}
