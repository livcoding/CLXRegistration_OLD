package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface DBDependentIDAO extends IDAO {

    public List getSubjectWiseStudentCountDataPre(String instituteid, String companyid, String stynumber, String programid, String branchid, String registrationid, String stytypeid);

    public List getSubjectWiseStudentCountData(String instituteid, String companyid, String stynumber, String programid, String branchid, String registrationid, String stytypeid);

    public List getGridDataRegistration_infoBased(String instituteid, String registrationid, List academicyear, List programid);

    public List getGridDataPSTBased(String instituteid, String registrationid, List academicyear, List programid);

    public List checkStudentDueAmount(String instituteid, String studentid, String registrationid);

    public List checkStudentDueAmountFeeHeadWise(String instituteid, String studentid, String registrationid, String feeheadid);
}
