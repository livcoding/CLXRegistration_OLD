/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;


import com.jilit.irp.persistence.dto.Sct_ModuleRightsMaster;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface Sct_ModuleRightsMasterIDAO extends IDAO {

    public int checkIfChildExist(final String rightsid);

    public List<String> doValidate(final Sct_ModuleRightsMaster moduleRightsMaster, final String mode);

   // public ASObject getWebkioskRights(final List<String> rightsidList);

    //public Collection<?> getAllAssignedRightsModuleWise(final Collection moduleidslist, final List userTypeList, final String roleId);

    //public Collection<?> getAllRightsModuleWise(final Collection moduleidslist, final List userTypeList);

   // public List getRightsID(String completeMxmlPath);
    
    public List getParentRightsID();
    
    public Collection<?> getRightsModuleWise(final Collection moduleidslist);
}
