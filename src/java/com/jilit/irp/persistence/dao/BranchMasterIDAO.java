package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.BranchMaster;
import com.jilit.irp.persistence.dto.BranchMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Mohit kumar
 */
public interface BranchMasterIDAO extends IDAO {

    public List getbranchMasterList(String instituteid);

    public Collection<?> findAll(String instituteid);

    public List getBranchCodeWithProList(String insid, String programid);

    public List getBranchCode1(String instituteid, String programid);

    public List getToBranchCode(String insid, String programid, String branchid);

    public List<String> doValidate(final BranchMaster branchMaster, final String mode);

    public List getBranchMasterEdit(String instituteid, String programid, String branchid);

    public int checkIfChildExist(final BranchMasterId branchMasterId);

    public List getBranchCode(String instituteid, String programid);

    public List getBranchForSubjectFinalization(String instituteid, String regid, String acadyear, String programid);

    public List getAll_TSRBranchCode(String programid, String instid);

    public List getBranchForAcadWiseRegistration(String instituteid, String regid, List acadyear, List programid);

}
