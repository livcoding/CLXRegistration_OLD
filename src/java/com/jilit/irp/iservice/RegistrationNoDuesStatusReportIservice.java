package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
public interface RegistrationNoDuesStatusReportIservice {

    public void getFormData(Model model);

    public List getAcademicYear(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

    public List getProgram(HttpServletRequest request);

    public List getStyNo(HttpServletRequest request);

}
