/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StateMaster;
import com.jilit.irp.persistence.dto.StateMasterId;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public interface StateMasterIDAO extends IDAO {

    // public Collection<?> getStatesWithCities(final StateMasterId id);
//    public int checkIfChildExist(final StateMasterId id);
//
//    public List<String> doValidate(final StateMaster stateMaster, final String mode);
//
//    public void saveOrUpdate(Object record);
//
//    public List getAllStateCode(String countryid);
//
//    public List checkStateCodeExists(String countryid, String statecode);
//
//    public List<String> doValidate(final StateMaster stateMaster);
      public List getAllStateCode(String countryid);
}
