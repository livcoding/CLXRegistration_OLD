 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CommitteeActionTypeId;
import java.util.List;


/**
 *
 * @author campus.trainee
 */
public interface CommitteeActionMasterIDAO extends IDAO{

    public int checkIfChildExist(final   CommitteeActionTypeId      uid);
/*
   public void saveOrUpdate(Object record);

   public List getAllCommitteeAction(String instituteid, String committeeid, String actiontype);

   public List checkIfCommitteeAction(String instituteid, String committeeid, String actiondesc, String actiontype);
*/
}
