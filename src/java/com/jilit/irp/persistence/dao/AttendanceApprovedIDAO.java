/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface AttendanceApprovedIDAO extends IDAO {

 /*   public List getAttendanceApprovedForApprovalType(String companyid, String yearmonth, String approvaltype);

    public List getAttendanceApprovalIdForSalaryProcessing(String companyid, String yearmonth);
*/
    public List findAll(String companyid);
}
