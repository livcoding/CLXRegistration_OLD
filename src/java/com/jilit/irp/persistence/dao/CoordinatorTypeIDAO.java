/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CoordinatorType;
import com.jilit.irp.persistence.dto.CoordinatorTypeId;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface CoordinatorTypeIDAO extends IDAO {

    public List getGridData(String instituteid);

    public List doValidate(final CoordinatorType coType, final String mode);

    public List getLastCTypeId(String instituteid);
    //public int checkIfChildExist(final  String coordinatorTypeId);
    //  public int checkIfChildExist(final CoordinatorTypeId coordinatorTypeId);
}
