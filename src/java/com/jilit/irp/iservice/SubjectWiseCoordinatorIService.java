/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author priyanka.tyagi
 */
public interface SubjectWiseCoordinatorIService {

    public void getSubjectWiseCoordinatorList(Model model);

    public void getAllSubjectCode(ModelMap map);

    public List getCoordinator(HttpServletRequest request);

    public List saveSubjectCoordinator(HttpServletRequest request);

    public List updateStatus(HttpServletRequest request);

}
