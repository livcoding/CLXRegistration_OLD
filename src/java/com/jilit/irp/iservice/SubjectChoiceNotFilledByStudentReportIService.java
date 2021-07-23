package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface SubjectChoiceNotFilledByStudentReportIService {

    public void getRegistrationCombo(ModelMap model);

    public List getAllAcademicYear(HttpServletRequest request);

    public List getAllProgramCode(HttpServletRequest request);

    public List getAllSectionCode(HttpServletRequest request);

    public List getAllBasketCode(HttpServletRequest request);

    public List getAllSubjectCode(HttpServletRequest request);

    public List getAllSubSectionCode(HttpServletRequest request);

    public List getAllSemester(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

}
