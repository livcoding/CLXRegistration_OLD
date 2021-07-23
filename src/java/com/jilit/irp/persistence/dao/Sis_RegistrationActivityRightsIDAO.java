package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Sis_RegistrationActivityRights;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface Sis_RegistrationActivityRightsIDAO extends IDAO {

    public Object findByPrimaryKey(Serializable id);

    public List doValidate(final Sis_RegistrationActivityRights dto, final String mode);

    public List getAllSis_RegistrationActivityRights(String instituteid);

    public List getRegistrationActivityData(String instituteid);

    public List getAllStaffCode(String companyid);

    public List getEditDataSis_RegistrationActivityRights(String instituteid, String activityid, String staffid, String stafftype);
}
