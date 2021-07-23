/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;
import java.math.BigDecimal;
import java.io.Serializable;
import com.jilit.irp.persistence.dto.FeeStructureIndividualId;

/**
 *
 * @author subrata.lohar
 */
public interface ProgramSubjectDetailIDAO extends IDAO {

    public List getSubjectComponentIds(String instituteid, String registrationid, String subjectid, String acadyear, String programid, String sectionid, String subsectionid, String stytypeid, String basketid, String stynumber);

    public List getAllRegistrationCodeForSupplementary(String instituteid);

    public List getRegConfermOrNot(String instituteid, String registrationid, String studentid);

    public List getParametervalueFOrLateRegistration(String instituteid, String moduleid, String parameterid);

    public List getParameterForAuditSubjectCredit(String instituteid, String moduleid, String parameterid);

    public List getRegFromAndToDate(String instituteid, String registrationid, String programid, String academicYear, String branchid);

    public List checkRecordInFeestructureindivisual(String instituteid, String registrationid, String feeheadid, String studentid);

    public List getFeeHeadIdFOrLateRegistration(String instituteid);

    public List getCurrencyIdForAddDrop();

    public List getElectiveSubjectForSwap(String instituteid, String registrationid, String subjecttypeid, byte credit);

    public String getFeeHeadIdForSupplementrySubjEntry(String instituteid, String feehead);
    
    public void updateFeeStructureIndividual(FeeStructureIndividualId id, BigDecimal newamount);

}
