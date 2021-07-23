/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.DisciplinaryTypeMaster;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface DisciplinaryTypeMasterIDAO extends IDAO {

    public int checkIfChildExist(DisciplinaryTypeMaster id);

    public List doValidate(DisciplinaryTypeMaster dto);

    //public List getTypeCategory();

}
