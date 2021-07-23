package com.jilit.irp.persistence.dao;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface DepartmentMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public List getDepartmentNameOfReqSubject(String instituteid, String subjectid);

    public List getAllDepartmentCode();

    public List getAllFSTDepartmentCode(String instituteid,String registrationid);

    public List getAllRegWiseDepartmentCode(final String regid);

    public List getAllDepartment();

    public List getDepartmentCode();

}
