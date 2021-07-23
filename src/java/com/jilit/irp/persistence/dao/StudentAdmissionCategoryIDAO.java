/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentAdmissionCategory;
import com.jilit.irp.persistence.dto.StudentAdmissionCategoryId;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public interface StudentAdmissionCategoryIDAO extends IDAO {

    public int checkIfChildExist(final StudentAdmissionCategoryId id);

    public List getadmittedCategory(String instId);
    
//    public List getadmittedCategory1(String instId);

    public List doValidate(final StudentAdmissionCategory dto, final String mode);

}
