package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface SubjectCoordinatorReportIService {

    public void getCoordinate(ModelMap model);
    
    public void getAcademicYear(ModelMap model);

    public List getSubCode(HttpServletRequest request);

    public List getEmp_Type(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);

}
