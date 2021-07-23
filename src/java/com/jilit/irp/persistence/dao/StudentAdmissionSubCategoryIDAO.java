/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentAdmissionSubCategory;
import com.jilit.irp.persistence.dto.StudentAdmissionSubCategoryId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public interface StudentAdmissionSubCategoryIDAO extends IDAO {

    public List getsub_admittedCategory(String instId, String admittedcategoryid);
//
//    public List getAllStudentAdmissionCategoryCode(String instituteId);
//
    public List<String> doValidate(final StudentAdmissionSubCategory studentadmissionsubcategory,String mode);

    public int checkIfChildExist(final StudentAdmissionSubCategoryId id);
    
    public List getAllStudentAdmissionSubCategory(String ins_id);
    
    public Collection<?> findAllStudentAdmissionCategoryCode(String instituteid);
    
}

