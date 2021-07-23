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
 * @author deepak.gupta
 */
public interface SubjectComponentMasterIService {

    public Model getSubjectComponentMasterList(Model model);

    public List addSubjectComponentMaster(HttpServletRequest request);

    public ModelMap getAllSubjectComponentMasterData(ModelMap mm, HttpServletRequest request);

    public List updateSubjectComponentMaster(HttpServletRequest request);

    public String checkIfChildExist(HttpServletRequest request);

    public List deleteSubjectComponentMaster(HttpServletRequest request);
}
