/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author deepak.gupta
 */
public interface OfferedODSubjectTaggingDetailIDAO extends IDAO{

    public Collection<?> findByPrimaryKeys(Serializable id);

    public void saveOrUpdate(Object record);
    
     public List getOfferSubjectTagginfDetaildata(String instituteid, String registrationid, String offersubjectid);
}
