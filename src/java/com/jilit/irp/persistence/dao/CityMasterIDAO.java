/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CityMaster;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public interface CityMasterIDAO extends IDAO {
    
     public List getAllCityCode(String countryid, String stateid);

  /*  public List<String> doValidate(final CityMaster cityMaster, final String mode);

    public Short getMaxCitySeqId(final CityMaster cityMaster);

    public Collection<?> getAllCityStateCountry();

    public Collection<?> checkIfAllCityStateCountryExists(final String cityname);

    public List getAllCity();

    public List getAllCityCode(String countryid, String stateid);

    public List checkCityCodeExists(String countryid, String stateid,String citycode);

    public List getAllCityMasterData();
    
     public List checkIfCityMasterExist(String citycode);
   * */
}
