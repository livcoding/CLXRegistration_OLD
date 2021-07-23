/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface BasketMasterIService {

    public void getProgramCode(Model model);

    public void getSectionCode(Model model);

    public void getSubjectType(Model model);

    public List getBasketMaster(HttpServletRequest req, Model model);

    public List getSectionCode_NotSave(HttpServletRequest req, Model model);

    public List getSectionCode(HttpServletRequest req, Model model);

    public List getSemester(HttpServletRequest req, Model model);

    public List saveBasketMaster(HttpServletRequest req);

    public void editBasketMaster(ModelMap mm, HttpServletRequest request);

    public List updateBasketMaster(HttpServletRequest req);

}
