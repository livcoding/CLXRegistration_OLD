/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentCategory;
import com.jilit.irp.persistence.dto.StudentCategoryId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author S.Saurabh
 */
public interface StudentCategoryIDAO extends IDAO {

//    public ArrayList getDataForStudentCategory(final String instid);
//
    public List getStudentCategoryList(final String instituteid);
//
      public List doValidate(final StudentCategory dto, final String mode);
//
      public int checkIfChildExist(StudentCategoryId id);
      
//
//    public List getAllCategorycode(String instituteid);
//
//    public List getStudentCategoryReportData(String categorycode, String orderby, String sorttype, String instituteid);
//
//    public String getCategoryId(final String instituteid, final String categorycode);
//
     public Collection<?> findAll(String instituteid);
//
//    public List getAllCategoryData(final String instituteid);
//
//     public List checkAllCategoryData(final String instituteid,final String categorycode);
}
