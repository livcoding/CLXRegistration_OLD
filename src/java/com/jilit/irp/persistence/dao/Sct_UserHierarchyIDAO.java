/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author soa university
 */
public interface Sct_UserHierarchyIDAO extends IDAO {

    public List studentPersonalAcademicInfo(final String instituteid, final String studentid, final String regid, final String programid, final String branchid, final String academicyear, final String subsectionid);

    public List studentSubjectInfo(final String instituteid, final String studentid, final String regid);

    public List studentSubjectInfoChoice(final String instituteid, final String studentid, final String regid);

    public List getStudentPhoneInfo(String studentid);

}
