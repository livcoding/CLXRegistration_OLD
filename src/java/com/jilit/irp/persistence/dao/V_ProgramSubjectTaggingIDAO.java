/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface V_ProgramSubjectTaggingIDAO extends IDAO {
//
//    public List SubjectswithFilterpoints( String registrationid,String instituteid,String academicYear,String semester,String Sectionid,String ProgramCode,String subjecttypeid);
//
//    public Collection<?> getSubjectList(final String instituteid, final String registrationid, final String departmentid, final String academicyear, final String programid, final String sectionid, final String stynumber);
//
//    public List SubjectswithFilterpointsForInstituteElective(final String instituteid,final String registrationid,final String academicYear,final String semester,final String Sectioncode,final String ProgramCode,final String subjecttypeid);
//
//    public List getV_ProgramSubjectTaggingData(final String instituteid);
//
//    public List checkIfRegCodeExists_V_ProgramSubjectTagging(final String instituteid, final String regcode);
//

    public List getV_ProgramSubjectTypeData(final String instituteid, final String registrationid);
//
//    public List checkIfSubjectTypeExists_V_ProgramSubjectTagging(final String instituteid,final String registrationid, final String subjecttype);
//

    public List getSubjectTypeReport(final String registrationid, final String subjecttypeid, final String instituteid);
//
//    public List SubjectswithFilterpoints(final String instituteid,final String enrollmentNo,final String registrationid,final String academicYear,final String semester,final String Sectionid,final String Programid,final String subjecttypeid);
//
//    public List getStudentSubjectChoiceViewDetail(final String instituteid,final String registrationid,final String basketid,final String subjectid, final String departmentid, final String sectionid, final String stynumber);
//
//    public List getStudentSubjectChoiceViewDetailForNewTimeTableEntry(final String instituteid, final String registrationid, final String subjectid, final String departmentid, final String subcompid, final String stynumber);
//
//    public List getStudentSubjectChoiceViewDetailForNewTimeTableEntryForElectiveBatch(final String instituteid, final String registrationid, final String subjectid, final String departmentid, final String subcompid);
}
