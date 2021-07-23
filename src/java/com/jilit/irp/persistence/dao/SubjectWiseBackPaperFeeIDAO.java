/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.*;

/**
 *
 * @author Malkeet Singh
 */
public interface SubjectWiseBackPaperFeeIDAO extends IDAO {

    public List getGridData(String instituteid, String regid);

    public List getSubjectCode(String instituteid, String departmentid);

    public List doValidate(String instituteid, String registrationid, String subjectid);

    public String getSubjectWiseBackPaperFeeAmount(String instituteid, String registrationid, String subjectid);

}
