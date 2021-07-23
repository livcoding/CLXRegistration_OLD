package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.DepartmentSubjectTagging;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface DepartmentSubjectTaggingIDAO extends IDAO {

    public List<String> doValidate(final DepartmentSubjectTagging departmentSubjectTagging, final String mode);

    public List getSubjectCodeUsingDepartmentBased(String instituteid, String departmentid,String subjectid,String registrationid,String currentsubjectid);

    public List getDepartmentSubjectTaggingData(final String instituteid);

    public List getDepartmentWiseSubjectTaggingData(final String instituteid, final String deptId);

}
