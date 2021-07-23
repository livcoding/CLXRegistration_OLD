/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author mohit1.kumar
 */
public interface TT_TimeTableAllocationDetailIDAO extends IDAO {

    public void deleteTtDetailOnTTransREg(String tttransid, String regId, String instituteId);

    public void deleteTtAllocationOnTTransREg(String tttransid, String regId);

    public List getTimeTableDetailData(String instituteId, String registrationId, String acadmicYear, String programId, String sectionId, String subSectionId, int styNumber, String styTypeId, String subjectId, String ttrefrenceId, String subjectcomponentId);

    public List getFacultySubjectTaggingData(String instituteId, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, byte styNumber, String subjectId, String ttRefrenceId, String subjectcomponentId);

    public List getTimeTableDetailDataForMerging(String instituteId, String registrationId, String acadmicYear, String programId, String sectionId, String subSectionId, int styNumber, String styTypeId, String subjectId, String subjectcomponentId);

    public List getFacultySubjectTaggingDataForMerging(String instituteId, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, byte styNumber, String subjectId, String ttRefrenceId, String subjectcomponentId);

    public List checkTT_TimeTableAllocationData(String instituteid, String registrationId, String academicYear, String programId, String sectionId, String subsectionId, String styTypeId, int styNumber, String subjectId);

    public List checkMultifacultyData(String instituteid, String registrationId, String tttransid);

    public List checkDataInTTAllocation(String instituteid, String registrationId, String subjectid);

    public List checkDataInOfferSubTaggingDetail(String instituteid, String registrationId, String subjectid);

    public List getMergedWithCode(String instituteid, String registrationId, String tttransid);

    public void updateMergeWithSecSubsection(String instituteid, String tttransid, String mergewithsubsectioncode);

}
