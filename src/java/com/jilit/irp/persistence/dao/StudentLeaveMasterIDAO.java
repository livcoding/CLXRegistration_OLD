/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentLeaveMaster;
import com.jilit.irp.persistence.dto.StudentLeaveMasterId;
import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public interface StudentLeaveMasterIDAO extends IDAO {

  public int checkIfChildExist(final StudentLeaveMasterId studentLeaveMasterId);
//
  public List getStudentLeaveMaster(String ins_id);
  
  public List editStudentLeaveMaster(String instituteid,String leave_id);
  
   public List doValidate(final StudentLeaveMaster master);

}
