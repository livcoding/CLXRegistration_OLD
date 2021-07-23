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
public interface ProgramSubjectTaggingIService {

    public void getProgramSubjectTaggingList(Model model);

    public List getAcademicYear(HttpServletRequest request);

    public List getBranch(HttpServletRequest req);

    public List getSemester(HttpServletRequest req);

    public List getSection(HttpServletRequest req);

    public List getBasket(HttpServletRequest req);

    public List loadProgramTaggingList(HttpServletRequest req);

    public List addProgramSubjectTagging(HttpServletRequest req);

    public List addNewProgramSubjectTagging(HttpServletRequest request);

    public ModelMap getAllProgramSubjectTaggingData(ModelMap mm, HttpServletRequest request);

    public List updateProgramSubjectTagging(HttpServletRequest request);

    public String checkIfChildExist(HttpServletRequest request);

    public List deleteProgramSubjectTagging(HttpServletRequest request);

    public List copyProgramSubjectTagging(HttpServletRequest req);

}
