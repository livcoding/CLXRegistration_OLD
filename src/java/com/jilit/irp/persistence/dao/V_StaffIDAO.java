/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface V_StaffIDAO extends IDAO {

    public List getEmp_Type(String instituteid, String emp_type);

    public List getStaff(String depId, String instituteid, String facultyFrom);

    public List gettingAllDepartmentWiseEmployeeCode(String departmentid, String instituteid);
}
