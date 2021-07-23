package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface SubjectWiseStudentCountReportIService {

    public void getComboSubjectWiseStudentCount(ModelMap model);

    public List getProgramWithBranchCode(HttpServletRequest request);

    public List getStyNumber(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);
    
}
