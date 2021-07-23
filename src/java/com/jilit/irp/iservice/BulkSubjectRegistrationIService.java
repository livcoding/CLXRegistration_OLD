/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author deepak.gupta
 */
public interface BulkSubjectRegistrationIService {

    public void getRegistrationCode(Model model);

    public List checkExcelData(HttpServletRequest request, MultipartFile file);

    public List analyzeAndSaveExcelData(HttpServletRequest request);

    public List saveAndDeleteExcelData(HttpServletRequest request);
    
    public List saveAndDeleteExcelData1(HttpServletRequest request);

    public void getReport(HttpServletRequest request, HttpServletResponse response);
}
