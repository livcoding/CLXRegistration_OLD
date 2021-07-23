/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author deepak.gupta
 */
public interface OfferedODSubjectTaggingIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public void saveOrUpdate(Object record);

    public List<String> doValidate(final OfferedODSubjectTagging offeredODSubjectTagging, final String mode);

    public List getOfferSubjectTaggingGridData(String instituteid, String registrationid);
}
