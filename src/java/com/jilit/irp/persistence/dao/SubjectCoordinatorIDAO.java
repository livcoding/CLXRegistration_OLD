/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.SubjectCoordinator;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface SubjectCoordinatorIDAO extends IDAO {

    public List getGridData(String instituteid);

    public List getCoordinate(String ins_id);
    
    public List getAcademicYear(String ins_id);

    public List getCoordinateWithSubjectCode(String instituteid, String subjectid, String staffid);

    public List getCoordinateWithoutSubjectCode(String ins_id, String staffid, String acadyear);
//
//     public List findAllFacultyDetailsWithOutTimetableAllocation(final String instituteid, final String registrationid,String subjectid, String subjectcomponentid);

    public List getCoordinator(String instituteid, String depid, String status);

    public List checkActiveSubject(String subid, String instituteid);

}
