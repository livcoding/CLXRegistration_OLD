/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface StyTypeIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public List getStyTypeGridData(String instituteid);
    public List getStyTypeEditData(String instituteid,String stytypeid);

    public int checkIfChildExist(StyTypeId id);

    public List doValidate(final StyType styType, final String mode);

    public List getStyTypeData(String instituteid);
//
//    public Collection<?> getStyType(String stytype);
//
//    public List getAllQuota();
//
//    public List getAllStyType(String instituteid, String registrationid);
//
//    public List getStyType_1(String instituteid, String registrationid);
//
//    public List checkIfStyTypeExist_1(String instituteid, String stytype, String registrationid);
//
//    public List getStyType_1ExistInFST(String instituteid, String registrationid);
//
//    public List checkStyType_1ExistInFST(String instituteid, String registrationid, String stytype);
//
//    public List<Object[]> getAllStyType(final String instituteid);
//
//    public String getStyTypeId(final String instituteid);
//
//    public List getStytypeId_SCCT(String instituteid);
//
//    public List getSAPStytypeId_SCCT(String instituteid);
//

    public String getStytypeId(final String instituteid, final String stytype);
//

    public List<StyType> getStyType(final String instituteid, final String stytype);
//
//    public String getRWJStyTypeId(final String instituteid);
//
//    public String getstytypeID(String instituteid);
//
//    public String getStytype(final String instituteid, final String stytypeid);
//
//    public List getAllStyNumberWithAllOptionLOV(String instid, String Regid, String basketcode, String programid, String acadyear, String Radio);
//
//    public List checkIfStyNumberNotExistsWithAllOption(String instid, String Regid, String basketcode, String programid, String acadyear, String stynumber, String Radio);
//
//    public List getAllStyNumberWithAllOptionLOV1(String instid, String Regid, String programid);
//
//    public List getAllStyNumberWithAllOptionLOV11(String instid, String Regid, String programid, String subjectid, String subjecttypeid);
//
//    public List checkIfStyNumberNotExistsWithAllOption1(String instid, String Regid, String programid, String stynumber);
//
//    public List checkIfStyNumberNotExistsWithAllOption11(String instid, String Regid, String programid, String stynumber, String subjectid, String subjecttypeid);
//
//    public List getStyNoWithAllOption(String instid);
//
//    public List checkIfSTYNoWithAllOptionExists(String instid, String stynumber);
//
//    public List getStyTypeForAcademicDataReset(final String instituteid);
//
//    public List<Object[]> checkIfStyTypeExistForAcademicDataReset(final String instituteid, final String stytype);
//
//    public List getAllStyNumber(String instid);
//
//    public List getStyNoForAttendanceSchedular(String instituteid, String registrationid, String programid);
//
//    public List checkIfSTYNoWithAllOptionExists(String instituteid, String registrationid, String programid, String stynum);
//
//    public List getAllStyNumberForResultPublish(String enrollmentno);
}
