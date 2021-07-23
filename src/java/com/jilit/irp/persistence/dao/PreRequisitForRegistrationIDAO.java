package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.PreRequisitForRegistration;
import com.jilit.irp.persistence.dto.PreRequisitForRegistrationId;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface PreRequisitForRegistrationIDAO extends IDAO {

    public List getAllListPreRequisitForRegistration(String instituteid, String academicYear);

    public int checkIfChildExist(final PreRequisitForRegistrationId id);

    public List<String> doValidate(final PreRequisitForRegistration preRequisitForRegistration, final String mode);

    public List getEditPreRequisitForRegistration(String instituteid, String academicyear, String programid, String branchid, String stynumber);

    public List getPreRequisiteForRegistrationReportData(String instituteid, String academicyear, List programid, List branchid, String stynumber);

}
