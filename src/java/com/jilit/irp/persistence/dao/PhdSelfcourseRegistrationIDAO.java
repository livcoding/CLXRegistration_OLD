package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author ankit.kumar
 */
public interface PhdSelfcourseRegistrationIDAO extends IDAO {

    public List getPHDSubjectRegistrationDataPending(String instituteid, String registrationid, String status);

    public List getPHDSubjectRegistrationDataApprove(String instituteid, String registrationid, String status);

    public List getPHDSubjectRegistrationDataCancelled(String instituteid, String registrationid, String status);

    public List getSubjectWiseReportData(String instituteid, String registrationid);
}
