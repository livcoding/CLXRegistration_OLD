/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.InstituteMaster;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface InstituteMasterIDAO extends IDAO {

    public List getInstituteUniqueIdBase(String uniqueid);

    public List getAllInstituteCode(String instituteid);

    public List getInstituteCodeForAddDrop(String userid, String rightsid);
}
