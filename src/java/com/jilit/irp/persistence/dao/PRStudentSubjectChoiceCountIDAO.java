/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author mohit1.kumar
 */
public interface PRStudentSubjectChoiceCountIDAO extends IDAO {
  //  public void saveOrUpdate(Object record);
    public List getPRStudentSubjectChoiceCountData(String instituteid, String registrationid, String studentid);

    public void deletePRStudentSubjectChoiceCountData(String instituteid, String registrationid, String studentid);
}