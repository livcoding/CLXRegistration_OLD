/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CountryMaster;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public interface CountryMasterIDAO extends IDAO {
    
     public List getAllCountryCode();

 //   public Collection<?> getCountryWithStates();

 //   public int checkIfChildExist(final String countryid);
/*
    public List<String> doValidate(final CountryMaster countryMaster, final String mode);

    public Collection<?> getCountryStatesCitiesComboData();

    public List getAllCountryCode();

    public List checkCountryCodeExists(String countrycode);
*/}
