/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author Shimona.Khandelwal
 */
public interface StudentQualificationIDAO extends IDAO {

    public List getStudentQualification(String studentid);
//
//    public String getQualification(String qualificationid);
//
//    public String getBoard(String boardid);
}
