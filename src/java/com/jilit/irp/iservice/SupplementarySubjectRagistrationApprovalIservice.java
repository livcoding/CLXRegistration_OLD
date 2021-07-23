/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
public interface SupplementarySubjectRagistrationApprovalIservice {

    public void getFormData(Model model);

    public Map getPendingForApprovalData(HttpServletRequest request);

    public List getApprovedData(HttpServletRequest request);

    public List getCanclledData(HttpServletRequest request);

    public List getSubjectWiseReportData(HttpServletRequest request);

    public List approveCancleData(String status, HttpServletRequest request);
    
}
