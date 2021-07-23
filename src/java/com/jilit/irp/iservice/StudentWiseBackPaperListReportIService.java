package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

/**
 *
 * @author ankur.goyal
 */
public interface StudentWiseBackPaperListReportIService {

    void getComboStudentWiseBackPaperList(Model model);

    void getReport(HttpServletRequest request, HttpServletResponse response);

    List getBranch(HttpServletRequest request);

    public List getStyNo(HttpServletRequest request);

}
