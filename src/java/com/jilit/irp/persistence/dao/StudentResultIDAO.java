/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.StudentResult;
import com.jilit.irp.persistence.dto.SubjectMaster;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ibrahimb.shaik
 */
public interface StudentResultIDAO extends IDAO {

    public List getAllProgram(String instituteid);

    public List getAllBranch(String instituteid);

    public List getAllStyNumber(String instituteid);

    public List getBranchForProgram(String instituteid, String programid);

    public List<SubjectMaster> getAllSubjectCode_BPR(String instituteid);
}
