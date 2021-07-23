/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.DisciplinaryOffenceMaster;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface DisciplinaryOffenceMasterIDAO extends IDAO {

    public int checkIfChildExist(DisciplinaryOffenceMaster id);

    public List doValidate(DisciplinaryOffenceMaster dto);

    //public List getOffenceLevel();


}
