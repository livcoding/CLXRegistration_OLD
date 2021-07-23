package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface SubjectFacultyCumCoordinatorWiseStudentListIService {

    void getRegistrationCombo(ModelMap model);

    List getSubjectCodeCombo(HttpServletRequest request);

    List getCoordinatorCodeCombo(HttpServletRequest request);

    void getReport(HttpServletRequest request, HttpServletResponse response);

}
