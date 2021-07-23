/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Sct_IrpUsers;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface Sct_IrpUsersIDAO extends IDAO {
    
    public int checkIfChildExist(final String userid);

    public List<String> doValidate(final Sct_IrpUsers  sct_IrpUsers, final String mode);

    public List<Object[]> getLoginStatus(String userid) ;
    
}
