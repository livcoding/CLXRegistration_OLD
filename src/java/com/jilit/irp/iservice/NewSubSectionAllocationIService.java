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
public interface NewSubSectionAllocationIService {

    public void getStudentMasterTabData(Model model);

    public List getBranchAndStyNumber(HttpServletRequest req, ModelMap mm);

    public List getSection(HttpServletRequest req);

    public List getSubSectionStudentMas(HttpServletRequest req);

    public List getSubsectionForCombo2(HttpServletRequest req);

    public List loadStudentMasterList(HttpServletRequest req);

    public List saveSubSectionAllocationStudentMaster(HttpServletRequest req);

    public List getAcademicYear(HttpServletRequest req);

    public List getStyNumber(HttpServletRequest req);

    public List getBranchForStuReg(HttpServletRequest req);

    public List getSectionForStuReg(HttpServletRequest req);

    public List getSectionListForReg(HttpServletRequest req);

    public List getSubSectionListForReg(HttpServletRequest req);

    public List loadStudentRegData(HttpServletRequest req);

    public List saveSubSectionAllocationStudentReg(HttpServletRequest req);

    public List getSubjectCode(HttpServletRequest req);

    public List getDepartmentCode(HttpServletRequest req);

    public List getSubjectCompCode(HttpServletRequest req);

    public List loadStudentChoiceData(HttpServletRequest req);

    public List getSectionForChoice(HttpServletRequest req);

    public List getSubSectionChoice(HttpServletRequest req);

    public List getAcademicYearForChoice(HttpServletRequest req);

    public List getProgramForChoice(HttpServletRequest req);

    public List getBranchForChoice(HttpServletRequest req);

    public List getStyTypeChoice(HttpServletRequest req);

    public List getStyNumberChoice(HttpServletRequest req);

    public List loadStudentChoiceFilterData(HttpServletRequest req);

    public List saveSubSectionAllocationStudentChoiceDetail(HttpServletRequest req);

}
