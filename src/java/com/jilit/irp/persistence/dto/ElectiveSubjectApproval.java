/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author singh.jaswinder
 */
public class ElectiveSubjectApproval implements Serializable{
    
    private ElectiveSubjectApprovalId id;
    private String finalized;
    private String finalizedby;
    private Date finalizeddate;
    private String approved;
    private String approvedby;
    private Date approvaldate;
    private String deactive;
    private SubjectTypeMaster subjecttypemaster;
    private RegistrationMaster registrationmaster;



    public RegistrationMaster getRegistrationmaster() {
        return registrationmaster;
    }

    public void setRegistrationmaster(RegistrationMaster registrationmaster) {
        this.registrationmaster = registrationmaster;
    }

    public SubjectTypeMaster getSubjecttypemaster() {
        return subjecttypemaster;
    }

    public void setSubjecttypemaster(SubjectTypeMaster subjecttypemaster) {
        this.subjecttypemaster = subjecttypemaster;
    }
    
    public Date getApprovaldate() {
        return approvaldate;
    }

    public void setApprovaldate(Date approvaldate) {
        this.approvaldate = approvaldate;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getFinalized() {
        return finalized;
    }

    public void setFinalized(String finalized) {
        this.finalized = finalized;
    }

    public String getFinalizedby() {
        return finalizedby;
    }

    public void setFinalizedby(String finalizedby) {
        this.finalizedby = finalizedby;
    }

    public Date getFinalizeddate() {
        return finalizeddate;
    }

    public void setFinalizeddate(Date finalizeddate) {
        this.finalizeddate = finalizeddate;
    }

    public ElectiveSubjectApprovalId getId() {
        return id;
    }

    public void setId(ElectiveSubjectApprovalId id) {
        this.id = id;
    }

}
