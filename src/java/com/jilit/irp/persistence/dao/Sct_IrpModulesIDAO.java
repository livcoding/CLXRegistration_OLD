/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Sct_IrpModules;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface Sct_IrpModulesIDAO extends IDAO {

    public int checkIfChildExist(final String moduleid);

    public List<String> doValidate(final Sct_IrpModules irpModules, final String mode);

  //  public ASObject getModuleMenu(final List irpModuleList);

   // public ASObject getRightsModuleWise(final String moduleId, final List<String> rightsidList);

    public Collection<?> getModuleId(final String moduleName);
    public Collection<?> getModuleCode();
    
  
}


