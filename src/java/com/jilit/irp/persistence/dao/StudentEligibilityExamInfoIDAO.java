/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

/**
 *
 * @author deepak.gupta
 */
public interface StudentEligibilityExamInfoIDAO extends IDAO {

    public int deleteStudentEligibilityExamInfo(final String instituteid, final String eeexamid, final String eeinfoid, final String studentid);
}
