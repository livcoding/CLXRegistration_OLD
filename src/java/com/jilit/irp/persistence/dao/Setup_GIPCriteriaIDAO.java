package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Setup_GIPCriteria;
import java.io.Serializable;
import java.util.Collection;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author campus.trainee
 */
public interface Setup_GIPCriteriaIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public Object findByPrimaryKey(Serializable id);

    public List getAllSetup_GIPCriteria(String instituteid);

    public List getEditSetip_Criteria(String instituteid, String programid);

    public List getApplicableGrade(String instituteid, String programid);

}
