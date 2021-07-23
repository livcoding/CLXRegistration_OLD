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
 * @author Malkeet Singh
 */
public interface DuringCourseElectiveRegistrationIServices {

    public void getProgramCode(ModelMap model);

    public void creditSubjectType(ModelMap model);

    public void getGridData(ModelMap model, HttpServletRequest req);

    public List saveDuringCourseElectiveRegistration(HttpServletRequest req);

    public ModelMap editDuringCourseElectiveRegistration(HttpServletRequest request, ModelMap model);

    public List updateDuringCourseElectiveRegistration(HttpServletRequest req);

}
