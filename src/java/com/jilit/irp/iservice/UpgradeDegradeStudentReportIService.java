package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface UpgradeDegradeStudentReportIService {

    void getAcademicYearCombo(ModelMap model);

    List getAllBranchCode(HttpServletRequest request);

    void getAllStyCode(ModelMap model);

    void getReport(HttpServletRequest request, HttpServletResponse response);

}
