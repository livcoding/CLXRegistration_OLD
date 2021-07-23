/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;

/**
 *
 * @author priyanka.tyagi
 */
public interface StudentsFeePaymentStatusReportIService {

    public List getSemesterCode(HttpServletRequest request);
    
    public List getGridData(HttpServletRequest request);


}
