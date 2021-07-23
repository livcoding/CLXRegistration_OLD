package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMaster;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMasterId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface Sis_RegistrationActivityMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public Object findByPrimaryKey(Serializable id);

    public List getAllDataSis_RegistrationActivityMaster(String instituteid);

    public List doValidate(final Sis_RegistrationActivityMaster dto, final String mode);

    public List getFeeHeadList(String instituteid);

    public int checkIfChildExist(final Sis_RegistrationActivityMasterId id);

}
