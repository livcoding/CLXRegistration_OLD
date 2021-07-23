/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.QualificationMaster;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface QualificationMasterIDAO extends IDAO {

    public int checkIfChildExist(final String qualificationcategoryid);
//
    public List<String> doValidate(final QualificationMaster qualificationMaster, final String mode);
//
//    public Collection<?> getQualificationMasterReportData(final String orderBy, final String includeDeactivce, final String ascDesc);
//
//    public List getQualifcationMasterDataForEmployee();
//
//    public List checkQualifcationCodeExist(final String qualificationCode);
//
//    public List ValidateQualificationBoardCode(String code);
//
//    public List getQualificationBoardCode();
//
//     public List checkIfQualification_Exist(String qcode);
//
//    public List getQualificationCode();
//
//     public List checkIfQualificationCodeExist(final String qualificationcode);
//
//     public List getQualificationDropDown();
}