package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ankur.goyal
 */
public interface SummerRegistrationReportIService {

    List getEnrollmentNumber(HttpServletRequest request);

    void getReport(HttpServletRequest request, HttpServletResponse response);

}
