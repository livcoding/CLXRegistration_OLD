/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.RollNumberingSetupId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author suman.saurabh
 */
public interface RollNumberingSetupIDAO extends IDAO {

    public Collection<?> findAllInstituteWise(String instituteid);

    public int checkIfChildExist(final RollNumberingSetupId id);

    public void saveOrUpdate(Object record);
    
    public List getRollNumberDetail(final String instituteid, final String groupid);
}
