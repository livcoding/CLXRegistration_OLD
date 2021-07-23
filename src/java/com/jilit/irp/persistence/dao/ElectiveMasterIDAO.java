/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import java.util.List;

/**
 *
 * @author suman.saurabh
 */
public interface ElectiveMasterIDAO extends IDAO {

    public int checkIfChildExist(final ElectiveMasterId id);

    public List doValidate(final ElectiveMaster master, final String mode);

    public List getElectiveMaster(String instituteid);

    public List EditElectiveMaster(String instituteid,String elective_id);
}
