/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CommitteeDesignations;

import com.jilit.irp.persistence.dto.CommitteeDesignationsId;
import java.util.List;

/**
 *
 * @author campus.trainee
 */
public interface CommitteeDesignationsIDAO extends IDAO {

    public int checkIfChildExist(final CommitteeDesignationsId committeeDesignationsId);
 //   public List<String> doValidate(final CommitteeDesignations committeeDesignations, final String mode) ;

}
