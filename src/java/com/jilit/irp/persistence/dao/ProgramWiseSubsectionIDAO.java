/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramWiseSubsection;
import com.jilit.irp.persistence.dto.ProgramWiseSubsectionId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface ProgramWiseSubsectionIDAO extends IDAO {

    public String doValidate(final ProgramWiseSubsection programWiseSubsection, final String mode);

    public int checkIfChildExist(final ProgramWiseSubsectionId programWiseSubsectionId);

    public List getSubSectionList(String instituteid, String sectionid);

    public List getsubsection(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String subjectid, String registrationid);

    public List getProgramWiseSubBatchList(String instituteid, String acad_year, String programid, String branchid);

    public List getProgramWiseSubBatchData(String instituteid, String acad_year);

    public List getDataCheckNewacademicyear(String instituteid, String new_acadyear);

    public String saveObjList(final List<ProgramWiseSubsection> list);

    public List getSectionList(String instituteid, String programid, String branchid, String academicyear, byte stynumber);

    public List getSectionListMinStudentWise(String instituteid, String programid, String branchid, String academicyear, byte stynumber);

    public List getSubSectionList(String instituteid, String programid, String branchid, String academicyear, String Sectionid);

    public List getSubSectionCount(String instituteid, String programid, String branchid, String academicyear, byte stynumber, String Sectionid, String subsectionid);

    public List getSubSectionListMinStudentWise(String instituteid, String programid, String branchid, String academicyear, byte stynumber, String sectionid);
}
