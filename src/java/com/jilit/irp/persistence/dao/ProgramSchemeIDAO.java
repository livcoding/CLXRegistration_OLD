/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramScheme;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author subrata.lohar/Mohit1.kumar
 */
public interface ProgramSchemeIDAO extends IDAO {

//    //public Collection<?> getPrerequisiteData();
//    public List<String> doValidate(final ProgramScheme programScheme, final String mode);
//
//    public void saveOrUpdate(Object record);
//
//    public Collection<?> getPopUpProgramData(final String instituteid, final String[] programid, final String[] sectionid, final Byte[] stynumber);
//
//    public Collection<?> findPSDuplicateData(final String instituteid, final String Dprogramid, final String Dbasketid, final String Dsubjectid, final String Dsectionid, final Byte Dstynumber);
//
    public Collection<?> getSubjectTaggingList(final String instituteid, final String academicyear, final String sectionid, final String programid, final byte semister, final String basketid, String registrationid, String subjectid);
//

    public Collection<?> getSubjectTaggingListFromProgramScheme(final String instituteid, final String sectionid, final String programid, final byte semister, final String basketid);
//
//    public List getDataForAutomaticSelection(String instituteid, String programid, String sectionid, byte stynumber, String subjecttypeSTR);
//

    public List getProgramSchemeList(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String basketid);
//

    public List getProgramSchemeDetail(String instituteid, String programschemeacadwiseid);
//

    public Boolean checkProgramSchemeExistence(String instituteid, String academicyear, String programid, String sectionid, String stynumber, String subjectid);

    public List dovalidate(String instituteid, String academicyear, String programid, byte stynumber, String sectionid, String basketid, String subjectid);

    public List getTeachingSchemeData(String instituteid, String programid, String academicyear, String sectionid, String styno,String registrationid);
}
