/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Nazar.Mohammad
 */
public interface BulkStudentSubjectChoiceIService {

    public List getQueryCriteriaData(HttpServletRequest request);

    public List getProgramCode(HttpServletRequest request);

    public List getBranchCode(HttpServletRequest request);

    public List getStynumber(HttpServletRequest request);

    public List getSectionCode(HttpServletRequest request);

    public List getSubjectData(HttpServletRequest request);

    public Map getStudentsList(HttpServletRequest request);

    public List doSaveBulkStudents(HttpServletRequest request);

}
