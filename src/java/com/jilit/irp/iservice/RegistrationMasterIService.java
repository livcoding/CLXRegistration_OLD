/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
public interface RegistrationMasterIService {

    public void getRegistrationMaster(Model model);

    public void getRegistrationMasterList(Model model);

    public void getAcademicyearProgramList(Model model);

    public List addRegistrationMaster(HttpServletRequest request);

    public void editRegistrationMaster(Model mm, HttpServletRequest request);

    public List updatRegistrationMaster(HttpServletRequest request);

    public String checkIfChildExist(HttpServletRequest request);

    public List deleteRegistrationMaster(HttpServletRequest request);

    public List getgetRegistrationID(Model model);

}
