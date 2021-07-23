/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentGroupMasterId;
import java.util.List;

/**
 *
 * @author ankit.kumar
 */
public interface StudentGroupMasterIDAO extends IDAO {

    public List getStudentGroupMaster(String instituteid);

    public List editGroupMaster(String groupid, String clientid, String programid, String branchid);

    public int checkIfChildExist(final StudentGroupMasterId id);

    public List getBranchCode(final String instituteid, final String progid, final String acad_year);

}
