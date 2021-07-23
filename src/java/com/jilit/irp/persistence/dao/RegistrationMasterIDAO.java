package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.TT_SlotMasterId;
import java.util.Collection;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import java.util.List;

/**
 *
 * @author ashutosh1.kumar
 */
public interface RegistrationMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public List getRegistrationCodeGridData(final String instituteid);

    public List getRegistrationEditData(final String instituteid, final String registrationid);

    public List getAllRegistrationCodeDesc(String instituteid);

    public Collection<?> getRegsitrationCode(final String instituteid);

    public Collection<?> timeSlotGridData(final String instituteid, final String registrationid);

    public int checkIfChildExist1(final TT_SlotMasterId id);

    public List getDaysBySlotId(final String slotid, final String registration_no, final String instituteid);

    public Collection<?> getRegsitrationCode(final String instituteid, final String[] registrationid);

    public Collection<?> getRegistrationMaster(String instituteid);

    public Collection<?> getRegistrationMasterList(String instituteid);

    public int checkIfChildExist(final String instituteid, final String registrationid);

    public List<String> doValidate(final RegistrationMaster registrationmaster, final String mode);

    public List getregcaption(String instituteid, String registrationid);

    public List getprogduration(final String instituteid, final String acadyear, final String programid, final String branchid);

    public List<Object[]> getRegistrationCodeForAcademicDataReset(String instituteid);

    public List getRegistrationCode_1(final String instituteid);

    public List getRegistrationNumber(String institute, String programid, String branchid, String reg_id);

    public List getAllRegistrationCode(String instituteidlsit);

    public List getAllRegistrationCodeForAddDropSubject(List instituteidlsit);

    public List<Object[]> getFromToDate(String instituteid, String registrationid);

    public List getRegistrationCodeForSupplementary(String instituteid);

    public List getRegistrationCodeForSummer(List instituteid);

    public boolean saveOrUpdateObjectList(List list);

    public List getRegCodeForStudentsFeePaymentStatus(List instituteid, String reg_Type);

    public List<Object[]> getRegCodeForOST(String instituteid);

    public String getRegistrationIdByCode(String instituteid, String registrationcode);
}
