/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface FacultySubjectTaggingIDAO extends IDAO {
//

    public List checkIfFSTIDExists(final String instituteid, final String registrationid, final String subjectid, final String academicyear, final String programid, final String sectionid, final String subsectionid, final byte stynumber, final String componentid, final String stytypeid);

    public List getAddDropList(final String instituteid, final String registrationid, final byte stynumber, final String studentid, final String subjectid, final String academicyear, final String programid, final String sectionid, final String subsectionid);

    public List getSubjectDetail(final String instituteid, final String registrationid, final String subjectid, final String basketid, final String programid);

    public List checkFstIdExistOrNot(String instiuteid, String registrationid, String subjectid, String acadyear, String programid, byte styNo, String sectionid, String stytypeid, String subjectcomponentid,String subsectionid);

    public List getDropSubjectDetail(final String instituteid, final String registrationid, final String subjectid, final String basketid, final String programid, final String studentid);

    public List getGipSubject(String instituteid, String registrationid, String studentid, String programid, String gradeid, String grade);

    public List getGipCriteria(String instituteid, String programid);

    public Collection<?> getGradeCriteriaBased(final String instituteid, final String[] grade);

    public String getBasketForAudit(String instituteid, String programid, String sectionid, String stynumber);

    public List getBasketForGip(String instituteid, String registrationid, String studentid, String subjectid);

    public List checkInPST(String instituteid, String registrationid, String subjectid, String academicyear, String programid, Short stynumber, String sectionid, String basketid);

    public List getComponentIds(String instituteid, String subjectid, String registrationid, String programid, String academicyear);

    public List getStudentDetailData(String studentid, String instituteid);

    public List checkFstIdExistOrNotForAudit(String instiuteid, String registrationid, String subjectid, String academicyear, String programid, byte styNo, String sectionid, String stytypeid, String subjectcomponentid, String basketid);

    public List checkFSTExistOrNot(String instiuteid, String registrationid, String subjectid, String academicyear, String programid, byte styNo, String sectionid, String subsectionid, String stytypeid, String subjectcomponentid, String basketid);
}
