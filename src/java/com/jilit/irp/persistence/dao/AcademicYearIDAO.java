package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.AcademicyearId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface AcademicYearIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public int checkIfChildExist(AcademicyearId academicyearId);

    public List getAcademicYear();

    public List getAcademicYear(final String instituteid);

    public List checkAcademicYear(String academicyear);

    public List<String> doValidate(final Academicyear academicyear, final String mode);

    public List<String> Academicyearvalidate(final Academicyear academicyear, final String mode);

    public List getAllAcademicYear(String instituteid);

    public List getAllDistinctAcademicYear(String instituteid);

    public List getAllAcademic_Year(String instituteid, String registrationid);

    public List getAllCertificateCode(String instituteid, String academicyear);

    public List getAllAcademic_YearExitInFST(String instituteid, String registrationid);

    public List getAllAcademicYearFst(String instituteid);

    public List getAcademicYearData_LOV(final String instituteid);

    public List checkAcademicYear_LOV(final String instituteid, final String academicyear);

    public String getMaxAcadmicYear(final String instituteid);

    public List getAcademicYearbyGroupby();

    public List checkAcademicbyGroupby(String academicyear);

    public List getAcademicYearCheckPST(String regid, String instituteid);

    public List getAll_TSRAcademicYear(String instid);

    public List checkTSRAcademicbyGroupby(String instid, String academicyear);

    public List getAll_TSRSemester(String instid);

    public List getStyNumber(String instituteid, String programid, String academicyear);

    public List getStyNumberForTSReport(String instituteid, String programid, List academicyear);

    public List checkTSRSemester(String stynumber);

    public List getacadyrForFilteration(String instid);

    public List getAcademicYearData(String instid);

    public List getProgramSchemeAcadYear(String instid);

    public List checkIfProgramSchemeAcadYearExists(String acadyr, String instid);

    public List getAcadYear_PST(String instid, String Regid);

    public List checkIfAcadYearExists_PST(String acadyr, String instid, String Regid);

    public List getAcadYear_PST1(String instid, String Regid);

    public List getAll_AcademicYearForTopperList(String instituteid);

    public List getAcademicYear(String instituteid, String subjectid, String registrationid);

    public List getAcademicYearDeactiveWise(String instituteid);
}
