/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author priya.sharma
 */
public interface GuestStudentMasterIService {

    //public void getInstituteName(ModelMap model);

     void getAcademicYears(ModelMap model);
    //public void getList(Model model);
    //public void guestStudentMasterAdd(Model model);
    public void personalInfoSave(HttpServletRequest req, ModelMap m);
    
   // public List getbranchForFilteration(HttpServletRequest req);
    
    //public List getSemester(HttpServletRequest req);

    public void personalInfoUpdate(HttpServletRequest req, ModelMap m);

    public void addressDetailSave(HttpServletRequest req, ModelMap m);
    
    public List getAllStudentMaster(HttpServletRequest req);
    
    public void editStudentMaster(ModelMap model, HttpServletRequest req);

    public void studentPhotoSignatureSave(HttpServletRequest req, ModelMap m, CommonsMultipartFile[] attachment, CommonsMultipartFile[] sign);

    public void addressDetailUpdate(HttpServletRequest req, ModelMap m);
    
    public void studentPhotoSignatureUpdate(HttpServletRequest req, ModelMap m, CommonsMultipartFile[] attachment, CommonsMultipartFile[] sign);

//    public void getAcadmicYearLov(ModelMap model);

    public void getGuestInstituteName(ModelMap model);


}
