/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWise;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface ProgramSchemeAcadyearWiseIDAO extends IDAO {

//       public void saveOrUpdate(Object record);
//
    public List<String> doValidate(final ProgramSchemeAcadyearWise programSchemeAcadyearWise, final String mode);
//
//       public List getBasketId(final String instituteid, final String academicyear, final String programid, final String sectionid, final String subjectid);
//
//       public List getTeachingSchemeSubjectWiseFeeReportData(final String instituteid, final String programid, final String branchid, final String academicyear, final String stynumber);

    public List getTeachingSchemeEditData(String instituteId, String programSchemeAcadWiseId);

    public List getTeachingSubjectComponentEditData(String instituteId, String programSchemeAcadWiseId);
}
