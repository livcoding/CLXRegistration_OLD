/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sunny.singhal
 */
public interface ProgramSubjectTaggingIDAO extends IDAO {

    public List getProgramTaggingData(final String instituteid, final String regId, final String academicyear, final String programid, final String branchId, final Short stynumber, final String sectionid, final String basketid);

    public List getProgramTaggingData(String instituteid, String regId, String programSubId);

    public List getAcadYear(String instituteid, String regid);

    public List getData(String instituteid, String academicyear, String registrationid, String programid, List sectionid, Short stynumber);

    public List getSubjectWiseFSTData(String instituteid, String academicyear, String registrationid, String subjectid, String subjectcomponentid,String stytype_id);

    public int checkIfChildExist1(final String instituteid, final String registrationid, final String programsubjectid);

    public int checkIfChildExist2(final String instituteid, final String registrationid, final String programsubjectid);

    public List<String> doValidate(final ProgramSubjectTagging programSubjectTagging, final String mode);

    public List getAll_TTSTYNumberData(final String instituteid, final String registrationid, final String programid, final String sectionid);

    public List getAllRunningSubjectFromProgramSubjectTagging(final String instituteid, final String registrationid, final String programid, final String sectionid, final String academicyear, final String stynumber);

    public List getAllRunningSubjectFromProgramScheme(final String instituteid, final String programid, final String stynumber, final String sectionid);

    public String getProgramCode(final String institiuteid, final String programid);

    public String getSectionCode(final String institiuteid, final String sectionid);

    public List getAllRunningSubjectFromProgramSubjectTagging_LTP(final String instituteid, final String registrationid, final String programid, final String sectionid, final String academicyear, final String stynumber, final String subjectcode);

    public List getPSTList(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String basketid, String reg);

    public List dovalidate(String instituteid, String academicyear, String programid, Short stynumber, String sectionid, String basketid, String reg, String subjectid);

    public Boolean checkPSTExistence(String instituteid, String academicyear, String programid, String sectionid, String stynumber, String subjectid);

    public List getPSTDetail(String instituteid, String programsubjectid, String reg);

    public List getTakenCredit(String instituteid, String registrationid, String studentid);

    public Boolean checkPSTExistence(String instituteid, String registrationid, String academicyear, String programid, String sectionid, String stynumber, String basketid, String subjectid);

    public List getSubjectType(String instituteid, String registrationid);

    public List getStyNumber(String instituteid, String registrationid, String subjecttypeid);

    public List getDepartment(String instituteid, String registrationid, String subjecttypeid, String stynumber);

    public String getPSTid(String instituteid, String regId, String academicyear, String programid, String branchId, Short stynumber, String sectionid, String basketid, String subjectid);

    public List getStyNumberByInstituteid(String instituteid);
}
