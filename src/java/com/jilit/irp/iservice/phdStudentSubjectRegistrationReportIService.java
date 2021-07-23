package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface phdStudentSubjectRegistrationReportIService {

    public List getAllPhdStudents(ModelMap model, HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

}
